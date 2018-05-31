package haw.is.sudokury;

import haw.is.sudokury.algorithms.AC3Solver;
import haw.is.sudokury.annotations.Untested;
import haw.is.sudokury.interfaces.BoardCreator;
import haw.is.sudokury.models.Board;
import haw.is.sudokury.models.Node;

import java.util.*;

public class SudokuController {
	private BoardCreator boardCreator;
	private final Node<Board> root;
	private AC3Solver solver;
	Set<Board> closedList = new HashSet<>();

	public SudokuController(AC3Solver solver, BoardCreator boardCreator) {
		this.solver = solver;
		this.boardCreator = boardCreator;
		Board board = new Board(boardCreator.nextBoard());
		root = new Node<>(board);
	}

    @Untested
	public List<Node<Board>> listAllNodes() {
        return listAllNodes(getRoot());
    }

    @Untested
    public List<Node<Board>> listAllNodes(Node<Board> root) {
	    if (root.isLeaf()) {
	        return Arrays.asList(root);
        } else {
            List<Node<Board>> nodes = new ArrayList<>();
            for (Node<Board> node : root.getChildren()) {
                nodes.addAll(listAllNodes(node));
            }
            return nodes;
        }
    }

	// TODO Board zum Baum hinzufügen, danach Variationen darunter einhängen, in denen Felder gelöscht sind (wie genau? Strategie?)
    // (?)  TODO Ein Board leichter kategorisierbar machen =>
    //      TODO METRIK dafür, wie die Quadranten/Spalten/Zeilen beschaffen sind, wie viele Felder frei sind
    //      TODO
    // TODO Methode (die ListAll sortierter weise benutzt, um den höchsten Score zu ermitteln)
    // TODO ListAll() mäßige Methode, um alle Variationen eines Baumes in einer Liste zu sammeln
    // TODO Board: (De)Serialisierung, Zustand eines Boardes textlich speichern zu können
    //      => nützlich für Auflistung in Tabelle?

	public Node<Board> getRoot() {
		return root;
	}
}
