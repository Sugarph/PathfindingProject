package pathfinding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {
    Node parent;
    public boolean startPoint;
    public boolean finishPoint;
    boolean wall;
    boolean searched;
    boolean open;
    public int gCost;
    public int fCost;
    public int hCost;
    public int col;
    public int row;

    private final GridPanel gridPanel;

    public Node(int col, int row, GridPanel gridPanel) {
        this.col = col;
        this.row = row;
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        addActionListener(this);
        this.gridPanel = gridPanel;

    }

    public void setStartPoint() {
        setText("");
        setBackground(Color.green);
        startPoint = true;
    }

    public void setFinishPoint() {
        setText("");
        setBackground(Color.red);
        finishPoint = true;
    }

    public void toggleWall() {
        if (finishPoint || startPoint || open || searched) {
            return;
        }
        wall = !wall;
        setBackground(wall ? Color.black : Color.white);
    }

    //Open to be search
    public void setOpen() {
        this.setBackground(Color.orange);
        this.setForeground(Color.black);
        open = true;
    }

    public void setSearched() {
        this.setBackground(Color.blue);
        this.setForeground(Color.black);
        searched = true;
    }

    public void setPath() {
        this.setBackground(Color.green);
        this.setForeground(Color.black);
    }

    public void reset() {
        this.parent = null;
        this.wall = false;
        this.searched = false;
        this.open = false;
        this.gCost = 0;
        this.fCost = 0;
        this.hCost = 0;
        setText("");

        setBackground(Color.white);
        setForeground(Color.white);
    }

    public void buttonPressed() {
        if (gridPanel.startNode == null) {
            if (finishPoint) { return; }
            setStartPoint();
            gridPanel.startNode = this;
        } else if (gridPanel.finishNode == null) {
            if (startPoint) { return; }
            setFinishPoint();
            gridPanel.finishNode = this;
        } else if (startPoint) {
            startPoint = false;
            gridPanel.startNode = null;
            reset();
        } else if (finishPoint) {
            finishPoint = false;
            gridPanel.finishNode = null;
            reset();
        } else {
            toggleWall();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gridPanel.isRunning) {return;}
        buttonPressed();
        gridPanel.otherNodePressed(col, row);
    }
}
