# Matrix Run

## Introduction

Matrix Run is a maze-based game developed in Java using Swing. In this game, the player has to reach the destination while avoiding an enemy that continuously chases the player through the maze.

The main purpose of this project is to demonstrate the practical application of Data Structures and Algorithms. The project uses Breadth First Search (BFS) and Dijkstra's Algorithm to implement enemy movement and path validation.

This project was developed as part of the Data Structures and Algorithms (DAA) course.

---

## Problem Statement

Most graph algorithms are studied only theoretically. It is often difficult to understand where these algorithms are used in real applications.

The objective of this project is to create a game environment where graph algorithms can be visualized and understood through gameplay.

---

## Objectives

* Create a maze-based game using Java Swing.
* Implement graph traversal algorithms.
* Use BFS to verify whether a valid path exists.
* Use Dijkstra's Algorithm to calculate the shortest path.
* Develop an intelligent enemy that follows the player.
* Demonstrate practical applications of DAA concepts.

---

## Features

* Random maze generation
* Player movement using keyboard controls
* Intelligent enemy movement
* BFS-based path validation
* Dijkstra shortest path calculation
* Score counter
* Game timer
* Win and lose screens
* Restart functionality

---

## Technologies Used

* Java
* Java Swing
* Graph Algorithms
* Queue
* Priority Queue
* Object Oriented Programming

---

## Project Files

### Main.java

This file is the entry point of the project. It creates the game window and loads the game panel.

### GamePanel.java

This is the main game class. It handles:

* Maze display
* Player movement
* Enemy movement
* Score calculation
* Timer management
* Game state management
* Win/Lose screens

### DAA.java

This file contains the graph algorithms used in the project.

Functions implemented:

* Breadth First Search (BFS)
* Dijkstra's Algorithm

### map.java

Responsible for:

* Maze generation
* Random placement of player
* Random placement of enemy
* Random placement of destination

### player.java

Handles:

* Player movement
* Win condition checking
* Lose condition checking

### Position.java

Stores row and column coordinates used throughout the project.

---

## Algorithms Used

### Breadth First Search (BFS)

BFS is used to check whether a path exists between two positions in the maze.

Applications in this project:

* Verifying that the generated maze is solvable.
* Checking whether the enemy can reach the player.

### Dijkstra's Algorithm

Dijkstra's Algorithm is used to calculate the shortest path between the enemy and the player.

The enemy follows this shortest path and moves one step at a time, making its movement intelligent rather than random.

---

## Working of the Game

1. A random maze is generated.
2. Player, destination and enemy positions are generated.
3. BFS verifies that the maze is playable.
4. The player moves using W, A, S and D keys.
5. The enemy continuously calculates the shortest path using Dijkstra's Algorithm.
6. If the player reaches the destination, the player wins.
7. If the enemy reaches the player, the game ends.
8. The player can restart the game by pressing R.

---

## Controls

W - Move Up

A - Move Left

S - Move Down

D - Move Right

R - Restart Game

---

## Time Complexity

Breadth First Search (BFS)

Time Complexity:

O(V + E)

where:

* V = Number of vertices
* E = Number of edges

Dijkstra's Algorithm

Time Complexity:

O(E log V)

because a Priority Queue is used.

---

## Learning Outcomes

Through this project, the following concepts were learned and implemented:

* Graph Representation
* Breadth First Search
* Dijkstra's Algorithm
* Queue
* Priority Queue
* Event Handling
* Java Swing GUI Development
* Object Oriented Programming

---

## Future Scope

The project can be improved further by adding:

* Multiple difficulty levels
* Better maze generation techniques
* Sound effects
* Power-ups and obstacles
* Multiple enemies
* Leaderboard system
* Image-based graphics

---

## Conclusion

Matrix Run successfully demonstrates the use of graph algorithms in a game environment. The project combines Java Swing with Data Structures and Algorithms to create an interactive application that is both educational and engaging. The implementation of BFS and Dijkstra's Algorithm helps in understanding how these algorithms can be used in practical scenarios.
