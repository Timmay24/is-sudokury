package haw.is.sudokury.models.exceptions;

public class AmbiguousException extends RuntimeException {
    public AmbiguousException() {
        super("Board is ambiguous and can be solved in multiple ways.");
    }
}
