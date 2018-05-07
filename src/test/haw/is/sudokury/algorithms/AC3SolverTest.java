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
	public void makeArcConsMissingBeginning() {
		int x = 0;
		int y = 0;
		board[x][y] = 0;
		Set<Constraint> constraints = solver.getConstraints();
		for (Constraint<Field> constraint : constraints) {
			ConstraintVariable<Field, Integer> var = constraint.getSource();
			if (!var.getVariable().equals(Field.getField(x, y))) {
				int xVar = var.getVariable().getX();
				int yVar = var.getVariable().getY();
				int value = board[xVar][yVar];
				var.getDomain().clear();
				var.getDomain().add(value);
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
		solver.ConstraintsToString(constraints);
		solver.makeArcsConsistent((Collection<? extends Constraint<Field>>) constraints);
		solver.ConstraintsToString(constraints);
		for (Constraint<Field> constraint : constraints) {
			assertEquals(1, constraint.getSource().getDomain().size());
			assertEquals(1, constraint.getTarget().getDomain().size());
		}
	}

	@Test
	public void testReviseFalse() {
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
	public void testReviseTrue() {
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

		Set<Constraint<Field>> constraints = new HashSet<>();
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
}