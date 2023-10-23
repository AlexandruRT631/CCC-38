import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Level2 {
    private static int[][] computeDfs(int x, int y, char[][] map, int[][] dfs, int size) {
        dfs[y][x] = 1;
        if (x > 0 && map[y][x - 1] == 'L' && dfs[y][x - 1] == 0) {
            dfs = computeDfs(x - 1, y, map, dfs, size);
        }
        if (x < size - 1 && map[y][x + 1] == 'L' && dfs[y][x + 1] == 0) {
            dfs = computeDfs(x + 1, y, map, dfs, size);
        }
        if (y > 0 && map[y - 1][x] == 'L' && dfs[y - 1][x] == 0) {
            dfs = computeDfs(x, y - 1, map, dfs, size);
        }
        if (y < size - 1 && map[y + 1][x] == 'L' && dfs[y + 1][x] == 0) {
            dfs = computeDfs(x, y + 1, map, dfs, size);
        }
        return dfs;
    }

    public static void solve() throws IOException {
        String[] levels = {
                "level2_example.in",
                "level2_1.in",
                "level2_2.in",
                "level2_3.in",
                "level2_4.in",
                "level2_5.in"
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
                String[] pair = in.nextLine().split(" ");
                int x1 = Integer.parseInt(pair[0].split(",")[0]);
                int y1 = Integer.parseInt(pair[0].split(",")[1]);
                int x2 = Integer.parseInt(pair[1].split(",")[0]);
                int y2 = Integer.parseInt(pair[1].split(",")[1]);

                int[][] dfs = new int[size][size];
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        dfs[j][k] = 0;
                    }
                }
                dfs = computeDfs(x1, y1, map, dfs, size);
                if (dfs[y2][x2] == 1) {
                    out.write("SAME\n");
                }
                else {
                    out.write("DIFFERENT\n");
                }
            }

            in.close();
            out.close();
        }
    }
}
