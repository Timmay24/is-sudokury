package haw.is.sudokury.algorithms;

import java.util.ArrayList;
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

	@Override
	public int solve(int[][] board) {
		// mache Kantenkonsistent
		makeArcsConsistent((Collection<? extends Constraint<Field>>) constraints);
		// ist bereits gel�st?
		if (isSolved(constraints)) {
			return 1;
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
		Collections.reverse(constVars);
		// f�r die M�glichkeiten
		for (ConstraintVariable<Field, Integer> var : constVars) {
			// versuche die Domain durch
			for (int domain : var.getDomain()) {
				//klone die Constraints
				Set<Constraint> clonedConstraints = cloneContraints(constraints);
				//finde den geklonten neuen Var
				ConstraintVariable<Field, Integer> clonedVar = null;
				for (Constraint clonedConstraint : clonedConstraints) {
					if (clonedConstraint.getSource().equals(var)) {
						clonedVar = clonedConstraint.getSource();
					} else if (clonedConstraint.getTarget().equals(var)) {
						clonedVar = clonedConstraint.getTarget();
					}
				}
				int tempResult = solve_(clonedConstraints);
				if(tempResult >=1) {
					return tempResult+1;
				}
			}
		}
		return -1;
	}

	private int solve_(Set<Constraint> constraints) {
		// mache Kantenkonsistent
				makeArcsConsistent((Collection<? extends Constraint<Field>>) constraints);
				// ist bereits gel�st?
				if (isSolved(constraints)) {
					return 1;
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
				Collections.reverse(constVars);
				// f�r die M�glichkeiten
				for (ConstraintVariable<Field, Integer> var : constVars) {
					// versuche die Domain durch
					for (int domain : var.getDomain()) {
						//klone die Constraints
						Set<Constraint> clonedConstraints = cloneContraints(constraints);
						//finde den geklonten neuen Var
						ConstraintVariable<Field, Integer> clonedVar = null;
						for (Constraint clonedConstraint : clonedConstraints) {
							if (clonedConstraint.getSource().equals(var)) {
								clonedVar = clonedConstraint.getSource();
							} else if (clonedConstraint.getTarget().equals(var)) {
								clonedVar = clonedConstraint.getTarget();
							}
						}
						int tempResult = solve_(clonedConstraints);
						if(tempResult >=1) {
							return tempResult+1;
						}
					}
				}
				return -1;
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
		// constraints (in darüber liegendem aufruf gecloned) und annahme vereinen

		// vereinigte menge auf kantenkonsistenz prüfen
		// wenn konsistent dann
		// nächste variable mit mind. zwei elementen suchen (best choice...)
		// per for-schleife über ihre domain einen möglichen wert für die annahme
		// wählen
		// und wieder solve__ mit geclonten constraints und der annahme aufrufen

		//
		// wenn NICHT kosistent dann
		// -1 zurückgeben
		return 0;
	}

	private Set<Constraint> cloneContraints(Set<Constraint> constraints) {
		Set<Constraint> clonedConstraints = new HashSet<>();
		for (Constraint constraint : constraints) {
			clonedConstraints.add(constraint.cloneConst());
		}
		return clonedConstraints;
	}

	public boolean makeArcsConsistent(Collection<? extends Constraint<Field>> constraints) {
		// int score = 0;
		LinkedList<Constraint<Field>> q = new LinkedList<>();

		q.addAll(constraints);

		while (!q.isEmpty()) {

			// score++;

			// erste kante(x,y) aus Queue holen
			Constraint<Field> constraint = q.pop();
			FieldConstraintVariable x = (FieldConstraintVariable) constraint.getSource();
			FieldConstraintVariable y = (FieldConstraintVariable) constraint.getTarget();

			if (revise(x, y)) {
				// alle kanten hinzufügen, in denen x nachbar von z ist kante(z, x)
				q.addAll(getArcsToNeighboursOf(x, constraints));
			}
		}
		return true;
	}

	/*
	 * procedure AC3-LA(cv) Q <- {(Vi,Vcv) in arcs(G),i>cv}; consistent <- true;
	 * while not Q empty & consistent select and delete any arc (Vk,Vm) from Q; if
	 * REVISE(Vk,Vm) then Q <- Q union {(Vi,Vk) such that (Vi,Vk) in
	 * arcs(G),i#k,i#m,i>cv} consistent <- not Dk empty endif endwhile return
	 * consistent end AC3-LA
	 * 
	 * 
	 */

	public List<Constraint<Field>> getArcsToNeighboursOf(FieldConstraintVariable fcv,
			Collection<? extends Constraint<Field>> constraints) {
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
	 * REVISE löscht nur Werte von Vi Und zwar diejenigen, für die es keinen den
	 * Constraint erfüllenden Wert von Vj gibt
	 */
	public boolean revise(ConstraintVariable<Field, Integer> vi, ConstraintVariable<Field, Integer> vj) {
		boolean delete = false;
		Set<Integer> di = vi.getDomain();
		Set<Integer> dj = vj.getDomain();
		// Behilfsset für anschließende Löschung, da während der Iteration
		// keine Änderung an der Menge, über die iteriert wird,
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
}
