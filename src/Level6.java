import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Level6 {
    private static int[][] computeBfsMap(int x, int y, char[][] map, int size) {
        int[][] bfsMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                bfsMap[i][j] = 0;
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        bfsMap[y][x] = 1;

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        queue.offer(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            x = current[0];
            y = current[1];

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (newX >= 0 && newX < size && newY >= 0 && newY < size && bfsMap[newY][newX] == 0 && map[newY][newX] == 'L') {
                    bfsMap[newY][newX] = 1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }
        int[][] dilatedBfsMap = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i > 0 && bfsMap[i - 1][j] == 1) ||
                        (j > 0 && bfsMap[i][j - 1] == 1) ||
                        (i < size - 1 && bfsMap[i + 1][j] == 1) ||
                        (j < size - 1 && bfsMap[i][j + 1] == 1) ||
                        (bfsMap[i][j] == 1)) {
                    dilatedBfsMap[i][j] = 1;
                }
            }
        }

        return dilatedBfsMap;
    }

    private static String computeBorder(int x, int y, char[][] map, int size) {
        int[][] newMap = computeBfsMap(x, y, map, size);

        StringBuilder path = new StringBuilder();

        boolean start = false;
        for (int i = 0; i < size && !start; i++) {
            for (int j = 0; j < size && !start; j++) {
                if (newMap[i][j] == 1) {
                    x = j;
                    y = i;
                    start = true;
                }
            }
        }

        int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };
        int[] dx = { 1,  1,  0, -1, -1, -1, 0, 1 };
        List<Integer> contour = new LinkedList<>();
        int dir = 7;
        int[] p0 = { x, y };
        dir = (dir + 7 - dir % 2) % 8;
        int[] p1 = { x + dx[dir], y + dy[dir] };
        while (newMap[p0[1]][p0[0]] != newMap[p1[1]][p1[0]]) {
            dir = (dir + 1) % 8;
            p1[0] = x + dx[dir];
            p1[1] = y + dy[dir];
        }
        contour.add(dir);

        int[] prev;
        int[] next = p1;
        do {
            prev = next;
            dir = (dir + 7 - dir % 2) % 8;
            next = new int[] { prev[0] + dx[dir], prev[1] + dy[dir] };
            while (newMap[prev[1]][prev[0]] != newMap[next[1]][next[0]]) {
                dir = (dir + 1) % 8;
                next[0] = prev[0] + dx[dir];
                next[1] = prev[1] + dy[dir];
            }
            path.insert(0, (prev[0] - 1) + "," + (prev[1] - 1) + " ");
            contour.add(dir);
        }
        while (!Arrays.equals(next,p1) || !Arrays.equals(prev,p0));

        return path.toString();
    }

    public static void solve() throws IOException {
        String[] levels = {
                "level6_example.in",
                "level6_1.in",
                "level6_2.in",
                "level6_3.in",
                "level6_4.in",
                "level6_5.in"
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

            char[][] map = new char[size + 2][size + 2];
            for (int i = 0; i < size; i++) {
                String line = in.nextLine();
                for (int j = 0; j < size; j++) {
                    map[i + 1][j + 1] = line.charAt(j);
                }
            }
            for (int i = 0; i < size + 2; i++) {
                map[0][i] = map[size + 1][i] = 'W';
                map[i][0] = map[i][size + 1] = 'W';
            }

            int coordinates = Integer.parseInt(in.nextLine());
            for (int i = 0; i < coordinates; i++) {
                String line = in.nextLine();
                int x = Integer.parseInt(line.split(",")[1]);
                int y = Integer.parseInt(line.split(",")[0]);

                out.write(computeBorder(y + 1, x + 1, map, size + 2) + "\n");
                System.out.println("  Coordinate " + (i + 1) + " solved!");
            }

            in.close();
            out.close();
            System.out.println("Level " + level + " solved!");
        }
    }
}
