package haw.is.sudokury;

import java.util.Set;

import haw.is.sudokury.constraints.Constraint;
import haw.is.sudokury.constraints.ConstraintBuilder;

public abstract class Solver {

	protected final Set<Constraint> constraints;

	public Solver() {
		this.constraints = ConstraintBuilder.buildConstriants();  
	}

	public abstract int solve(int[][] board);

}
