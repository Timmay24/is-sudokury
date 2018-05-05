package haw.is.sudokury.constraints;

import java.util.Arrays;
import java.util.HashSet;

public class FieldConstraintVariable<T> extends ConstraintVariable<T, Integer> {

    public FieldConstraintVariable(T variable) {
        super(variable, new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
    }

    public FieldConstraintVariable(T variable, HashSet<Integer> domain) {
        super(variable, new HashSet<>(domain));
    }

    public FieldConstraintVariable(T variable, Integer... domainValues) {
        this(variable, new HashSet<>(Arrays.asList(domainValues)));
    }

    public FieldConstraintVariable(ConstraintVariable constraintVariable) {
        super((T) constraintVariable.getVariable(), new HashSet<>(constraintVariable.getDomain()));
    }
}
