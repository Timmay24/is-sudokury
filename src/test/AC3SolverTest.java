package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import haw.is.sudokury.AC3Solver;
import haw.is.sudokury.Field;
import haw.is.sudokury.SudokuGenerator;

public class AC3SolverTest {
/*
	private int[][] board;
	private AC3Solver solver;
	private Field f1_1, f1_3, f5_4;
	
	@Before
	public void setUp() throws Exception {
		board = new SudokuGenerator().nextBoard();
		board[1][1] = 0;
		board[1][3] = 0;
		board[5][4] = 0;
		solver = new AC3Solver();
		f1_1 = Field.getField(1, 1);
		f1_3 = Field.getField(1, 3);
		f5_4 = Field.getField(5, 4);
		
	}
	
	@Test
	public void testFieldIsEmpty() {
		AC3Solver solver = new AC3Solver();
		int[][] board = new SudokuGenerator().nextBoard();
		board[0][1] = 0;
		solver.solve(board);
		
		assertTrue(solver.isFieldEmpty(Field.getField(0, 1)));
		assertFalse(solver.isFieldEmpty(Field.getField(0, 0)));
	}
*/
}
