package haw.is.sudokury.algorithms;

import java.util.ArrayList;
import java.util.Collection;
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
		int score = 0;
		this.board = board;
		return solveRec(constraints);
		
		
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
	private int solveRec(Set<Constraint> constraints) {
		makeArcsConsistent((Collection <? extends Constraint<Field>>) constraints);
		Set<ConstraintVariable> vars = new HashSet<>();
		boolean solved = true;
		//hol dir mal alle ConstVars und pack sie in ein Set
		for(Constraint constraint: constraints) {
			ConstraintVariable var = constraint.getSource();
			vars.add(var);
			if(var.getDomain().size()>1) {
				solved = false;
			}
		}
		if(solved) {
			return 1;
		}
		//gehe alle interessanten und erlaubten Feldgrößen durch
		for(int i = 2; i <= 9; ++i) {
			for(ConstraintVariable<Field, Integer> var: vars) {
				if(var.getDomain().size() == i) {
					Set<Constraint> clonedConstraints = cloneContraints(constraints);
					for(Integer annahme: var.getDomain()) {
						for(Constraint clonedConstraint: clonedConstraints) {
							if(clonedConstraint.getSource().equals(var)) {
								clonedConstraint.getSource().getDomain().retainAll(Set.of(i));
								solveRec(clonedConstraints);
							}
						}
					}
				}
			}
		}
		
	}
	
	private int solve__(Set<Constraint> constraints, ConstraintVariable<Field, Integer> annahme) {
		// constraints (in darüber liegendem aufruf gecloned) und annahme vereinen
	
		// vereinigte menge auf kantenkonsistenz prüfen
		// wenn konsistent dann
		//		nächste variable mit mind. zwei elementen suchen (best choice...)
		//		per for-schleife über ihre domain einen möglichen wert für die annahme wählen
		//		und wieder solve__ mit geclonten constraints und der annahme aufrufen 
		
		//		
		// wenn NICHT kosistent dann
		//		-1 zurückgeben
	}
	
	
	private Set<Constraint> cloneContraints(Set<Constraint> constraints) {
		Set<Constraint> clonedConstraints = new HashSet<>();
		for(Constraint constraint: constraints) {
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
	
	public List<Constraint<Field>> getArcsToNeighboursOf(FieldConstraintVariable fcv,Collection<? extends Constraint<Field>> constraints) {
		List<Constraint<Field>> result = new LinkedList<>();
		
		for (Constraint<Field> constraint : constraints) {
			FieldConstraintVariable y = (FieldConstraintVariable) constraint.getTarget();
			
			// wenn die variable y an zweiterstelle gleich fcv ist
			//	dann ist x ein nachbar zu fcv bzw. y
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
	 * REVISE löscht nur Werte von Vi
	 * Und zwar diejenigen, für die es keinen den Constraint erfüllenden
	 * Wert von Vj gibt 
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
