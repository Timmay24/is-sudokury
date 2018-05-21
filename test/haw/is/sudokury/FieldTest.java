package haw.is.sudokury;

import haw.is.sudokury.models.Field;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FieldTest {

	@Test
	public void testCreateField() {
		Field field = Field.of(2, 1);
		assertNotNull(field);
		assertEquals(2,field.getX());
		assertEquals(1,field.getY());
	}
}
