package haw.is.sudokury.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.models.Field;

public class AC3SolverTest {

	@Test
	public void testReviseTrue() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		
		vi.getDomain().retainAll(Set.of(1,2,3,4,7,8));
		vj.getDomain().retainAll(Set.of(4,5,6,7,9));
		
		AC3Solver solver = new AC3Solver();
		assertTrue(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1,2,3,8)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(4,5,6,7,9)));
	}
	
	@Test
	public void testReviseFalse() {
		FieldConstraintVariable vi = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable vj = new FieldConstraintVariable(Field.getField(1, 3));
		
		vi.getDomain().retainAll(Set.of(1,2,5,6));
		vj.getDomain().retainAll(Set.of(3,7));
		
		AC3Solver solver = new AC3Solver();
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1,2,5,6)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(3,7)));
	}

}
