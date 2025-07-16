# parallel-game-of-life
This project implements Conway’s Game of Life — a well-known cellular automaton — in a multithreaded environment with row-based data partitioning and synchronized time steps using a CountDownLatch-like mechanism.
The simulation evolves a 2D grid of cells according to simple rules that mimic life and death based on neighbors. It is designed to process large grids efficiently using parallel computing.

Game Description
Each cell on the 2D grid is either:
Alive, or
Dead

At every time interval (tick), the state of each cell is updated simultaneously according to the following rules:
Any live cell with 2 or 3 live neighbors survives.
Any dead cell with exactly 3 live neighbors becomes alive.
All other live cells die in the next generation, and all other dead cells stay dead.
The board is updated in generations, with each step representing a full-board update.

Parallelization Strategy
Partitioning type: Row-based
The grid is divided horizontally among available threads
Each thread processes one or more rows independently in parallel
