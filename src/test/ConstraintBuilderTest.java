package test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import constraint.Constraint;
import constraint.ConstraintBuilder;

public class ConstraintBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Set<Constraint> set = ConstraintBuilder.buildConstriants();
		assertNotNull(set);
	}

}
