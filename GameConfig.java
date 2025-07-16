import java.util.List;
import java.util.ArrayList;

public class GameConfig {
    private int rows;
    private int cols;
    private int iterations;
    private List<int[]> liveCells;
    private int numThreads;

    public GameConfig(int rows, int cols, int iterations, List<int[]> liveCells) {
        this.rows = rows;
        this.cols = cols;
        this.iterations = iterations;
        this.liveCells = liveCells;
        this.numThreads = Runtime.getRuntime().availableProcessors();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getIterations() {
        return iterations;
    }

    public List<int[]> getLiveCells() {
        return liveCells;
    }

    public int getNumThreads() {
        return numThreads;
    }

}
