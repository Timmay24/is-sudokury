package haw.is.sudokury.models.v2;

import haw.is.sudokury.constraints.FieldConstraintVariable;

import java.util.Arrays;

public class Board {
    private Field[][] fields = new Field[9][9];
    private FieldConstraintVariable<Field>[][] variables = new FieldConstraintVariable[9][9];

    // TODO Board hält Felder OK
    // TODO Board hält Constraint Variablen OK
    // TODO Board nimmt Belegung per Array entgegen und setzt entsprechend Feldwerte und Constraintvariablen Domains OK

    public Board(int[][] configuration) {
        //fields = new Field[9][9];
        //variables = new FieldConstraintVariable[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                fields[x][y] = Field.of(x, y, configuration[x][y]);
                if (configuration[x][y] == 0) {
                    variables[x][y] = new FieldConstraintVariable<>(fields[x][y]); // Variable wird mit Domain(1..9) initialisiert.
                } else {
                    variables[x][y] = new FieldConstraintVariable<>(fields[x][y], Integer.valueOf(configuration[x][y]));
                }
            }
        }
    }

    public Board(Board oldBoard) {
        //fields = new Field[9][9];
        //variables = new FieldConstraintVariable[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                fields[x][y] = Field.of(x,y, oldBoard.fields[x][y].getValue());
                if (fields[x][y].getValue() == 0) {
                    variables[x][y] = new FieldConstraintVariable<>(fields[x][y]);
                } else {
                    variables[x][y] = new FieldConstraintVariable<>(fields[x][y], fields[x][y].getValue());
                }
            }
        }
    }

    public Board() {
        //fields = new Field[9][9];
        //variables = new FieldConstraintVariable[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                fields[x][y] = Field.of(x,y);
                variables[x][y] = new FieldConstraintVariable<>(fields[x][y]);
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public Field getField(int x, int y) {
        return fields[x][y];
    }

    public FieldConstraintVariable<Field>[][] getVariables() {
        return variables;
    }

    public FieldConstraintVariable<Field> getVariable(int x, int y) {
        return getVariables()[x][y];
    }

    public void setFieldValue(int x, int y, int value) {
        getField(x, y).setValue(value);
        getVariable(x, y).getDomain().clear();

        if (value == 0) {
            // Bei leerem Feld, den möglichen Wertebereich auf 1..9 setzen
            getVariable(x, y).getDomain().addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
        } else {
            // Bei belegtem Feld, den Wertebereich auf Feldwert setzen
            getVariable(x, y).getDomain().add(value);
        }
    }

    public int getFieldValue(int x, int y) {
        return getField(x, y).getValue();
    }

    public void eraseFieldValue(int x, int y) {
        setFieldValue(x,y,0);
    }

    public int[][] toArray() {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = fields[x][y].getValue();
            }
        }
        return result;
    }
}
