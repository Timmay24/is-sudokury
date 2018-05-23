package haw.is.sudokury;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.constraints.ConstraintVariable;
import haw.is.sudokury.interfaces.BoardCreator;
import haw.is.sudokury.models.Board;
import haw.is.sudokury.models.Field;
import haw.is.sudokury.models.exceptions.AmbiguousException;
import haw.is.sudokury.models.exceptions.NotSolvableException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class AC3SolverTest {

	AC3Solver solver;
	BoardCreator creator;
	int[][] solvedBoard = new int[][]{
            {7, 8, 3, 1, 9, 5, 2, 6, 4},
            {5, 4, 2, 8, 6, 7, 3, 1, 9},
            {9, 1, 6, 2, 3, 4, 5, 8, 7},
            {8, 3, 1, 6, 2, 9, 7, 4, 5},
            {6, 9, 7, 4, 5, 8, 1, 2, 3},
            {4, 2, 5, 3, 7, 1, 6, 9, 8},
            {2, 7, 4, 9, 1, 3, 8, 5, 6},
            {1, 5, 9, 7, 8, 6, 4, 3, 2},
            {3, 6, 8, 5, 4, 2, 9, 7, 1}};

	@Before
	public void setUp() {
        solver = new AC3Solver();
        creator = new SudokuGenerator();
	}

    @Test
    public void testBoardCopyConstructor() {
        Board original = new Board(creator.nextBoard());
        Board copy = new Board(original);

        assertTrue(original != copy);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                assertTrue(original.getFieldValue(x, y) == copy.getFieldValue(x, y));
                assertTrue(original.getVariable(x, y).equals(copy.getVariable(x, y)));
            }
        }
    }

    @Test
    public void testGeneratedBoardIsCompletelyFilled() {
        Board board = new Board(creator.nextBoard());
        assertTrue(board.isSolved());
    }

    @Test
    public void testSimpleReviseTrue() {
        ConstraintVariable vi = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(1,2,3,7)));
        ConstraintVariable vj = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(2)));

        AC3Solver solver = new AC3Solver();
        assertTrue(solver.revise(vi, vj));
        assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(1,3,7)));
        assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(2)));
    }

	@Test
	public void testSimpleReviseFalse() {
		ConstraintVariable vi = new ConstraintVariable(Field.of(1, 2), new HashSet<>(Arrays.asList(2,3,7)));
		ConstraintVariable vj = new ConstraintVariable(Field.of(1, 3), new HashSet<>(Arrays.asList(1,2)));

		AC3Solver solver = new AC3Solver();
		assertFalse(solver.revise(vi, vj));
		assertEquals(vi.getDomain(), new HashSet<>(Arrays.asList(2,3,7)));
		assertEquals(vj.getDomain(), new HashSet<>(Arrays.asList(1,2)));
	}

    @Test
    public void testReviseTrue() {
        int[][] config = {
                {7, 8, 3, 1, 9, 5, 2, 6, 4},
                {5, 0, 2, 8, 6, 7, 3, 1, 9}, // (1,1) = 4
                {9, 1, 6, 2, 3, 4, 5, 8, 7},
                {8, 3, 1, 6, 2, 9, 7, 4, 5},
                {6, 9, 7, 4, 5, 8, 1, 2, 3},
                {4, 2, 5, 3, 7, 1, 6, 9, 8},
                {2, 7, 4, 9, 1, 3, 8, 5, 6},
                {1, 5, 9, 7, 8, 6, 4, 3, 2},
                {3, 6, 8, 5, 4, 2, 9, 7, 1}};

        ConstraintVariable[][] variables = new ConstraintVariable[9][9];

        // test var
        variables[1][1] = new ConstraintVariable(Field.of(1, 1), new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));

        /* neighbours */
        // same row
        for (int x = 0; x < 9; x++) {
            if (x == 1) continue;
            variables[x][1] = new ConstraintVariable(Field.of(x, 1), new HashSet<>(Arrays.asList(config[x][1])));
            solver.revise(variables[1][1], variables[x][1]);
        }

        // same column
        for (int y = 0; y < 9; y++) {
            if (y == 1) continue;
            variables[1][y] = new ConstraintVariable(Field.of(1, y), new HashSet<>(Arrays.asList(config[1][y])));
            solver.revise(variables[1][1], variables[1][y]);
        }

        // same block
        variables[0][0] = new ConstraintVariable(Field.of(0, 0), new HashSet<>(Arrays.asList(config[0][0])));
        variables[0][2] = new ConstraintVariable(Field.of(0, 2), new HashSet<>(Arrays.asList(config[0][2])));
        variables[2][0] = new ConstraintVariable(Field.of(2, 0), new HashSet<>(Arrays.asList(config[2][0])));
        variables[2][2] = new ConstraintVariable(Field.of(2, 2), new HashSet<>(Arrays.asList(config[2][2])));

        solver.revise(variables[1][1], variables[0][0]);
        solver.revise(variables[1][1], variables[0][2]);
        solver.revise(variables[1][1], variables[2][0]);
        solver.revise(variables[1][1], variables[2][2]);

        // same row
        for (int x = 0; x < 9; x++) {
            if (x == 1) continue;
            assertEquals(variables[x][1].getDomain().size(), 1);
            assertTrue(variables[x][1].getDomain().contains(config[x][1]));
        }

        // same row
        for (int y = 0; y < 9; y++) {
            if (y == 1) continue;
            assertEquals(variables[1][y].getDomain().size(), 1);
            assertTrue(variables[1][y].getDomain().contains(config[1][y]));
        }

        assertEquals(variables[0][0].getDomain().size(), 1);
        assertEquals(variables[0][2].getDomain().size(), 1);
        assertEquals(variables[2][0].getDomain().size(), 1);
        assertEquals(variables[2][2].getDomain().size(), 1);
        assertTrue(variables[0][0].getDomain().contains(config[0][0]));
        assertTrue(variables[0][2].getDomain().contains(config[0][2]));
        assertTrue(variables[2][0].getDomain().contains(config[2][0]));
        assertTrue(variables[2][2].getDomain().contains(config[2][2]));
    }

    @Test
    public void testSolvedBoardIsSolved() {
        List<Board> testBoards = new ArrayList<>();
        testBoards.add(new Board(creator.nextBoard())); // add generated test board
        int[][] config = {
                { 7, 8, 3, 1, 9, 5, 2, 6, 4 },
                { 5, 4, 2, 8, 6, 7, 3, 1, 9 },
                { 9, 1, 6, 2, 3, 4, 5, 8, 7 },
                { 8, 3, 1, 6, 2, 9, 7, 4, 5 },
                { 6, 9, 7, 4, 5, 8, 1, 2, 3 },
                { 4, 2, 5, 3, 7, 1, 6, 9, 8 },
                { 2, 7, 4, 9, 1, 3, 8, 5, 6 },
                { 1, 5, 9, 7, 8, 6, 4, 3, 2 },
                { 3, 6, 8, 5, 4, 2, 9, 7, 1 } };
        testBoards.add(new Board(config)); // add custom test board

        for (Board board : testBoards) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    assertTrue(board.getFieldValue(x, y) != 0);
                    assertTrue(board.getVariable(x, y).getDomain().size() == 1);
                }
            }
            assertTrue(board.isSolved());
        }
    }

    @Test
    public void testNotSolvedBoardIsNotSolved() {
        int[][] blankDiagonal = {
                { 0, 8, 3, 1, 9, 5, 2, 6, 4 },
                { 5, 0, 2, 8, 6, 7, 3, 1, 9 },
                { 9, 1, 0, 2, 3, 4, 5, 8, 7 },
                { 8, 3, 1, 0, 2, 9, 7, 4, 5 },
                { 6, 9, 7, 4, 0, 8, 1, 2, 3 },
                { 4, 2, 5, 3, 7, 0, 6, 9, 8 },
                { 2, 7, 4, 9, 1, 3, 0, 5, 6 },
                { 1, 5, 9, 7, 8, 6, 4, 0, 2 },
                { 3, 6, 8, 5, 4, 2, 9, 7, 0 } };

        Board board = new Board(blankDiagonal);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                boolean fieldIsBlank = board.getFieldValue(x, y) != 0;
                boolean domainSizeIs1 = board.getVariable(x, y).getDomain().size() == 1;
                if (x != y) {
                    assertTrue(fieldIsBlank);
                    assertTrue(domainSizeIs1);
                } else {
                    assertFalse(fieldIsBlank);
                    assertFalse(domainSizeIs1);
                }
            }
        }
        assertFalse(board.isSolved());
    }

    @Test(expected = NotSolvableException.class)
    public void testNotSolvableBoardShouldThrowError() {
        int[][] config = {
                { 7, 8, 3, 1, 9, 5, 2, 6, 4 },
                { 5, 0, 2, 8, 6, 7, 3, 1, 9 }, // 4
                { 9, 1, 6, 2, 3, 4, 5, 8, 7 },
                { 8, 3, 1, 6, 2, 9, 7, 4, 5 },
                { 6, 4, 7, 4, 5, 8, 1, 2, 3 }, // 9 => 4 so (1,4) crashes with (3,4) and (0,5)
                { 4, 2, 5, 3, 7, 1, 6, 9, 8 },
                { 2, 7, 4, 9, 1, 3, 8, 5, 6 },
                { 1, 5, 9, 7, 8, 6, 4, 3, 2 },
                { 3, 6, 8, 5, 4, 2, 9, 7, 1 } };

        Board board = new Board(config);
        solver.solve(board);
    }

    @Test
    public void testSomeSolveableBoard() {
        int[][] config = {
                { 7, 8, 3, 1, 9, 5, 2, 6, 4 },
                { 0, 0, 2, 8, 6, 7, 3, 1, 9 },
                { 9, 1, 0, 2, 3, 4, 5, 8, 7 },
                { 8, 3, 0, 0, 0, 9, 7, 4, 5 },
                { 6, 9, 7, 4, 0, 8, 1, 2, 3 },
                { 4, 2, 5, 3, 0, 1, 6, 9, 8 },
                { 2, 7, 4, 9, 0, 3, 8, 5, 6 },
                { 1, 5, 9, 7, 8, 6, 4, 3, 2 },
                { 3, 6, 8, 5, 4, 2, 9, 7, 1 } };

        Board board = new Board(config);
        int score = solver.solve(board);
        System.out.println(board);
        System.out.println(score);
        assertTrue(score > 0);
    }

    @Test
    @Ignore
    public void testMultipleRandomBoards() {
        for (int i = 0; i < 9; i++) {
            Board board = new Board(creator.nextBoard((int)(Math.random() * ((30 - 9) + 1)) + 9));
            int score = solver.solve(board);
            System.out.println(board);
            System.out.println(score);
            assertTrue(score > 0);
        }
    }

    @Test
    public void test4RandomBoardWith1FieldMissing() {
        for (int i = 0; i < 4; i++) {
            Board board = new Board(creator.nextBoard(1));
            int score = solver.solve(board);
            System.out.println(board);
            System.out.println(score);
            assertTrue(score > 0);
        }
    }

    @Test(expected = AmbiguousException.class)
    public void testBoardWithout1sAnd2sIsAmbiguous() {
        int[][] config = {
                { 7, 8, 3, 0, 9, 5, 0, 6, 4 },
                { 5, 4, 0, 8, 6, 7, 3, 0, 9 },
                { 9, 0, 6, 0, 3, 4, 5, 8, 7 },
                { 8, 3, 0, 6, 0, 9, 7, 4, 5 },
                { 6, 9, 7, 4, 5, 8, 0, 0, 3 },
                { 4, 0, 5, 3, 7, 0, 6, 9, 8 },
                { 0, 7, 4, 9, 0, 3, 8, 5, 6 },
                { 0, 5, 9, 7, 8, 6, 4, 3, 0 },
                { 3, 6, 8, 5, 4, 0, 9, 7, 0 } };

        Board board = new Board(config);
        solver.solve(board);
    }
}