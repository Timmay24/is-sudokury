package haw.is.sudokury.constraints;

import java.util.HashSet;
import java.util.Set;

import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Field;

public class ConstraintBuilder {

	private ConstraintBuilder() {}
	
	public static Set<Constraint> buildConstraints() {
		Set<Constraint> result = new HashSet<>();
		for(int x = 0; x < 9; ++x) {
			for(int y = 0; y < 9; ++y) {
				Field field = Field.getField(x, y);
				ConstraintVariable<Field, Integer> constVar = new FieldConstraintVariable(field);
				
				
				//alle in der Zeile
				for(int x2 = 0; x2 < 9; ++x2) {
					
					if(x != x2) {
						Field field2 = Field.getField(x2, y);
						ConstraintVariable<Field, Integer> constVar2 = new FieldConstraintVariable(field2);
						result.add(new AllDiffConstraint<Field>(constVar, constVar2));
					}
				}
		
				//alle in der Spalte
				for(int y2 = 0; y2 < 9; ++y2) {
					
					if(y != y2) {
						Field field2 = Field.getField(x, y2);
						ConstraintVariable<Field, Integer> constVar2 = new FieldConstraintVariable(field2);
						result.add(new AllDiffConstraint<Field>(constVar, constVar2));
					}
				}
				
				//alle im Block
				for(int x2 = 0; x2 < 9; ++x2) {
					for(int y2 = 0; y2 < 9; ++y2) {
						
						if(y != y2 && x != x2 && x/3==x2/3 && y/3==y2/3) {
							Field field2 = Field.getField(x2, y2);
							ConstraintVariable<Field, Integer> constVar2 = new FieldConstraintVariable(field2);
							result.add(new AllDiffConstraint<Field>(constVar, constVar2));
						}
					}
				}
			}
		}
		return result;
	}
}
