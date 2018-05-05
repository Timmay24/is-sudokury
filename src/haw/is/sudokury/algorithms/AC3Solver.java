package haw.is.sudokury.algorithms;

import haw.is.sudokury.constraints.ConstraintBuilder;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.v2.Board;
import haw.is.sudokury.models.v2.Field;

import java.util.*;

public class AC3Solver {

    public AC3Solver() {
    }


    // ??? Variablen nicht im Board halten, sondern lokal (in der Hierarchie neben Board und Constraints)?
    //      Vorteil:  Kein zusätzliches Board für Kantenkonsistenz notwendig (in dem die Domainwerte verändert werden)
    //      Nachteil: Variablen aus Board herausziehen und Collection/2d-array zur Haltung der Variablen an ConstraintBuilder übergeben?


    public int solve(Board board) {
        int score = 0;

        // Lege lokale Kopie des Boards an, um zu verhindern, dass Änderungen an board nach oben propagiert werden
        Board localBoardCopy = new Board(board);

        // Lege weitere lokale Kopie an, auf der das Kantenkonsistenzverfahren arbeiten kann
        // (dort werden Domain-Werte verändert, was nicht an weiterzugebenen Kopie geschehen darf)
        Board consistencyBoardCopy = new Board(board);

        // Bereite Constraints für die Kantenkonsistenzprüfung vor
        Set<Constraint> constraints = ConstraintBuilder.buildConstraints(consistencyBoardCopy);

        // Prüfe übergebenes Board anhand seiner consistency-Kopie auf Kantenkonsistenz
        makeArcsConsistent(constraints);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                // Wenn nach Herstellung der Kantenkonsistenz Felder existieren, in denen nicht genau eine Möglichkeit
                // übrig ist (=0 oder >1), dann ist die gewählte Belegung ungültig oder das Spielfeld mehrdeutig
                if (consistencyBoardCopy.getVariable(x, y).getDomain().size() != 1) {
                    return -1;
                }
            }
        }

        // Prüfe anhand der lokalen Kopie, ob nun alle Felder belegt sind
        boolean solved = true; // technisch zuerst annehmen, es wäre gelöst (bis das Gegenteil festgestellt wird)
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (localBoardCopy.getFieldValue(x, y) == 0) {
                    solved = false;
                }
            }
        }
        // Falls alle Felder belegt sind, ist das Board gelöst (und da die Kantenkonsistenz durchgelaufen ist
        // ist die Lösung auch gültig)
        if (solved) {
            return 1;
        }

        // Falls das Board noch nicht gelöst wurde, geht es weiter mit dem Ausprobieren...

        // Queue mit allen Variablen anlegen, in denen Werte ausprobiert werden können
        LinkedList<FieldConstraintVariable> varsToCheck = new LinkedList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board.getVariable(x, y).getDomain().size() != 1) {
                    varsToCheck.add(board.getVariable(x, y));
                }
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

        // Probiere in jedem noch offenen Feld alle Möglichkeiten aus
        for (FieldConstraintVariable<Field> fieldConstVar : varsToCheck) {
            for (Integer trialValue : fieldConstVar.getDomain()) {
                localBoardCopy.setFieldValue(
                        fieldConstVar.getVariable().getX(),
                        fieldConstVar.getVariable().getY(),
                        trialValue); // Testwert im lokalen Board setzen
                score = solve(localBoardCopy);

                if (score != -1) {
                    return score + 1;
                }

                // TODO Wenn Testwert nicht zum Erfolg führt, dann muss der Wert zurückgenommen werden und
                // TODO ein nächster ausprobiert werden
                // Wann ist eine Belegung ungültig? Wenn -1 zurückkommt
                // wert hier schon gesetzt
            }
        }

        return score;

        // TODO mache lokale Kopie (später zum weitergeben) vom übergebenen Board
        //  => sonst werden an board an das übergebene Board propagiert!
        // TODO mache Arbeitskopie vom übergebenen Board
        // TODO Führe Kantenkonsistenz auf Arbeitskopie aus
        // TODO Prüfe ob jede Domain der Arbeitskopie nur noch 1 Element besitzt
        // TODO     JA   => fahre fort
        // TODO     NEIN => gibt -1 zurück

        // TODO Wähle in lokaler Kopie ein Feld, das belegt ist und dessen Domain möglichst klein ist (schleife über Feldvariablen)
        // TODO Setze einen Wert aus der Domain der gewählten Feldvariablen im zugehörigen Boardfeld (schleife über Domainwerte)
        // TODO Rufe return solve(board) + 1 auf

        //return score;
		/*
		 * 	function AC3
		 // Reduziert Domänen
		     queue = Alle Kanten des CSP
		     while (!empty(queue))
		         Entferne eine Kante (x, y) aus queue;
		         if(EntferneInkonsistenteWerte(x, y))
		             foreach (Nachbar z von x)
		                 queue.ADD(Kante(z, x))
		 	function AC3 end
		 */
		
		/* 	function AC3(csp) returns csp possibly with the domains reduced
			    queue, a queue with all the arcs of the CSP
			    while queue not empty
			         (X,Y) <- getFirst(queue)
			         if RemoveConsistentValues(X,Y, csp) then
			              foreach Z in neighbor(X) - {Y}
			                   add to queue (Z,X)
			    return csp
		*/
    }

    public boolean makeArcsConsistent(Set<Constraint> constraints) {
        // int score = 0;
        LinkedList<Constraint> q = new LinkedList<>();

        q.addAll(constraints);

        while (!q.isEmpty()) {

            // erste kante(x,y) aus Queue holen
            Constraint<Field> constraint = q.pop();
            ConstraintVariable x = constraint.getSource();
            ConstraintVariable y = constraint.getTarget();

            if (revise(x, y)) {
                // alle kanten hinzufügen, in denen x nachbar von z ist kante(z, x)
                q.addAll(getArcsToNeighboursOf(x, constraints));
            }
        }
        return true;
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

            // wenn die variable y an zweiterstelle gleich fcv ist
            //	dann ist x ein nachbar zu fcv bzw. y
            if (y.getVariable().equals(fcv.getVariable())) {
                result.add(constraint);
            }
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
