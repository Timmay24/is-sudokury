package haw.is.sudokury;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Solver {

	protected Map< Field, Set<Field>> constList = new HashMap<>();

	
	public abstract int solve(int[][] board);
}
