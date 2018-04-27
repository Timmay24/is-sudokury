package haw.is.sudokury.algorithms;

import java.util.ArrayList;
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
		
		List<Field> emptyFields = new ArrayList<>();
		LinkedList<Constraint<Field>> q = new LinkedList<>();
		
		// Q <- {(Vi,Vj) in arcs(G),i#j};
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (board[x][y] == 0) {
					emptyFields.add(Field.getField(x, y));
					for (Constraint<Field> constraint : constraints) {
						// queue all constraints with field(x,y) at first
						if (constraint.getF().getVariable().equals(Field.getField(x, y))) {
							q.add(constraint);
						}
					}
				}
			}
		}
		// while queue not empty
		while (!q.isEmpty()) {
			
			score++;
			
			// (X,Y) <- getFirst(queue)
			Constraint<Field> constraint = q.pop();
			FieldConstraintVariable x = (FieldConstraintVariable) constraint.getF();
			FieldConstraintVariable y = (FieldConstraintVariable) constraint.getG();
			
			if (revise(x, y)) {
				
				// foreach Z in neighbor(X) - {Y}
				for (Constraint<Field> constraintZ : constraints) {				
					if (constraintZ.getG().equals(x) && !constraintZ.getG().equals(y)) {
						q.add(constraintZ);
					}
				}
			}
		}
		
		/* 	function AC3(csp) returns csp possibly with the domains reduced
			    queue, a queue with all the arcs of the CSP
			    while queue not empty
			         (X,Y) <- getFirst(queue)
			         if RemoveConsistentValues(X,Y, csp) then
			              foreach Z in neighbor(X) - {Y}
			                   add to queue (Z,X)
			    return csp
		 */
		
		
		/*
		 * procedure AC-3
			   Q <- {(Vi,Vj) in arcs(G),i#j};
			   while not Q empty
			     select and delete any arc (Vk,Vm) from Q;
			     if REVISE(Vk,Vm) then
			       Q <- Q union { (Vi,Vk) such that (Vi,Vk) in arcs(G),i#k,i#m }
			       endif
			   endwhile
			end AC-3
		 */
		
		/*for (Field f : emptyFields) {
			for (Constraint constraint : constraints) {
				if (constraint.getF().getVariable().equals(f)) {
					System.out.println(constraint.getF().getDomain());
				}
			}
		}*/
		
		return score;
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
		
		Set<Integer> equalValues = new HashSet<>();
		for (Integer i : di) {
			if (dj.contains(i)) {
				equalValues.add(i);
				delete = true;
			}
		}
		
		di.removeAll(equalValues);
		return delete;
		
		/*
		 * procedure REVISE(Vi,Vj)
			   DELETE <- false;
			   for each X in Di do
			     	if there is no such Y in Dj such that
			                       (X,Y) is consistent,
					then
			       		delete X from Di;
			       		DELETE <- true;
		       		endif;
   				endfor;
   				return DELETE;
 			end REVISE
		 */
	}
}
