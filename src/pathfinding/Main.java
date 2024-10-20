package pathfinding;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        int nodePixelSize;
        String x = JOptionPane.showInputDialog(null,"Pixel Size");
        try {
            int inputSize = Integer.parseInt(x);
            nodePixelSize = Math.max(10, Math.min(60, inputSize));
        } catch (NumberFormatException e) {
            nodePixelSize = 40;
        }
        //Main Window
        JFrame window = new JFrame("Pathfinding Comparison");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setPreferredSize(new Dimension(960, 540));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainGrid = new GridBagConstraints();
        mainGrid.insets = new Insets(10, 10, 10, 10);

        GridPanel gridPanel1 = new GridPanel(nodePixelSize);
        GridPanel gridPanel2 = new GridPanel(nodePixelSize);

        GridBagConstraints infoGrid = new GridBagConstraints();
        infoGrid.insets = new Insets(2, 2, 2, 2);

        //Info box 1
        JPanel infoPanel1 = new JPanel();
        infoPanel1.setPreferredSize(new Dimension(250, 100));
        infoPanel1.setBackground(Color.lightGray);
        infoPanel1.setLayout(new GridBagLayout());

        JCheckBox switchAlgo1 = new JCheckBox("Use BFS");
        switchAlgo1.setBackground(Color.lightGray);
        switchAlgo1.addActionListener(e -> {
            if (switchAlgo1.isSelected()) {
                gridPanel1.setAlgorithmType("BFS");
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

        //Info box 2
        JPanel infoPanel2 = new JPanel();
        infoPanel2.setPreferredSize(new Dimension(250, 100));
        infoPanel2.setBackground(Color.lightGray);
        infoPanel2.setLayout(new GridBagLayout());


        JCheckBox switchAlgo2 = new JCheckBox("Use BFS");
        switchAlgo2.setBackground(Color.lightGray);
        switchAlgo2.addActionListener(e -> {
            if (switchAlgo2.isSelected()) {
                gridPanel2.setAlgorithmType("BFS");
            } else {
                gridPanel2.setAlgorithmType("A*");
            }
        });

        JLabel timeLabel2 = new JLabel("Time: 0 ms");
        JLabel searchInfo2 = new JLabel("Searched: 0");
        JLabel pathInfo2 = new JLabel("Length: 0");

        //Place things to main panel
        infoGrid.gridx = 0;
        infoGrid.anchor = GridBagConstraints.CENTER;

        infoGrid.gridy = 1;
        infoPanel2.add(switchAlgo2, infoGrid);

        infoGrid.gridy = 2;
        infoPanel2.add(timeLabel2, infoGrid);

        infoGrid.gridy = 3;
        infoPanel2.add(searchInfo2, infoGrid);

        infoGrid.gridy = 4;
        infoPanel2.add(pathInfo2, infoGrid);

        gridPanel1.setOtherGridPanel(gridPanel2);
        gridPanel2.setOtherGridPanel(gridPanel1);

        //Start Button
        JButton startButtonBoth = new JButton("Start Algorithms");
        startButtonBoth.addActionListener(e -> {
            if (!gridPanel1.isRunning && !gridPanel2.isRunning || !gridPanel1.isFinished || !gridPanel2.isFinished) {
                gridPanel1.startPathfinding();
                gridPanel2.startPathfinding();
            }
        });

        //Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            gridPanel1.reset();
            gridPanel2.reset();
            timeLabel1.setText("Time: 0 ms");
            timeLabel2.setText("Time: 0 ms");
        });

        //Reset All Button
        JButton resetWallButton = new JButton("Reset Wall");
        resetWallButton.addActionListener(e -> {
            gridPanel1.resetWall();
            gridPanel2.resetWall();
            timeLabel1.setText("Time: 0 ms");
            timeLabel2.setText("Time: 0 ms");
        });

        //Update Info
        Timer updateTimer = new Timer(25, e -> {
            boolean gridPanel1Ready = gridPanel1.startNode != null && gridPanel1.finishNode != null;
            boolean gridPanel2Ready = gridPanel2.startNode != null && gridPanel2.finishNode != null;
            boolean isReady = gridPanel1Ready && gridPanel2Ready && !gridPanel1.isRunning && !gridPanel2.isRunning;

            startButtonBoth.setEnabled(isReady && !gridPanel1.isFinished && !gridPanel2.isFinished);
            resetButton.setEnabled(isReady);
            resetWallButton.setEnabled(isReady);
            searchInfo1.setText("Searched: " + gridPanel1.nodeSearched);
            pathInfo1.setText("Length: " + gridPanel1.pathLength);
            searchInfo2.setText("Searched: " + gridPanel2.nodeSearched);
            pathInfo2.setText("Length: " + gridPanel2.pathLength);
            timeLabel1.setText("Time: " + (int) gridPanel1.timeUse + " ms");
            timeLabel2.setText("Time: " + (int) gridPanel2.timeUse + " ms" );

        });
        updateTimer.start();

        //Place button to grid
        mainGrid.gridx = 0;
        mainGrid.gridy = 0;
        mainPanel.add(gridPanel1, mainGrid);

        mainGrid.gridy = 1;
        mainPanel.add(infoPanel1, mainGrid);

        mainGrid.gridx = 1;
        mainGrid.gridy = 0;
        mainPanel.add(gridPanel2, mainGrid);

        mainGrid.gridy = 1;
        mainPanel.add(infoPanel2, mainGrid);

        mainGrid.gridx = 0;
        mainGrid.gridy = 2;
        mainGrid.gridwidth = 2;
        mainPanel.add(startButtonBoth, mainGrid);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGrid = new GridBagConstraints();
        buttonGrid.insets = new Insets(10, 10, 10, 10);

        buttonGrid.gridx = 0;
        buttonGrid.gridy = 1;
        buttonPanel.add(resetButton, buttonGrid);

        buttonGrid.gridx = 1;
        buttonPanel.add(resetWallButton, buttonGrid);

        mainGrid.gridx = 0;
        mainGrid.gridy = 3;
        mainGrid.gridwidth = 2;
        mainPanel.add(buttonPanel, mainGrid);

        //Show Main panel
        window.add(mainPanel);
        window.pack();
        window.setVisible(true);
    }
}