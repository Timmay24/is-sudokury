package haw.is.sudokury.constraints;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import haw.is.sudokury.models.Field;

public class FieldConstraintVariable extends ConstraintVariable<Field, Integer> {

	public FieldConstraintVariable(Field field) {
		super(field, new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
	}
	
	public FieldConstraintVariable(Field field, Integer... domain) {
		super(field, new HashSet<>(Set.of(domain)));
	}
	
	public FieldConstraintVariable(Field field, HashSet<Integer> domain) {
		super(field, domain);
	}

	@Override
	public ConstraintVariable cloneVar() {
		return new FieldConstraintVariable(this.getVariable(), new HashSet<>(this.getDomain()));
	}
	
}
