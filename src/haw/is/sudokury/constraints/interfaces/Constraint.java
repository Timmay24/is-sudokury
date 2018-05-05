package haw.is.sudokury.constraints.interfaces;

import haw.is.sudokury.constraints.ConstraintVariable;

public interface Constraint<E> {
	public ConstraintVariable<E, Integer> getSource();
	public ConstraintVariable<E, Integer> getTarget();
	public boolean isConsistent();
	public Constraint copy();
}
