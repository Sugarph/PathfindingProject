package queue;

import pathfinding.Node;

public class Deque {
    private Node[] deque;
    private int currentSize;
    private int maxSize;

    public Deque(int maxSize) {
        this.maxSize = maxSize;
        deque = new Node[maxSize];
        currentSize = 0;
    }

    public void addFirst(Node node) {
        if (currentSize == maxSize) {
            System.out.println("The deque is full!");
            return;
        }
        //move back other index by 1
        for (int i = currentSize; i > 0; i--) {
            deque[i] = deque[i - 1];
        }
        deque[0] = node;
        currentSize++;
    }

    public void addLast(Node node) {
        if (currentSize == maxSize) {
            System.out.println("The deque is full!");
            return;
        }
        deque[currentSize] = node;
        currentSize++;
    }

    public Node removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        Node removedNode = deque[0];
        for (int i = 1; i < currentSize; i++) {
            deque[i - 1] = deque[i];
        }
        currentSize--;
        return removedNode;
    }

    public Node removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        Node removedNode = deque[currentSize - 1];
        currentSize--;
        return removedNode;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean contains(Node node) {
        for (int i = 0; i < currentSize; i++) {
            if (deque[i] == node) {
                return true;
            }
        }
        return false;
    }

    public void showDeque() {
        System.out.println("Deque:");
        for (int i = 0; i < currentSize; i++) {
            System.out.printf("Index %d: col=%d, row=%d\n", i, deque[i].row, deque[i].col);
        }
    }
}
