package haw.is.sudokury.interfaces;

public interface BoardCreator {
	int[][] nextBoard();
	int[][] nextBoard(int difficult);
}
