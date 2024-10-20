package queue;

import pathfinding.Node;

public class PriorityQueue {
    private final Node[] queue;
    private int currentSize;
    private final int maxSize;

    public PriorityQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new Node[maxSize];
        currentSize = 0;
    }

    public void enqueue(Node node) {
        if (currentSize == maxSize) {
            System.out.println("Queue is full!");
            return;
        }
        queue[currentSize] = node;
        currentSize++;
        sortQueue();
    }

    //Sort the queue,insertion sort btw
    //Priority by f cost and by h cost if f cost is equal
    public void sortQueue() {
        for (int i = 1; i < currentSize; i++) {
            Node current = queue[i];
            int j = i - 1;

            while (j >= 0 && (queue[j].fCost > current.fCost || (queue[j].fCost == current.fCost && queue[j].hCost > current.hCost))) {
                queue[j + 1] = queue[j];
                j--;
            }
            queue[j + 1] = current;
        }
    }

    public Node dequeue() {
        if (isEmpty()) {
            System.out.println("Queue empty!");
            return null;
        }
        Node highestPriority = queue[0];

        //Move other index by 1
        for (int i = 1; i < currentSize; i++) {
            queue[i - 1] = queue[i];
        }
        currentSize--;
        return highestPriority;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean contains(Node node) {
        for (int i = 0; i < currentSize; i++) {
            if (queue[i] == node) {
                return true;
            }
        }
        return false;
    }

    public void showQueue() {
        System.out.println("Priority Queue:");
        for (int i = 0; i < currentSize; i++) {
            System.out.printf("Index %d: fCost=%d, hCost=%d \n", i, queue[i].fCost, queue[i].hCost);
        }
    }
}
