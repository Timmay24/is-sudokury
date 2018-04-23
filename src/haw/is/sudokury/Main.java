package haw.is.sudokury;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.algorithms.Solver;

public class Main {

	public static void main(String[] args) {
		Solver solver = new AC3Solver();
		Controller controller = new Controller(solver, new SudokuGenerator());
		controller.printBoard(controller.getRoot().getBoard());
	}

}
