package haw.is.sudokury.constraints;

import haw.is.sudokury.models.Field;

import java.util.Arrays;
import java.util.HashSet;

public class FieldConstraintVariable extends ConstraintVariable<Field, Integer> {

	public FieldConstraintVariable(Field field) {
		super(field, new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
	}
	
	public FieldConstraintVariable(Field field, HashSet<Integer> domain) {
		super(field, domain);
	}

	@Override
	public ConstraintVariable cloneVar() {
		return new FieldConstraintVariable(this.getVariable(), new HashSet<>(this.getDomain()));
	}

	public int getX() {
        return this.getVariable().getX();
    }

    public int getY() {
        return this.getVariable().getY();
    }
}
