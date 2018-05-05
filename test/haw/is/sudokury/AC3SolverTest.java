package haw.is.sudokury;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.models.v2.Field;
import haw.is.sudokury.models.v2.Board;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AC3SolverTest {

	@Test
	public void testReviseFalse() {
		ConstraintVariable vi = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(2,3)));
		ConstraintVariable vj = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(1,2)));
		
		vi.getDomain().retainAll(new HashSet<>(Arrays.asList(2,3)));
		vj.getDomain().retainAll(new HashSet<>(Arrays.asList(1,2)));
		
		AC3Solver solver = new AC3Solver();
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(2,3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(1,2)));
	}
	
	@Test
	public void testReviseTrue() {
		ConstraintVariable vi = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(1,2,3)));
		ConstraintVariable vj = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(2)));
		
		vi.getDomain().retainAll(new HashSet<>(Arrays.asList(1,2,3)));
		vj.getDomain().retainAll(new HashSet<>(Arrays.asList(2)));
		
		AC3Solver solver = new AC3Solver();
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1,3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(2)));
	}

	@Test
    public void testGeneratedBoardWithThreeBlankFields() {
	    Board testBoard = new Board(new SudokuGenerator().nextBoard());
	    testBoard.eraseFieldValue(1,1);
	    testBoard.eraseFieldValue(4,5);
	    testBoard.eraseFieldValue(7,3);

	    AC3Solver solver = new AC3Solver();
	    int score = solver.solve(testBoard);
        System.out.println("Testscore: " + score);
	    assertTrue(score != 1);
    }
	
	/*
	@Test
	public void testMakeArcsConsistentWith3Fields() {
		//int[][] board = new SudokuGenerator().nextBoard();
		AC3Solver solver = new AC3Solver();
		ConstraintVariable<Field, Integer> a, b, c;
		a = new ConstraintVariable<>(Field.getField(0, 0));
		b = new ConstraintVariable<>(Field.getField(0, 1));
		c = new ConstraintVariable<>(Field.getField(1, 0));
		
		a.getDomain().clear();
		a.getDomain().addAll(Set.of(1,2,3));
		
		b.getDomain().clear();
		b.getDomain().addAll(Set.of(1,3));
		
		c.getDomain().clear();
		c.getDomain().addAll(Set.of(1));
		
		Set<Constraint<Field>> constraints = new HashSet<>();
		constraints.add(new AllDiffConstraint<Field>(a, b));
		constraints.add(new AllDiffConstraint<Field>(a, c));
		constraints.add(new AllDiffConstraint<Field>(b, a));
		constraints.add(new AllDiffConstraint<Field>(b, c));
		constraints.add(new AllDiffConstraint<Field>(c, a));
		constraints.add(new AllDiffConstraint<Field>(c, b));
		
		solver.makeArcsConsistent(constraints);
		
		assertEquals(a.getDomain(), Set.of(2));
		assertEquals(b.getDomain(), Set.of(3));
		assertEquals(c.getDomain(), Set.of(1));
	}
	*/
}
