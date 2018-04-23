package haw.is.sudokury;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.constraints.ConstraintBuilder;

public class ConstraintBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Set<Constraint> set = ConstraintBuilder.buildConstraints();
		assertNotNull(set);
		assertEquals(set.size(), 81*20);
	}

}
