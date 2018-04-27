package haw.is.sudokury.constraints;

import java.util.Arrays;
import java.util.HashSet;
import haw.is.sudokury.models.Field;

public class FieldConstraintVariable extends ConstraintVariable<Field, Integer> {

	public FieldConstraintVariable(Field field) {
		super(field, new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
	}

}
