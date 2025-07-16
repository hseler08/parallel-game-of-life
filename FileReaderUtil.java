import java.io.*;
import java.util.*;




public class FileReaderUtil {

    public static GameConfig readConfig(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        try {
            String firstLine = reader.readLine();
            if (!isNumericLine(firstLine)) {
                throw new IllegalArgumentException("The first line must contain only integers.");
            }
            String[] parts = firstLine.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("The first line must contain exactly three numbers: rows, cols, iterations.");
            }

            int rows = Integer.parseInt(parts[0]);
            int cols = Integer.parseInt(parts[1]);
            int iterations = Integer.parseInt(parts[2]);

            String secondLine = reader.readLine();
            if (!isNumeric(secondLine)) {
                throw new IllegalArgumentException("The second line must contain the number of live cells.");
            }
            int liveCellsCount = Integer.parseInt(secondLine);

            List<int[]> liveCells = new ArrayList<>();
            for (int i = 0; i < liveCellsCount; i++) {
                String line = reader.readLine();
                if (!isNumericLine(line)) {
                    throw new IllegalArgumentException("All coordinates must contain only integers.");
                }
                String[] coordinates = line.split(" ");
                if (coordinates.length != 2) {
                    throw new IllegalArgumentException("Each coordinate line must contain exactly two numbers.");
                }

                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                liveCells.add(new int[]{x, y});
            }

            return new GameConfig(rows, cols, iterations, liveCells);
        } finally {
            reader.close();
        }
    }

    private static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    private static boolean isNumericLine(String line) {
        return line != null && line.matches("[\\d ]+");
    }
}
