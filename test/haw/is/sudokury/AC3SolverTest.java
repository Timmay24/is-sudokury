package haw.is.sudokury;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.constraints.AllDiffConstraint;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Field;

public class AC3SolverTest {

	@Test
	public void testReviseFalse() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		
		vi.getDomain().retainAll(Set.of(2,3));
		vj.getDomain().retainAll(Set.of(1,2));
		
		AC3Solver solver = new AC3Solver();
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(2,3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(1,2)));
	}
	
	@Test
	public void testReviseTrue() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		
		vi.getDomain().retainAll(Set.of(1,2,3));
		vj.getDomain().retainAll(Set.of(2));
		
		AC3Solver solver = new AC3Solver();
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1,3)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(2)));
	}
	
	@Test
	public void testMakeArcsConsistentWith3Fields() {
		//int[][] board = new SudokuGenerator().nextBoard();
		AC3Solver solver = new AC3Solver();
		FieldConstraintVariable a, b, c;
		a = new FieldConstraintVariable(Field.getField(0, 0));
		b = new FieldConstraintVariable(Field.getField(0, 1));
		c = new FieldConstraintVariable(Field.getField(1, 0));
		
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
}
