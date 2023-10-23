import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Level4 {

    private static String computeShortestPath(int x, int y, char[][] map, int size, int xEnd, int yEnd) {
        int[][] visited = new int[size][size];
        int[][] prevX = new int[size][size];
        int[][] prevY = new int[size][size];
        int xStart = x;
        int yStart = y;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});

        int[] dx = {-1, 0, 1, 0, -1, 1, -1, 1};
        int[] dy = {0, -1, 0, 1, -1, -1, 1, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            x = current[0];
            y = current[1];

            if (x == xEnd && y == yEnd) {
                // Build the path from the end to the start
                StringBuilder path = new StringBuilder();
                while (x != xStart || y != yStart) {
                    int prevXCoord = prevX[y][x];
                    int prevYCoord = prevY[y][x];
                    path.insert(0, x + "," + y + " ");
                    x = prevXCoord;
                    y = prevYCoord;
                }
                path.insert(0, x + "," + y + " ");
                return path.toString();
            }

            visited[y][x] = 1;

            for (int i = 0; i < 8; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (newX >= 0 && newX < size && newY >= 0 && newY < size
                        && map[newY][newX] == 'W' && visited[newY][newX] == 0) {
                    queue.offer(new int[]{newX, newY});
                    visited[newY][newX] = 1;
                    prevX[newY][newX] = x;
                    prevY[newY][newX] = y;
                }
            }
        }

        return ""; // Return an empty string if no path is found
    }

    public static void solve() throws IOException {
        String[] levels = {
                "level4_example.in",
                "level4_1.in",
                "level4_2.in",
                "level4_3.in",
                "level4_4.in",
                "level4_5.in"
        };

        String[] outputs = {
                "outputexample.txt",
                "output1.txt",
                "output2.txt",
                "output3.txt",
                "output4.txt",
                "output5.txt"
        };

        for (int level = 0; level < 6; level++) {
            File file = new File(levels[level]);
            FileWriter out = new FileWriter(outputs[level]);

            Scanner in = new Scanner(file);
            int size = Integer.parseInt(in.nextLine());

            char[][] map = new char[size][size];
            for (int i = 0; i < size; i++) {
                String line = in.nextLine();
                for (int j = 0; j < size; j++) {
                    map[i][j] = line.charAt(j);
                }
            }

            int coordinates = Integer.parseInt(in.nextLine());
            for (int i = 0; i < coordinates; i++) {
                String[] line = in.nextLine().split(" ");
                int[][] pairs = new int[line.length][2];
                for (int j = 0; j < line.length; j++) {
                    pairs[j][0] = Integer.parseInt(line[j].split(",")[0]);
                    pairs[j][1] = Integer.parseInt(line[j].split(",")[1]);
                }

                out.write(computeShortestPath(pairs[0][0], pairs[0][1], map, size, pairs[1][0], pairs[1][1]) + "\n");
            }

            in.close();
            out.close();
        }
    }
}
