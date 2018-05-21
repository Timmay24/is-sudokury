package haw.is.sudokury.models;

import haw.is.sudokury.constraints.FieldConstraintVariable;

import java.util.*;

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

    public List<FieldConstraintVariable> getOpenFields() {
        List<FieldConstraintVariable> openFields = new ArrayList<>();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (getFieldValue(x, y) == 0) {
                    openFields.add(getVariable(x, y));
                }
            }
        }

        return openFields;
    }

    public boolean isSolved() {
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            occurrences.put(i, 0);
        }

        // Check if any field is empty and count value occurences
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (getFieldValue(x, y) == 0) {
                    return false;
                }
                if (getVariable(x, y).getDomain().isEmpty()) {
                    return false;
                }
                if (getVariable(x, y).getDomain().size() > 1) {
                    return false;
                }
                occurrences.put(getFieldValue(x, y), occurrences.get(getFieldValue(x, y)) + 1);
            }
        }

        // if the board is solved properly, then each value has to occure exactly 9 times
        for (int i = 1; i < 10; i++) {
            if (!occurrences.get(i).equals(9)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasEmptyDomains() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (variables[x][y].getDomain().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean domainsAreDistinct() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (variables[x][y].getDomain().size() != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNotSolvable() {
        return hasEmptyDomains();
    }

    public boolean isAmbiguous() {
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            occurrences.put(i, 0);
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                // Count occurrences of each value
                occurrences.put(getFieldValue(x,y), occurrences.get(getFieldValue(x,y)) + 1);
            }
        }

        int figureMissingCount = 0;
        for (int i = 1; i < 10; i++) {
            if (occurrences.get(i).equals(0)) {
                figureMissingCount++;
                // if more than 1 kind of figures are missing in the whole board
                // (f.e. => there are neither 1's nor 2's)
                // then the board has multiple possible solutions => ambiguous
                if (figureMissingCount > 1)
                    return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String stripline = "------------------------" + "\n";
        String board = "" + stripline;

        for (int y = 0; y < 9; y++) {
            String line = "";
            for (int x = 0; x < 9; x++) {
                line = line + getFieldValue(x,y) + " ";
            }
            board = board + line + "\n";
        }
        board = board + stripline;

        return board;
    }
}
