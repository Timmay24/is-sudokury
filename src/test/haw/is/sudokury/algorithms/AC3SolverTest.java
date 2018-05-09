package test.haw.is.sudokury.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.SudokuGenerator;
import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.algorithms.Solver;
import haw.is.sudokury.constraints.AllDiffConstraint;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.interfaces.BoardCreator;
import haw.is.sudokury.models.Field;

public class AC3SolverTest {

	AC3Solver solver;
	BoardCreator creator;
	int[][] board;

	@Before
	public void setUp() {
		solver = new AC3Solver();
		creator = new SudokuGenerator();
		board = creator.nextBoard();
	}

	// @Test
	// public void fixOneMissingBeginning() {
	// board[0][0] = 0;
	// assertTrue(solver.solve(board) > 0);
	// }
	@Test
	public void solveBeginning() {
		int x = 0;
		int y = 0;
		board[x][y] = 0;

		solver.solve(board);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void solveDiagonalZero() {
		for (int i = 0; i < 9; ++i) {
			board[i][i] = 0;
		}

		solver.solve(board);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void solveVerticalZero() {
		for (int i = 0; i < 9; ++i) {
			board[i][0] = 0;
		}

		solver.solve(board);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void solveHorizontalZero() {
		for (int i = 0; i < 9; ++i) {
			board[3][i] = 0;
		}

		solver.solve(board);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void solveActuallyNotSolveable() {
		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				board[x][y] = 0;
			}
		}

		solver.solve(board);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(9, constraint.getSource().getDomain().size());
			assertEquals(9, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void makeArcConsMissingBeginning() {
		int x = 0;
		int y = 0;
		board[x][y] = 0;
		ConstraintVariable<Field, Integer> varZero = null;
		Set<Constraint> constraints = solver.getConstraints();
		for (Constraint<Field> constraint : constraints) {
			ConstraintVariable<Field, Integer> var = constraint.getSource();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			} else {
				varZero = var;
			}
			var = constraint.getTarget();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			}
		}
		solver.makeArcsConsistent(constraints);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}

	}

	@Test
	public void makeArcConsMissingMiddle() {
		int x = 5;
		int y = 4;
		board[x][y] = 0;
		ConstraintVariable<Field, Integer> varZero = null;
		Set<Constraint> constraints = solver.getConstraints();
		for (Constraint<Field> constraint : constraints) {
			ConstraintVariable<Field, Integer> var = constraint.getSource();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			} else {
				varZero = var;
			}
			var = constraint.getTarget();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			}
		}
		solver.makeArcsConsistent(constraints);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}

	}

	@Test
	public void makeArcConsMissingEnd() {
		int x = 8;
		int y = 8;
		board[x][y] = 0;
		ConstraintVariable<Field, Integer> varZero = null;
		Set<Constraint> constraints = solver.getConstraints();
		for (Constraint<Field> constraint : constraints) {
			ConstraintVariable<Field, Integer> var = constraint.getSource();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			} else {
				varZero = var;
			}
			var = constraint.getTarget();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
			}
		}
		solver.makeArcsConsistent(constraints);

		for (Constraint<Field> constraint : solver.getConstraints()) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}

	}

	// teste () ()- ergebnis () ()
	@Test
	public void testReviseOne() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));

		vi.getDomain().clear();
		vj.getDomain().clear();

		HashSet<Integer> viComp = new HashSet<>(Arrays.asList());
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList());

		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste (1) ()- ergebnis (1) ()
	@Test
	public void testReviseTwo() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList(1));
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList());
		vi.getDomain().clear();
		vi.getDomain().addAll(viComp);
		vj.getDomain().clear();
		vj.getDomain().addAll(vjComp);
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste () (1)- ergebnis () (1)
	@Test
	public void testReviseThree() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList());
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList(1));
		vi.getDomain().clear();
		vj.getDomain().clear();
		vi.getDomain().addAll(viComp);
		vj.getDomain().addAll(vjComp);
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste (1) (2)- ergebnis (1) (2)
	@Test
	public void testReviseFour() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList(1));
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList(2));
		vi.getDomain().clear();
		vj.getDomain().clear();
		vi.getDomain().addAll(viComp);
		vj.getDomain().addAll(vjComp);
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste (1) (1)- ergebnis () (1)
	@Test
	public void testReviseFive() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		vi.getDomain().clear();
		vj.getDomain().clear();
		HashSet<Integer> viDomain = new HashSet<>(Arrays.asList(1));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList());
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList(1));
		vi.getDomain().addAll(viDomain);
		vj.getDomain().addAll(vjComp);
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste (1,2) (1)- ergebnis (2) (1)
	@Test
	public void testReviseSix() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		HashSet<Integer> viDomain = new HashSet<>(Arrays.asList(1, 2));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList(2));
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList(1));
		vi.getDomain().clear();
		vj.getDomain().clear();
		vi.getDomain().addAll(viDomain);
		vj.getDomain().addAll(vjComp);
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	// teste (1) (1,2)- ergebnis (1) (1,2)
	@Test
	public void testReviseSeven() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		HashSet<Integer> viDomain = new HashSet<>(Arrays.asList(1));
		HashSet<Integer> viComp = new HashSet<>(Arrays.asList(1));
		HashSet<Integer> vjComp = new HashSet<>(Arrays.asList(1, 2));
		vi.getDomain().clear();
		vj.getDomain().clear();
		vi.getDomain().addAll(viDomain);
		vj.getDomain().addAll(vjComp);
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), viComp);
		assertEquals(vj.getDomain(), vjComp);
	}

	@Test
	public void testReviseDeleteNoneOfTwo() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));

		vi.getDomain().retainAll(new HashSet<>(Arrays.asList(2, 3)));
		vj.getDomain().retainAll(new HashSet<>(Arrays.asList(1, 2)));

		AC3Solver solver = new AC3Solver();
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(2, 3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(1, 2)));
	}

	@Test
	public void testReviseDeleteOneOfThree() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));

		vi.getDomain().retainAll(Arrays.asList(1, 2, 3));
		vj.getDomain().retainAll(Arrays.asList(2));

		AC3Solver solver = new AC3Solver();
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1, 3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(2)));
	}

	@Test
	public void testMakeArcsConsistentWith3Fields() {
		// int[][] board = new SudokuGenerator().nextBoard();
		AC3Solver solver = new AC3Solver();
		FieldConstraintVariable a, b, c;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));
		c = new FieldConstraintVariable(Field.getField(1, 0));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1, 2, 3));

		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1, 3));

		c.getDomain().clear();
		c.getDomain().addAll(Arrays.asList(1));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));
		constraints.add(new AllDiffConstraint<Field>(a, c));
		constraints.add(new AllDiffConstraint<Field>(b, a));
		constraints.add(new AllDiffConstraint<Field>(b, c));
		constraints.add(new AllDiffConstraint<Field>(c, a));
		constraints.add(new AllDiffConstraint<Field>(c, b));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(2)));
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(3)));
		assertEquals(c.getDomain(), new HashSet<>(Arrays.asList(1)));
	}

	// test makeArcConst keine Consts - ergebnis läuft durch
	@Test
	public void testMakeArcsConsistentNoConstraints() {

		Set<Constraint> constraints = new HashSet<>();
		solver.makeArcsConsistent(constraints);
		assertTrue(true);
	}

	// test makeArcConst a->b a=() b=()
	@Test
	public void testMakeArcsConsistentOne() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();

		b.getDomain().clear();

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertTrue(a.getDomain().isEmpty());
		assertTrue(b.getDomain().isEmpty());
	}

	// test makeArcConst a->b a=(1) b=()
	@Test
	public void testMakeArcsConsistentTwo() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1));
		b.getDomain().clear();

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(1)));
		assertTrue(b.getDomain().isEmpty());
	}

	// test makeArcConst a->b a=() b=(1)
	@Test
	public void testMakeArcsConsistentThree() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(1)));
		assertTrue(a.getDomain().isEmpty());
	}

	// test makeArcConst a->b a=(1) b=(2)
	@Test
	public void testMakeArcsConsistentFour() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1));
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(2));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(1)));
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(2)));
	}

	// test makeArcConst a->b a=(1) b=(1)
	@Test
	public void testMakeArcsConsistentFive() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1));
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertTrue(a.getDomain().isEmpty());
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(1)));
	}

	// test makeArcConst a->b a=(1,2) b=(1)
	@Test
	public void testMakeArcsConsistentSix() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1, 2));
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(2)));
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(1)));
	}

	// test makeArcConst a->b b->c a=(1,2) b=(1,2) c=(2)
	@Test
	public void testMakeArcsConsistentSeven() {
		FieldConstraintVariable a, b, c;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));
		c = new FieldConstraintVariable(Field.getField(1, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1, 2));
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1, 2));
		c.getDomain().clear();
		c.getDomain().addAll(Arrays.asList(2));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));
		constraints.add(new AllDiffConstraint<Field>(b, c));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(2)));
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(1)));
		assertEquals(c.getDomain(), new HashSet<>(Arrays.asList(2)));
	}

	// test makeArcConst a->b b->c c->a a=(1,2) b=(1,2) c=(2)
	@Test
	public void testMakeArcsConsistentEight() {
		FieldConstraintVariable a, b, c;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));
		c = new FieldConstraintVariable(Field.getField(1, 1));

		a.getDomain().clear();
		a.getDomain().addAll(Arrays.asList(1, 2));
		b.getDomain().clear();
		b.getDomain().addAll(Arrays.asList(1, 2));
		c.getDomain().clear();
		c.getDomain().addAll(Arrays.asList(2));

		Set<Constraint> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));
		constraints.add(new AllDiffConstraint<Field>(b, c));
		constraints.add(new AllDiffConstraint<Field>(c, a));

		solver.makeArcsConsistent(constraints);

		assertEquals(a.getDomain(), new HashSet<>(Arrays.asList(2)));
		assertEquals(b.getDomain(), new HashSet<>(Arrays.asList(1)));
		assertTrue(c.getDomain().isEmpty());
	}
}