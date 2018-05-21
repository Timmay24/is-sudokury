package haw.is.sudokury;

import haw.is.sudokury.constraints.AllDiffConstraint;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.models.Field;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllDiffConstraintTest {

	@Test
	public void testIsConsistent() {
		ConstraintVariable f = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(1,2,3)));
		ConstraintVariable g = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(2,3,4)));
		
		AllDiffConstraint<Field> adc = new AllDiffConstraint<>(f,g);
		
		f.getDomain().retainAll(new HashSet<>(Arrays.asList(1,2,3)));
		g.getDomain().retainAll(new HashSet<>(Arrays.asList(2,3,4)));
		assertTrue(adc.isConsistent());
	}

	@Test
	public void testIsNotConsistent() {
		ConstraintVariable f = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(1,2,3)));
		ConstraintVariable g = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(2)));
		
		AllDiffConstraint<Field> adc = new AllDiffConstraint<>(f,g);
		
		f.getDomain().retainAll(new HashSet<>(Arrays.asList(1,2,3)));
		g.getDomain().retainAll(new HashSet<>(Arrays.asList(2)));
		assertFalse(adc.isConsistent());
	}
}
