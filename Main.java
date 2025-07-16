import java.io.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide an input file name.");
            return;
        }

        String filename = args[0];

        try {
            GameConfig config = FileReaderUtil.readConfig(filename);

            int[][] board = Board.createBoard(config.getRows(), config.getCols(), config.getLiveCells());
            runGameOfLife(board, config);

        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
    }

    public static void runGameOfLife(int[][] board, GameConfig config) {
        int rows = config.getRows();
        int cols = config.getCols();
        int numThreads = config.getNumThreads();

        numThreads = Math.min(numThreads, rows);

        printThreadAllocation(config);

        int[][] newBoard = new int[rows][cols];

        for (int i = 0; i < config.getIterations(); i++) {
            System.out.println("\nIteration " + (i + 1) + ":");
            Board.printBoard(board);

            CountDownLatch latch = new CountDownLatch(numThreads);
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            int rowsPerThread = rows / numThreads;
            int remainder = rows % numThreads;

            for (int threadId = 0; threadId < numThreads; threadId++) {
                int startRow = threadId * rowsPerThread + Math.min(threadId, remainder);
                int endRow = (threadId + 1) * rowsPerThread + Math.min(threadId + 1, remainder);

                executor.submit(new GameWorker(board, newBoard, startRow, endRow, latch));
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int[][] temp = board;
            board = newBoard;
            newBoard = temp;

            executor.shutdown();
        }
    }

    private static void printThreadAllocation(GameConfig config) {
        int rows = config.getRows();
        int cols = config.getCols();
        int numThreads = config.getNumThreads();

        int rowsPerThread = rows / numThreads;
        int remainder = rows % numThreads;

        System.out.println("# " + numThreads + " threads, row-based partitioning");
        for (int threadId = 0; threadId < numThreads; threadId++) {
            int startRow = threadId * rowsPerThread + Math.min(threadId, remainder);
            int endRow = (threadId + 1) * rowsPerThread + Math.min(threadId + 1, remainder);

            int rowCount = endRow - startRow;
            System.out.printf("tid %2d: rows: %3d:%-3d (%2d) cols: %3d:%-3d (%3d)%n",
                    threadId, startRow, endRow - 1, rowCount, 0, cols - 1, cols);
        }
    }
}







