package pathfinding;

import queue.PriorityQueue;
import java.util.ArrayList;

public class AStarAlgorithm {
    private final Node finish;
    private Node[][] nodes;
    private final int maxCol, maxRow;
    private boolean goalReached;
    private PriorityQueue openList;
    private ArrayList<Node> checkedList;
    private final GridPanel gridPanel;

    public AStarAlgorithm(Node[][] nodes, Node start, Node finish, int maxCol, int maxRow, GridPanel gridPanel) {
        this.nodes = nodes;
        this.finish = finish;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        goalReached = false;
        openList = new PriorityQueue(maxCol * maxRow);
        checkedList = new ArrayList<>();
        this.gridPanel = gridPanel;
        openList.enqueue(start);
    }

    // A* algorithm
    public void AStar() {
        if (openList.isEmpty()) {
            return;
        }

        Node current = openList.dequeue();
        checkedList.add(current);
        current.setSearched();
        gridPanel.nodeSearched++;

        if (current == finish) {
            goalReached = true;
            openList.showQueue();
            return;
        }

        //Check around the current node
        Node[] neighbor = {
                (current.row - 1 >= 0) ? nodes[current.col][current.row - 1] : null,
                (current.row + 1 < maxRow) ? nodes[current.col][current.row + 1] : null,
                (current.col - 1 >= 0) ? nodes[current.col - 1][current.row] : null,
                (current.col + 1 < maxCol) ? nodes[current.col + 1][current.row] : null
        };

        //set parent to trace later
        for (Node node : neighbor) {
            if (node == null || node.wall || checkedList.contains(node)) {
                continue;
            }

            //Calculate the gCost
            int newGCost = current.gCost + getDistance(current, node);

            //Check if the path is shorter
            if (newGCost < node.gCost || !openList.contains(node)) {
                node.gCost = newGCost;
                node.hCost = getDistance(node, finish);
                node.fCost = node.gCost + node.hCost;
                node.parent = current;
                openList.sortQueue();

                //Not shorter but a new path
                if (!openList.contains(node)) {
                    openList.enqueue(node);
                    node.setOpen();
                }

                node.setText(String.format("%d,%d", node.fCost, node.hCost));
            }
        }
    }

    // Calculate Distance
    private int getDistance(Node nodeA, Node nodeB) {
        int distanceX = Math.abs(nodeA.col - nodeB.col);
        int distanceY = Math.abs(nodeA.row - nodeB.row);
        return distanceX + distanceY;
    }

    public boolean isGoalReached() {
        return goalReached;
    }

    public boolean failToReach() {
        return openList.isEmpty();
    }
}
