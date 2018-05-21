package haw.is.sudokury.models.exceptions;

public class NotSolvableException extends RuntimeException {
    public NotSolvableException() {
        super("Given board is not solvable.");
    }
}
