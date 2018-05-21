package haw.is.sudokury.models.exceptions;

public class BoardAmbiguousException extends RuntimeException {
    public BoardAmbiguousException() {
        super("Board is ambiguous and can be solved in multiple ways.");
    }
}
