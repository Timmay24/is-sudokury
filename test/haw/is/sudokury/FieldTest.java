package haw.is.sudokury;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.models.v2.Field;

public class FieldTest {

	@Before
	public void setUp() throws Exception {
	}

	//teste das einfache Createn
	@Test
	public void testCreateField() {
		Field field = Field.of(2, 1);
		assertNotNull(field);
		assertEquals(2,field.getX());
		assertEquals(1,field.getY());
	}
	
	//teste, ob wirklich die selbe Instanz zur�ck gegeben wird
	@Test
	public void testGetExistingField() {
		Field field = Field.of(1, 1);
		assertNotNull(field);
		assertTrue(Field.of(1, 1) == field);
	}

	//teste, ob bei selber x, aber verschiedener y-koordinate der Prozess funktioniert
	@Test
	public void testCreateFieldYUnknown() {
		Field field1 = Field.of(1, 1);
		assertNotNull(field1);
		Field field2 = Field.of(1, 2);
		assertNotNull(field2);
		assertNotEquals(field1, field2);
		assertEquals(1, field1.getY());
		assertEquals(2, field2.getY());
	}
}
