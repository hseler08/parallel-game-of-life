import java.util.concurrent.CountDownLatch;



public class GameWorker implements Runnable {
    private int[][] board;
    private int[][] newBoard;
    private int startRow;
    private int endRow;
    private CountDownLatch latch;

    public GameWorker(int[][] board, int[][] newBoard, int startRow, int endRow, CountDownLatch latch) {
        this.board = board;
        this.newBoard = newBoard;
        this.startRow = startRow;
        this.endRow = endRow;
        this.latch = latch;
    }

    public void run() {
        for (int row = startRow; row < endRow; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int liveNeighbors = Board.countLiveNeighbors(board, row, col);
                if (board[row][col] == 1) {
                    if (liveNeighbors != 2 && liveNeighbors != 3) {
                        newBoard[row][col] = 0;
                    } else {
                        newBoard[row][col] = 1;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newBoard[row][col] = 1;
                    } else {
                        newBoard[row][col] = 0;
                    }
                }
            }
        }
        latch.countDown();
    }
}