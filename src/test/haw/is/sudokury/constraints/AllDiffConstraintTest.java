package test.haw.is.sudokury.constraints;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
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

public class AllDiffConstraintTest {

	Solver solver;
	BoardCreator creator;
	int[][] board;

	@Before
	public void setUp() {
		solver = new AC3Solver();
		creator = new SudokuGenerator();
		board = creator.nextBoard();
	}

	// test cloneConstraint
	@Test
	public void testCloneConstraint() {
		FieldConstraintVariable a, b;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(1, 0));
		Constraint constraint = new AllDiffConstraint<Field>(a, b);
		Constraint cloned = constraint.cloneConst();
		assertTrue(constraint != cloned);
		assertTrue(constraint.getSource() != cloned.getSource());
		assertTrue(constraint.getTarget() != cloned.getTarget());
	}
}
