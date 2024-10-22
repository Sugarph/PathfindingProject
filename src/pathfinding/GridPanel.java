package pathfinding;

import queue.Deque;
import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    public Node[][] nodes;
    private final int maxCol, maxRow;
    private AStarAlgorithm aStarAlgorithm;
    private MazeDFSAlgorithm dfsMaze;
    private Timer timer;
    public Node startNode, finishNode;
    public boolean isRunning;
    public boolean isFinished;
    public boolean maze;
    private double startTime;
    public double timeUse;
    private final Deque traceQueue;
    public int nodeSearched, pathLength;

    GridPanel(int nodeSize) {
        int panelSize = 250;
        maxCol = panelSize / nodeSize;
        maxRow = panelSize / nodeSize;
        nodes = new Node[maxCol][maxRow];
        setPreferredSize(new Dimension(panelSize, panelSize));
        setLayout(new GridLayout(maxRow, maxCol));
        traceQueue = new Deque(maxCol * maxRow);
        nodeSearched = 0;
        pathLength = 0;
        isRunning = false;
        isFinished = false;
        maze = false;

        //Create grid of node
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                nodes[col][row] = new Node(col, row, this);
                nodes[col][row].setPreferredSize(new Dimension(nodeSize, nodeSize));
                this.add(nodes[col][row]);
            }
        }
    }

    public void setAlgorithmType(String algorithm) {
        if (algorithm.equals("maze")) {
            maze = true;
        } else if (algorithm.equals("A*")) {
            maze = false;
        }
    }

    public void startPathfinding() {
        if (isRunning) return;
        isRunning = true;
        startTime = System.currentTimeMillis();
        if (maze) {
            dfsMaze = new MazeDFSAlgorithm(nodes, startNode, maxCol, maxRow);
            startMaze();
        } else {
            aStarAlgorithm = new AStarAlgorithm(nodes, startNode, finishNode, maxCol, maxRow, this);
            startAStar();
        }
    }
    private void startMaze() {
        timer = new Timer(25, e -> {
            timeUse = System.currentTimeMillis() - startTime;
            dfsMaze.generateMazeStep();
            repaint();
            if (dfsMaze.isFinished()) {
                ((Timer) e.getSource()).stop();
                finishNode = dfsMaze.finishNode;
                isRunning = false;
            }

        });
        timer.start();
    }

    private void startAStar() {
        timer = new Timer(25, e -> {
            timeUse = System.currentTimeMillis() - startTime;
            aStarAlgorithm.AStar();
            repaint();

            if (aStarAlgorithm.isGoalReached()) {
                ((Timer) e.getSource()).stop();
                tracePath(finishNode);
            } else if (aStarAlgorithm.failToReach()) {
                reset();
            }
        });
        timer.start();
    }

    private void tracePath(Node finishNode) {
        Node current = finishNode;
        while (current != null) {
            traceQueue.addFirst(current);
            current = current.parent;
        }
        traceQueue.showDeque();

        //set path from both way
        Timer traceTimer = new Timer(5, e -> {
            if (!traceQueue.isEmpty()) {
                Node frontNode = traceQueue.removeFirst();
                frontNode.setPath();
                pathLength++;

                if (!traceQueue.isEmpty()) {
                    Node backNode = traceQueue.removeLast();
                    backNode.setPath();
                    pathLength++;
                }
            } else {
                ((Timer) e.getSource()).stop();
                isRunning = false;
                isFinished = true;

                timeUse = System.currentTimeMillis() - startTime;
            }
        });

        traceTimer.start();
    }

    public void reset() {
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                if (!nodes[col][row].wall) {
                    nodes[col][row].reset();
                }
            }
        }
        startNode.setStartPoint();
        finishNode.setFinishPoint();
        timeUse = 0;
        nodeSearched = 0;
        pathLength = 0;
        isFinished = false;
        isRunning = false;
    }

    public void resetWall() {
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                nodes[col][row].reset();
            }
        }
        startNode.setStartPoint();
        finishNode.setFinishPoint();
        timeUse = 0;
        nodeSearched = 0;
        pathLength = 0;
        isFinished = false;
    }

}
