package constraint;

import java.util.Set;

import haw.is.sudokury.Field;

public class FieldConstraintVariable extends ConstraintVariable<Field, Integer> {

	public FieldConstraintVariable(Field field) {
		super(field, Set.of(1,2,3,4,5,6,7,8,9));
	}

}
