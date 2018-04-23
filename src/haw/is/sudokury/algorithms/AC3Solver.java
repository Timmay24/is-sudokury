package haw.is.sudokury.algorithms;

import java.util.ArrayList;
import java.util.List;

import haw.is.sudokury.models.Field;

public class AC3Solver extends Solver {
	
	public AC3Solver() {
		super();
	}

	private int[][] board;
	
	@Override
	public int solve(int[][] board) {
		this.board = board;
		
		// Alle leeren Felder zur Betrachtung einlesen //
		List<Field> emptyFields = new ArrayList<>();
		
		// TODO: refactor into method
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (board[x][y] == 0) {
					emptyFields.add(Field.getField(x, y));
				}
			}
		}
		////
		
		
		
		
		
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
		
		return 0; // Score
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
	 * 
	 */
	public boolean revise(Field i, Field j) {
		boolean delete = false;
		
		
		
		
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
		
		//für jeden möglichen Feldwert X im Feld i:
		//	wenn es keinen möglichen Feldwert Y im Feld j gibt mit dem ein Constraint(X,Y) konsistent ist
		//	(d.h. 
		//  dann lösche X aus Feld i
		
		return delete;
	}
	
	
	/*
	 * // von feldwert 1..9 testen, ob belegung möglich
		
		Set<Integer> validValuesForI = new HashSet<>();
		for (int v = 1; v < 10; v++) {
			
			/// CONTRAINTS PRÜFEN \\\
			// constraints zu Field i holen
			Set<Field> relatedFields = constraints.get(i);
			
			for (Field field : relatedFields) {
				
			}
			
		}
	 */
}
