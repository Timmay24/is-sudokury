package haw.is.sudokury.constraints;

import haw.is.sudokury.constraints.interfaces.Constraint;
import haw.is.sudokury.models.v2.Board;
import haw.is.sudokury.models.v2.Field;

import java.util.HashSet;
import java.util.Set;

public class ConstraintBuilder {

    private ConstraintBuilder() {
    }

    public static Set<Constraint> buildConstraints(Board board) {
        FieldConstraintVariable<Field>[][] variables = board.getVariables();
        Set<Constraint> result = new HashSet<>();
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 9; ++y) {

                // alle in selber Zeile
                for (int x2 = 0; x2 < 9; ++x2) {
                    if (x != x2) {
                        result.add(new AllDiffConstraint<>(variables[x][y], variables[x2][y]));
                    }
                }

                // alle in selber Spalte
                for (int y2 = 0; y2 < 9; ++y2) {
                    if (y != y2) {
                        result.add(new AllDiffConstraint<>(variables[x][y], variables[x][y2]));
                    }
                }

                // alle im selben Block
                for (int x2 = 0; x2 < 9; ++x2) {
                    for (int y2 = 0; y2 < 9; ++y2) {
                        if (y != y2 && x != x2 && x / 3 == x2 / 3 && y / 3 == y2 / 3) {
                            result.add(new AllDiffConstraint<>(variables[x][y], variables[x2][y2]));
                        }
                    }
                }
            }
        }
        return result;
    }
}
