package haw.is.sudokury.models;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T data;
    private List<Node<T>> children;

    // Additional Attributes
    private int difficulty;

    public Node(T data) {
        children = new ArrayList<>();
        difficulty = 0;
        this.data = data;
    }

    public Node<T> add(T data) {
        Node<T> newChild = new Node<>(data);
        children.add(newChild);
        return newChild;
    }

    public boolean remove(Node<T> child) {
        return children.remove(child);
    }

    // different name for getData for domain specific purpose
    public T getBoard() {
        return data;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}