package haw.is.sudokury.algorithms;

import java.util.Map;
import java.util.Set;

import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Field;
import haw.is.sudokury.constraints.ConstraintBuilder;

public abstract class Solver {

	protected final Set<Constraint> constraints;

	public Solver() {
		this.constraints = ConstraintBuilder.buildConstraints();  
	}

	public abstract int solve(int[][] board);

}
