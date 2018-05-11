package haw.is.sudokury.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Field;

public class AC3Solver extends Solver {

	public AC3Solver() {
		super();
	}

	private int[][] board;
	private int iterationCounted;

	@Override
	public int solve(int[][] board) {
		iterationCounted = 1;
		this.board = board;
		List<ConstraintVariable> constVars = new ArrayList<>();
		// sammle alle ContVars
		for (Constraint<Field> constraint : constraints) {
			ConstraintVariable<Field, Integer> var = constraint.getSource();
			if (!constVars.contains(var)) {
				constVars.add(var);
				// wenn die Variable bereits belegt ist, also das Feld
				// ausgefüllt ist:
				int x = (var.getVariable()).getX();
				int y = (var.getVariable()).getY();
				int value = board[x][y];
				if (value != 0) {
					var.getDomain().clear();
					var.getDomain().add(value);
				}

			}

			var = constraint.getTarget();
			if (!constVars.contains(constraint.getTarget())) {
				constVars.add(var);
				// wenn die Variable bereits belegt ist, also das Feld
				// ausgefüllt ist:
				int x = (var.getVariable()).getX();
				int y = (var.getVariable()).getY();
				int value = board[x][y];
				if (value != 0) {
					var.getDomain().clear();
					var.getDomain().add(value);
				}

			}

		}
		// mache Kantenkonsistent
		makeArcsConsistent(constraints);
		// ist bereits gelöst?
		if (isSolved(constraints)) {
			return 1;
		}
		// sortiere die Liste
		// Collections.reverse(constVars);
		// für die Möglichkeiten
		for (ConstraintVariable<Field, Integer> var : constVars) {
			// versuche die Domain durch wenn mehr als 1 Element noch enthalten
			// ist
			if (var.getDomain().size() > 1) {
				for (int domain : var.getDomain()) {
					// klone die Constraints
					Set<Constraint> clonedConstraints = cloneContraints(constraints);
					// finde den geklonten neuen Var
					for (Constraint clonedConstraint : clonedConstraints) {
						// und setze die Annahme ein
						if (clonedConstraint.getSource().equals(var)) {
							clonedConstraint.getSource().getDomain().retainAll(Arrays.asList(domain));
						} else if (clonedConstraint.getTarget().equals(var)) {
							clonedConstraint.getTarget().getDomain().retainAll(Arrays.asList(domain));
						}
					}
					// setze das Feld auf die ausgewählte Domain
					if (solve_(clonedConstraints)) {
						return iterationCounted;
					}
				}
			}
		}
		return -iterationCounted; // wenn nicht lösbar oder mehrdeutig, dann negative Zahl zurückgeben
	}

	private boolean solve_(Set<Constraint> constraints) {
		iterationCounted++;
		System.out.println(iterationCounted);
		// mache Kantenkonsistent
		makeArcsConsistent(constraints);
		// ist bereits gelöst?
		if (isSolved(constraints)) {
			return true;
		}
		// sammle alle ContVars
		List<ConstraintVariable> constVars = new ArrayList<>();
		for (Constraint constraint : constraints) {
			if (!constVars.contains(constraint.getSource())) {
				constVars.add(constraint.getSource());
			}
			if (!constVars.contains(constraint.getTarget())) {
				constVars.add(constraint.getTarget());
			}
		}
		// sortiere die Liste
		// Collections.reverse(constVars);
		// für die Möglichkeiten
		for (ConstraintVariable<Field, Integer> var : constVars) {
			if (var.getDomain().size() > 1) {
				// versuche die Domain durch
				for (int domain : var.getDomain()) {
					// System.out.println(var.getVariable() + " " +
					// var.getDomain() + " " + domain);
					// klone die Constraints
					Set<Constraint> clonedConstraints = cloneContraints(constraints);
					// finde den geklonten neuen Var
					for (Constraint clonedConstraint : clonedConstraints) {
						// und mache setze die Annahme ein
						if (clonedConstraint.getSource().equals(var)) {
							clonedConstraint.getSource().getDomain().retainAll(Arrays.asList(domain));
							break;
						} else if (clonedConstraint.getTarget().equals(var)) {
							clonedConstraint.getTarget().getDomain().retainAll(Arrays.asList(domain));
							break;
						}
					}

					// löse mit der Annahme
					if (solve_(clonedConstraints)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isSolved(Set<Constraint> constraints) {
		for (Constraint constraint : constraints) {
			if (constraint.getSource().getDomain().size() != 1 || constraint.getTarget().getDomain().size() != 1) {
				return false;
			}
		}
		return true;
	}

	private int solve__(Set<Constraint> constraints, ConstraintVariable<Field, Integer> annahme) {
		// constraints (in darÃ¼ber liegendem aufruf gecloned) und annahme
		// vereinen

		// vereinigte menge auf kantenkonsistenz prÃ¼fen
		// wenn konsistent dann
		// nÃ¤chste variable mit mind. zwei elementen suchen (best choice...)
		// per for-schleife Ã¼ber ihre domain einen mÃ¶glichen wert fÃ¼r die
		// annahme
		// wÃ¤hlen
		// und wieder solve__ mit geclonten constraints und der annahme aufrufen

		//
		// wenn NICHT kosistent dann
		// -1 zurÃ¼ckgeben
		return 0;
	}

	public Set<Constraint> cloneContraints(Set<Constraint> constraints) {
		Set<Constraint> clonedConstraints = new HashSet<>();
		for (Constraint constraint : constraints) {
			clonedConstraints.add(constraint.cloneConst());
		}
		return clonedConstraints;
	}

	public boolean makeArcsConsistent(Set<Constraint> constraints) {
		// int score = 0;
		LinkedList<Constraint> q = new LinkedList<>();

		q.addAll(constraints);

		while (!q.isEmpty()) {

			// score++;

			// erste kante(x,y) aus Queue holen
			Constraint<Field> constraint = q.pop();
			FieldConstraintVariable source = (FieldConstraintVariable) constraint.getSource();
			FieldConstraintVariable target = (FieldConstraintVariable) constraint.getTarget();

			if (revise(source, target)) {
				// alle kanten hinzufÃ¼gen, in denen x nachbar von z ist
				// kante(z, x)
				q.addAll(getArcsToNeighboursOf(source, constraints));
			}
		}
		return true;

	}

	/*
	 * procedure AC3-LA(cv) Q <- {(Vi,Vcv) in arcs(G),i>cv}; consistent <- true;
	 * while not Q empty & consistent select and delete any arc (Vk,Vm) from Q;
	 * if REVISE(Vk,Vm) then Q <- Q union {(Vi,Vk) such that (Vi,Vk) in
	 * arcs(G),i#k,i#m,i>cv} consistent <- not Dk empty endif endwhile return
	 * consistent end AC3-LA
	 * 
	 * 
	 */

	public List<Constraint<Field>> getArcsToNeighboursOf(FieldConstraintVariable fcv, Set<Constraint> constraints) {
		List<Constraint<Field>> result = new LinkedList<>();

		for (Constraint<Field> constraint : constraints) {
			FieldConstraintVariable y = (FieldConstraintVariable) constraint.getTarget();

			// wenn die variable y an zweiterstelle gleich fcv ist
			// dann ist x ein nachbar zu fcv bzw. y
			if (y.getVariable().equals(fcv.getVariable())) {
				result.add(constraint);
			}
		}
		return result;
	}

	public int getValueOf(Field field) {
		return board[field.getX()][field.getY()];
	}

	public boolean isFieldEmpty(Field field) {
		return getValueOf(field) == 0;
	}

	/*
	 * REVISE loÌˆscht nur Werte von Vi Und zwar diejenigen, fuÌˆr die es keinen
	 * den Constraint erfuÌˆllenden Wert von Vj gibt
	 */
	public boolean revise(ConstraintVariable<Field, Integer> vi, ConstraintVariable<Field, Integer> vj) {
		boolean delete = false;
		Set<Integer> di = vi.getDomain();
		Set<Integer> dj = vj.getDomain();
		if (di.isEmpty() || dj.isEmpty()) {
			return false;
		}
		// Behilfsset fÃ¼r anschlieÃŸende LÃ¶schung, da wÃ¤hrend der Iteration
		// keine Ã„nderung an der Menge, Ã¼ber die iteriert wird,
		// vorgenommen werden darf (java says no).
		Set<Integer> valuesToBeDeleted = new HashSet<>();

		for (Integer i : di) {
			boolean satisfied = false;
			for (Integer j : dj) {
				if (i != j) {
					satisfied = true;
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

	public void constraintsToString(Set<Constraint> constraints) {
		Set<ConstraintVariable> constVars = new HashSet<>();
		for (int y = 0; y < 9; ++y) {
			for (int x = 0; x < 9; ++x) {
				for (Constraint<Field> constraint : constraints) {
					constVars.add(constraint.getSource());
					constVars.add(constraint.getTarget());
				}
			}
		}
		for (ConstraintVariable var : constVars) {

			System.out.print(" - " + var.getVariable() + ": " + var.getDomain());

		}
		System.out.println();
	}
}
