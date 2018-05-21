package haw.is.sudokury.algorithms;

import haw.is.sudokury.constraints.ConstraintBuilder;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Board;
import haw.is.sudokury.models.Field;
import haw.is.sudokury.models.exceptions.AmbiguousException;
import haw.is.sudokury.models.exceptions.NotSolvableException;

import java.util.*;

public class AC3Solver {

    public AC3Solver() {}

    public int solve(Board board) throws NotSolvableException {

        // Queue mit allen Variablen anlegen, in denen Werte ausprobiert werden können
        LinkedList<FieldConstraintVariable> varsToCheck = new LinkedList<>(board.getOpenFields());

        if (board.isAmbiguous())
            throw new AmbiguousException();

        if (varsToCheck.isEmpty()) {
            if (board.isSolved()) {
                return 0;
            } else {
                throw new RuntimeException("No vars to check but board is not solved.");
            }
        }

        // Zu prüfende Variablen in Queue vorsortieren, damit die mit den wenigsten Möglichkeiten zuerst geprüft werden
        varsToCheck.sort((v1, v2) -> {
            if (v1.getDomain().size() == v2.getDomain().size()) {
                return 0;
            } else if (v1.getDomain().size() > v2.getDomain().size()) {
                return 1;
            } else {
                return -1;
            }
        });

        int score = 0;

        // Probiere in jedem noch offenen Feld alle Möglichkeiten aus
        for (FieldConstraintVariable<Field> fieldConstVar : varsToCheck) {
            for (Integer trialValue : fieldConstVar.getDomain()) {
                score++;

                // Lege lokale Kopie des Boards an, um zu verhindern, dass Änderungen ans Originalboard propagiert werden
                Board localBoardCopy = new Board(board);

                // Testwert im lokalen Board setzen
                localBoardCopy.setFieldValue(
                        fieldConstVar.getVariable().getX(),
                        fieldConstVar.getVariable().getY(),
                        trialValue
                );

                // Lege weitere lokale Kopie an, auf der das Kantenkonsistenzverfahren arbeiten kann
                // (dort werden Domain-Werte verändert, was nicht an der weiterzugebenen Kopie geschehen darf)
                Board consistencyBoardCopy = new Board(localBoardCopy);

                // Bereite Constraints für die Kantenkonsistenzprüfung vor
                Set<Constraint> constraints = ConstraintBuilder.buildConstraints(consistencyBoardCopy);

                // Prüfe übergebenes Board anhand seiner consistency-Kopie auf Kantenkonsistenz
                makeArcsConsistent(constraints);

                if (consistencyBoardCopy.domainsAreDistinct()) {
                    try {
                        return solve(localBoardCopy) + score;
                    } catch (NotSolvableException e) {
                    }
                }
            }
        }

        throw new NotSolvableException();
    }

    public void makeArcsConsistent(Set<Constraint> constraints) {
        LinkedList<Constraint> q = new LinkedList<>();

        q.addAll(constraints);

        while (!q.isEmpty()) {
            // Erste kante(x,y) aus Queue holen
            Constraint<Field> constraint = q.pop();
            ConstraintVariable x = constraint.getSource();
            ConstraintVariable y = constraint.getTarget();

            if (revise(x, y))
                // Alle kanten hinzufügen, in denen x Nachbar von z ist => kante(z, x)
                q.addAll(getArcsToNeighboursOf(x, constraints));
        }
    }
	
	/*
	 * 	procedure AC3-LA(cv)
		  Q <- {(Vi,Vcv) in arcs(G),i>cv};
		  consistent <- true;
		  while not Q empty & consistent
		    select and delete any arc (Vk,Vm) from Q;
		    if REVISE(Vk,Vm) then Q <- Q union {(Vi,Vk)
		        such that (Vi,Vk) in arcs(G),i#k,i#m,i>cv}
		      consistent <- not Dk empty
		    endif
		  endwhile
		  return consistent
		end AC3-LA
	 * 
	 * 
	 */

    public List<Constraint<Field>> getArcsToNeighboursOf(ConstraintVariable fcv, Set<Constraint> constraints) {
        List<Constraint<Field>> result = new LinkedList<>();

        for (Constraint<Field> constraint : constraints) {
            ConstraintVariable y = constraint.getTarget();

            // wenn die variable y an zweiterstelle == fcv ist
            //	dann ist x ein nachbar zu fcv bzw. zu y
            if (y.getVariable().equals(fcv.getVariable()))
                result.add(constraint);
        }
        return result;
    }

    /*
     * REVISE löscht nur Werte von Vi
     * Und zwar diejenigen, für die es keinen den Constraint erfüllenden
     * Wert von Vj gibt
     */
    public boolean revise(ConstraintVariable<Field, Integer> vi, ConstraintVariable<Field, Integer> vj) {
        boolean delete = false;
        Set<Integer> di = vi.getDomain();
        Set<Integer> dj = vj.getDomain();
        // Behilfsset für anschließende Löschung, da während der Iteration keine Änderung an der Menge,
        // über die iteriert wird, vorgenommen werden darf (java says no).
        Set<Integer> valuesToBeDeleted = new HashSet<>();

        for (Integer i : di) {
            boolean satisfied = false;

            for (Integer j : dj) {
                if (i != j) {
                    // mind. ein erfüllendes Constraint gefunden
                    satisfied = true;
                    // daher kann mit dem nächsten i fortgefahren werden
                    break;
                }
            }
            if (!satisfied) {
                valuesToBeDeleted.add(i);
                delete = true;
            }
        }
        di.removeAll(valuesToBeDeleted);
        return delete;
    }
}
