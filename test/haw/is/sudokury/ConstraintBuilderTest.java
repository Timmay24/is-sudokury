package haw.is.sudokury;

import haw.is.sudokury.constraints.ConstraintBuilder;
import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConstraintBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Set<Constraint> set = ConstraintBuilder.buildConstraints(new Board());
		assertNotNull(set);
		assertEquals(set.size(), 81*20);
	}

}
