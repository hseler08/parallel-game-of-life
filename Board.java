import java.util.List;



public class Board {


    public static int[][] createBoard(int rows, int cols, List<int[]> liveCells) {
        int[][] board = new int[rows][cols];
        for (int[] cell : liveCells) {
            board[cell[0]][cell[1]] = 1;
        }
        return board;
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "O" : ".");
            }
            System.out.println();
        }
    }

    public static int countLiveNeighbors(int[][] board, int row, int col) {
        int liveNeighbors = 0;
        int rows = board.length;
        int cols = board[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = (row + i + rows) % rows;
                int newCol = (col + j + cols) % cols;
                liveNeighbors += board[newRow][newCol];
            }
        }
        return liveNeighbors;
    }
}
