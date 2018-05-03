package haw.is.sudokury.algorithms;

import java.util.Set;

import haw.is.sudokury.constraints.ConstraintBuilder;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;

public abstract class Solver {

	protected final Set<Constraint> constraints;

	public Solver() {
		this.constraints = ConstraintBuilder.buildConstraints();  
	}

	public abstract int solve(int[][] board);

	public final Set<Constraint> getConstraints() {
		return constraints;
	}
}
