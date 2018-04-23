package haw.is.sudokury;

import java.util.HashSet;
import java.util.Set;

public class Controller {
	private FieldCreator boardCreator;
	private final Node<int[][]> root;
	private Solver solver;
	Set<int[][]> closedList = new HashSet<>();

	public Controller(Solver solver, FieldCreator fieldCreator) {
		this.solver = solver;
		boardCreator = fieldCreator;
		root = new Node<>(boardCreator.nextBoard());
	}

	public Node<int[][]> removeField(Node<int[][]> node, Field field) {
		int[][] board = deepCloneBoard(node.getBoard());
		board[field.getX()][field.getY()] = 0;
		if (closedList.contains(board)) {
			return null;
		}
		int difficulty = solver.solve(board);
		if (difficulty == 0) {
			closedList.add(board);
			return null;
		}
		Node<int[][]> childNode = node.add(board);
		childNode.setDifficulty(difficulty);
		return childNode;
	}

	private int[][] deepCloneBoard(int[][] oldArray) {
		int[][] clonedArray = new int[oldArray.length][oldArray.length];
		for (int x = 0; x < oldArray.length; ++x) {
			clonedArray[x] = oldArray[x].clone();
		}
		return clonedArray;
	}
	
	public void printBoard(int[][] board){
		for (int y = 0; y < 9; ++y) {
			for (int x = 0; x < 9; ++x) {
				System.out.print(board[x][y]);
				if(x==8){
					System.out.print("\n");
				}
				else {
					System.out.print(" ");
				}
			}
		}
	}

	public Node<int[][]> getRoot() {
		return root;
	}
}
