package test.haw.is.sudokury.constraints;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.constraints.ConstraintBuilder;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.constraints.interfaces.Constraint;

public class ConstraintBuilderTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testSourceAndTargetSameIdentity() {
		Set<Constraint> constraints = ConstraintBuilder.buildConstraints();
		for (Constraint constraint : constraints) {
			ConstraintVariable source = constraint.getSource();
			for (Constraint constraintDeu : constraints) {
				ConstraintVariable target = constraintDeu.getTarget();
				if (source.getVariable().equals(target.getVariable())) {
					assertTrue(source == target);
				}
			}
		}
	}

}
