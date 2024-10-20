package pathfinding;

import queue.Deque;
import java.util.ArrayList;

public class BFSAlgorithm {
    private final Node finish;
    private final Node[][] nodes;
    private final int maxCol, maxRow;
    private boolean goalReached;
    private final Deque queue; // Use as normal queue
    private final ArrayList<Node> checkedList;
    private final GridPanel gridPanel;

    public BFSAlgorithm(Node[][] nodes, Node start, Node finish, int maxCol, int maxRow, GridPanel gridPanel) {
        this.nodes = nodes;
        this.finish = finish;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        goalReached = false;
        queue = new Deque(maxCol * maxRow);
        checkedList = new ArrayList<>();
        this.gridPanel = gridPanel;
        queue.addLast(start);
    }

    //breadth first search algorithm
    public void BFS() {
        if (queue.isEmpty()) {
            return;
        }

        Node current = queue.removeFirst();
        checkedList.add(current);
        current.setSearched();
        gridPanel.nodeSearched++;

        if (current == finish) {
            goalReached = true;
            queue.showDeque();
            return;
        }

        // Check around the current node
        Node[] neighbors = {
                (current.row - 1 >= 0) ? nodes[current.col][current.row - 1] : null, // up
                (current.row + 1 < maxRow) ? nodes[current.col][current.row + 1] : null, // down
                (current.col - 1 >= 0) ? nodes[current.col - 1][current.row] : null, // left
                (current.col + 1 < maxCol) ? nodes[current.col + 1][current.row] : null // right
        };

        //set parent for trace later
        for (Node neighbor : neighbors) {
            if (neighbor == null || neighbor.wall || checkedList.contains(neighbor)) {
                continue;
            }

            if (!queue.contains(neighbor)) {
                neighbor.parent = current;
                queue.addLast(neighbor);
                neighbor.setOpen();
            }
        }
    }

    public boolean isGoalReached() {
        return goalReached;
    }

    public boolean failToReach(){
        return queue.isEmpty();
    }

}
