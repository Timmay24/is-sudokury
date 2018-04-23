package haw.is.sudokury;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import constraint.Constraint;

public abstract class Solver {

	protected final Set<Constraint> constraints;

	public Solver(Set<Constraint> constraints) {
		this.constraints = constraints;
		
	}

	public abstract int solve(int[][] board);

}
