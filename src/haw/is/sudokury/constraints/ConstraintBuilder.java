package haw.is.sudokury.constraints;

import java.util.HashSet;
import java.util.Set;

import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.Field;

public class ConstraintBuilder {

	private ConstraintBuilder() {
	}

	public static Set<Constraint> buildConstraints() {
		Set<Constraint> result = new HashSet<>();
		Set<ConstraintVariable<Field, Integer>> variables = new HashSet<>();
		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				variables.add(new FieldConstraintVariable(Field.getField(x, y)));
			}
		}
		for (ConstraintVariable<Field, Integer> source : variables) {
			for (ConstraintVariable<Field, Integer> target : variables) {
				int sx = source.getVariable().getX();
				int sy = source.getVariable().getY();
				int tx = target.getVariable().getX();
				int ty = target.getVariable().getY();
				if (!source.equals(target) && (sy == ty || sx == tx)
						|| (sy != ty && sx != tx && sx / 3 == tx / 3 && sy / 3 == ty / 3)) {
					result.add(new AllDiffConstraint<Field>(source, target));
				}
			}

		}
		return result;
	}
}
