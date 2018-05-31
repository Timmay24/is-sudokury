package haw.is.sudokury;

import haw.is.sudokury.algorithms.AC3Solver;

public class Main {

	public static void main(String[] args) {
		AC3Solver solver = new AC3Solver();
		SudokuController sudokuController = new SudokuController(solver, new SudokuGenerator());
		//sudokuController.printBoard(sudokuController.getRoot().getBoard());
    }

}
