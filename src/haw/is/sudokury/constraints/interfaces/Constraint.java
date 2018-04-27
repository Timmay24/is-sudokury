package haw.is.sudokury.constraints.interfaces;

import haw.is.sudokury.constraints.ConstraintVariable;

public interface Constraint<E> {
	public ConstraintVariable<E, Integer> getF();
	public ConstraintVariable<E, Integer> getG();
	public boolean isConsistent();
}
