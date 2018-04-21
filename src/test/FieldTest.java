package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.Field;

public class FieldTest {

	@Before
	public void setUp() throws Exception {
	}

	//teste das einfache Createn
	@Test
	public void testCreateField() {
		Field field = Field.getField(2, 1);
		assertNotNull(field);
		assertEquals(2,field.getX());
		assertEquals(1,field.getY());
	}
	
	//teste, ob wirklich die selbe Instanz zurück gegeben wird
	@Test
	public void testGetExistingField() {
		Field field = Field.getField(1, 1);
		assertNotNull(field);
		assertTrue(Field.getField(1, 1) == field);
	}

	//teste, ob bei selber x, aber verschiedener y-koordinate der Prozess funktioniert
	@Test
	public void testCreateFieldYUnknown() {
		Field field1 = Field.getField(1, 1);
		assertNotNull(field1);
		Field field2 = Field.getField(1, 2);
		assertNotNull(field2);
		assertNotEquals(field1, field2);
		assertEquals(1, field1.getY());
		assertEquals(2, field2.getY());
	}
}
