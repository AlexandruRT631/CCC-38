import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Level3 {
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UPRIGHT,
        UPLEFT,
        DOWNRIGHT,
        DOWNLEFT
    }

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
                "level3_example.in",
                "level3_1.in",
                "level3_2.in",
                "level3_3.in",
                "level3_4.in",
                "level3_5.in"
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

                Direction[] dir = new Direction[line.length - 1];
                for (int j = 0; j < line.length - 1; j++) {
                    if (pairs[j][0] == pairs[j + 1][0] && pairs[j][1] < pairs[j + 1][1]) {
                        dir[j] = Direction.DOWN;
                    }
                    else if (pairs[j][0] == pairs[j + 1][0] && pairs[j][1] > pairs[j + 1][1]) {
                        dir[j] = Direction.UP;
                    }
                    else if (pairs[j][0] < pairs[j + 1][0] && pairs[j][1] == pairs[j + 1][1]) {
                        dir[j] = Direction.RIGHT;
                    }
                    else if (pairs[j][0] > pairs[j + 1][0] && pairs[j][1] == pairs[j + 1][1]) {
                        dir[j] = Direction.LEFT;
                    }
                    else if (pairs[j][0] < pairs[j + 1][0] && pairs[j][1] > pairs[j + 1][1]) {
                        dir[j] = Direction.UPRIGHT;
                    }
                    else if (pairs[j][0] > pairs[j + 1][0] && pairs[j][1] > pairs[j + 1][1]) {
                        dir[j] = Direction.UPLEFT;
                    }
                    else if (pairs[j][0] < pairs[j + 1][0] && pairs[j][1] < pairs[j + 1][1]) {
                        dir[j] = Direction.DOWNRIGHT;
                    }
                    else {
                        dir[j] = Direction.DOWNLEFT;
                    }
                }

                boolean valid = true;
                for (int j = 0; j < line.length - 1 && valid; j++) {
                    for (int k = j + 1; k < line.length; k++) {
                        // same coordinate
                        if (pairs[j][0] == pairs[k][0] && pairs[j][1] == pairs[k][1]) {
                            valid = false;
                        }
                        else if (k != line.length - 1) {
                            // same column
                            if (pairs[j][0] == pairs[k][0]) {
                                // first pair is above second pair
                                if (pairs[j][1] == pairs[k][1] - 1 && (
                                        (dir[j] == Direction.RIGHT && dir[k] == Direction.UPRIGHT) ||
                                                (dir[j] == Direction.DOWNRIGHT && dir[k] == Direction.RIGHT) ||
                                                (dir[j] == Direction.LEFT && dir[k] == Direction.UPLEFT) ||
                                                (dir[j] == Direction.DOWNLEFT && dir[k] == Direction.LEFT) ||
                                                (dir[j] == Direction.DOWNRIGHT && dir[k] == Direction.UPRIGHT) ||
                                                (dir[j] == Direction.DOWNLEFT && dir[k] == Direction.UPLEFT)
                                )) {
                                    valid = false;
                                }
                                // first pair is below second pair
                                else if (pairs[j][1] == pairs[k][1] + 1 && (
                                        (dir[j] == Direction.RIGHT && dir[k] == Direction.DOWNRIGHT) ||
                                                (dir[j] == Direction.UPRIGHT && dir[k] == Direction.RIGHT) ||
                                                (dir[j] == Direction.LEFT && dir[k] == Direction.DOWNLEFT) ||
                                                (dir[j] == Direction.UPLEFT && dir[k] == Direction.LEFT) ||
                                                (dir[j] == Direction.UPRIGHT && dir[k] == Direction.DOWNRIGHT) ||
                                                (dir[j] == Direction.UPLEFT && dir[k] == Direction.DOWNLEFT)
                                )) {
                                    valid = false;
                                }
                            }
                            // same row
                            else if (pairs[j][1] == pairs[k][1]) {
                                // first pair is left second pair
                                if (pairs[j][0] == pairs[k][0] - 1 && (
                                        (dir[j] == Direction.UP && dir[k] == Direction.UPLEFT) ||
                                                (dir[j] == Direction.UPRIGHT && dir[k] == Direction.UP) ||
                                                (dir[j] == Direction.DOWN && dir[k] == Direction.DOWNLEFT) ||
                                                (dir[j] == Direction.DOWNRIGHT && dir[k] == Direction.DOWN) ||
                                                (dir[j] == Direction.UPRIGHT && dir[k] == Direction.UPLEFT) ||
                                                (dir[j] == Direction.DOWNRIGHT && dir[k] == Direction.DOWNLEFT)
                                )) {
                                    valid = false;
                                }
                                // first pair is right second pair
                                else if (pairs[j][0] == pairs[k][0] + 1 && (
                                        (dir[j] == Direction.UP && dir[k] == Direction.UPRIGHT) ||
                                                (dir[j] == Direction.UPLEFT && dir[k] == Direction.UP) ||
                                                (dir[j] == Direction.DOWN && dir[k] == Direction.DOWNRIGHT) ||
                                                (dir[j] == Direction.DOWNLEFT && dir[k] == Direction.DOWN) ||
                                                (dir[j] == Direction.UPLEFT && dir[k] == Direction.UPRIGHT) ||
                                                (dir[j] == Direction.DOWNLEFT && dir[k] == Direction.DOWNRIGHT)
                                )) {
                                    valid = false;
                                }

                            }
                        }
                    }
                }

                if (valid) {
                    out.write("VALID\n");
                }
                else {
                    out.write("INVALID\n");
                }
            }

            in.close();
            out.close();
        }
    }
}
