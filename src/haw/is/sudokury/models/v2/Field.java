package haw.is.sudokury.models.v2;

import java.util.Objects;

public class Field {
    private int x, y; // defs position
    private int value;

    public Field(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public static Field of(int x, int y, int value) {
        return new Field(x, y, value);
    }

    public static Field of(int x, int y) {
        return of(x,y,0);
    }

    public Field(int x, int y) {
        this(x,y,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return x == field.x &&
                y == field.y &&
                value == field.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, value);
    }
}
