package pathfinding;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        int nodePixelSize;
        nodePixelSize = 15;
        // Main Window
        JFrame window = new JFrame("Pathfinding");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setPreferredSize(new Dimension(600, 600));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainGrid = new GridBagConstraints();
        mainGrid.insets = new Insets(10, 10, 10, 10);

        GridPanel gridPanel1 = new GridPanel(nodePixelSize);

        GridBagConstraints infoGrid = new GridBagConstraints();
        infoGrid.insets = new Insets(2, 2, 2, 2);

        // Info box
        JPanel infoPanel1 = new JPanel();
        infoPanel1.setPreferredSize(new Dimension(250, 100));
        infoPanel1.setBackground(Color.lightGray);
        infoPanel1.setLayout(new GridBagLayout());

        JCheckBox switchAlgo1 = new JCheckBox("Maze mode");
        switchAlgo1.setBackground(Color.lightGray);
        switchAlgo1.addActionListener(_ -> {
            if (switchAlgo1.isSelected()) {
                gridPanel1.setAlgorithmType("maze");
            } else {
                gridPanel1.setAlgorithmType("A*");
            }
        });
        JLabel timeLabel1 = new JLabel("Time: 0 ms");
        JLabel searchInfo1 = new JLabel("Searched: 0");
        JLabel pathInfo1 = new JLabel("Length: 0");

        infoGrid.anchor = GridBagConstraints.CENTER;

        infoGrid.gridy = 1;
        infoPanel1.add(switchAlgo1, infoGrid);

        infoGrid.gridy = 2;
        infoPanel1.add(timeLabel1, infoGrid);

        infoGrid.gridy = 3;
        infoPanel1.add(searchInfo1, infoGrid);

        infoGrid.gridy = 4;
        infoPanel1.add(pathInfo1, infoGrid);

        // Start Button
        JButton startButton = new JButton("Start Algorithm");
        startButton.addActionListener(_ -> {
            if (!gridPanel1.isRunning && !gridPanel1.isFinished) {
                gridPanel1.startPathfinding();
            }
        });

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(_ -> {
            gridPanel1.reset();
            timeLabel1.setText("Time: 0 ms");
        });

        // Reset Wall Button
        JButton resetWallButton = new JButton("Reset Wall");
        resetWallButton.addActionListener(_ -> {
            gridPanel1.resetWall();
            timeLabel1.setText("Time: 0 ms");
        });

        // Update Info
        Timer updateTimer = new Timer(25, _ -> {
            boolean isReady = gridPanel1.startNode != null && gridPanel1.finishNode != null && !gridPanel1.isRunning;
            startButton.setEnabled(isReady && !gridPanel1.isFinished);
            resetButton.setEnabled(isReady);
            resetWallButton.setEnabled(isReady);
            searchInfo1.setText("Searched: " + gridPanel1.nodeSearched);
            pathInfo1.setText("Length: " + gridPanel1.pathLength);
            timeLabel1.setText("Time: " + (int) gridPanel1.timeUse + " ms");
        });
        updateTimer.start();

        // Place components in main panel
        mainGrid.gridx = 0;
        mainGrid.gridy = 0;
        mainPanel.add(gridPanel1, mainGrid);

        mainGrid.gridy = 1;
        mainPanel.add(infoPanel1, mainGrid);

        mainGrid.gridy = 2;
        mainGrid.gridwidth = 1;
        mainPanel.add(startButton, mainGrid);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGrid = new GridBagConstraints();
        buttonGrid.insets = new Insets(10, 10, 10, 10);

        buttonGrid.gridx = 0;
        buttonGrid.gridy = 0;
        buttonPanel.add(resetButton, buttonGrid);

        buttonGrid.gridx = 1;
        buttonPanel.add(resetWallButton, buttonGrid);

        mainGrid.gridy = 3;
        mainPanel.add(buttonPanel, mainGrid);

        // Show Main panel
        window.add(mainPanel);
        window.pack();
        window.setVisible(true);
    }
}
