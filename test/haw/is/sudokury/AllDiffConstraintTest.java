package haw.is.sudokury;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import haw.is.sudokury.constraints.AllDiffConstraint;
import haw.is.sudokury.constraints.FieldConstraintVariable;
import haw.is.sudokury.models.Field;

public class AllDiffConstraintTest {

	@Test
	public void testIsConsistent() {
		FieldConstraintVariable f = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable g = new FieldConstraintVariable(Field.getField(1, 3));
		
		AllDiffConstraint<Field> adc = new AllDiffConstraint<>(f,g);
		
		f.getDomain().retainAll(Set.of(1,2,3));
		g.getDomain().retainAll(Set.of(2,3,4));
		assertTrue(adc.isConsistent());
	}

	@Test
	public void testIsNotConsistent() {
		FieldConstraintVariable f = new FieldConstraintVariable(Field.getField(1, 2));
		FieldConstraintVariable g = new FieldConstraintVariable(Field.getField(1, 3));
		
		AllDiffConstraint<Field> adc = new AllDiffConstraint<>(f,g);
		
		f.getDomain().retainAll(Set.of(1,2,3));
		g.getDomain().retainAll(Set.of(2));
		assertFalse(adc.isConsistent());
	}
}
