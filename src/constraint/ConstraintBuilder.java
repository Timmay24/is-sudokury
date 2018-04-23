package constraint;

import java.util.HashSet;
import java.util.Set;

import haw.is.sudokury.Field;

public class ConstraintBuilder {

	private ConstraintBuilder() {
		
	}
	
	public static Set<Constraint> buildConstriants(){
		Set<Constraint> result = new HashSet<>();
		for(int x = 0; x < 9; ++x) {
			for(int y = 0; y < 9; ++y) {
				Field field = Field.getField(x, y);
				ConstraintVariable constVar = new FieldConstraintVariable(field);
				
				
				//alle in der Zeile
				for(int x2 = 0; x2 < 0; ++x2) {
					
					if(x != x2) {
						Field field2 = Field.getField(x2, y);
						ConstraintVariable constVar2 = new FieldConstraintVariable(field2);
						result.add(new AllDiffConstraint(constVar, constVar2));
					}
				}
		
				//alle in der Spalte
				for(int y2 = 0; y2 < 0; ++y2) {
					
					if(y != y2) {
						Field field2 = Field.getField(x, y2);
						ConstraintVariable constVar2 = new FieldConstraintVariable(field2);
						result.add(new AllDiffConstraint(constVar, constVar2));
					}
				}
				
				//alle im Block
				for(int x2 = 0; x2 < 0; ++x2) {
					for(int y2 = 0; y2 < 0; ++y2) {
						
						if(y != y2 && x != x2 && x/3==x2/3 && y/3==y2/3) {
							Field field2 = Field.getField(x2, y2);
							ConstraintVariable constVar2 = new FieldConstraintVariable(field2);
							result.add(new AllDiffConstraint(constVar, constVar2));
						}
					}
				}
				
				
			}
		}
		return result;
	}
}
