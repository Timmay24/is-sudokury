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
		
		assertFalse(adc.isConsistent());
		f.getDomain().removeAll(Set.of(5,6,7,8,9)); 	// f {1,2,3,4}
		g.getDomain().removeAll(Set.of(1,2,3));			// g {4,5,6,7,8,9}
		assertFalse(adc.isConsistent());
		f.getDomain().remove(4);						// f {1,2,3}
		assertTrue(adc.isConsistent());
	}

}
