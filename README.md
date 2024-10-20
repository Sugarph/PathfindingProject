# Pathfinding Comparison Project

## Overview
This project compares the A* and BFS pathfinding algorithms, implemented in Java. Initially, it was designed for a Data Structures and Algorithms assignment to demonstrate the use of custom-built priority queues and double-ended queues. However, as the project evolved, it grew beyond the assignment’s scope and has since become a standalone effort.

## Features
- Two side-by-side grids for algorithm comparison.
- Ability to switch between A* and BFS algorithms for each grid.
- Information panels displaying search time, number of nodes searched, and path length.
- Control buttons to start the pathfinding process, reset grids, or reset walls.
- Real-time visual updates as the pathfinding progresses.

## How to Run
1. Run the `Main` class.
2. When prompted, input the desired node pixel size (between 10 and 60).
3. The main window will open, displaying two grid panels and control options.
4. Click on the grid to set the start and finish nodes (applies to both grids).
5. Use the checkboxes to select either A* or BFS for each grid.
6. Press the "Start Algorithms" button to begin the pathfinding.
7. Track search time, nodes searched, and path length in the information panels below the grids.
8. Use the "Reset" button to reset the grids, or the "Reset Wall" button to clear any walls.
