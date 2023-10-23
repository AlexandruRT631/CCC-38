import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Level1 {
    public static void solve() throws IOException {
        String[] levels = {
                "level1_1.in",
                "level1_2.in",
                "level1_3.in",
                "level1_4.in",
                "level1_5.in"
        };

        String[] outputs = {
                "output1.txt",
                "output2.txt",
                "output3.txt",
                "output4.txt",
                "output5.txt"
        };

        for (int level = 0; level < 5; level++) {
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
                String line = in.nextLine();
                int x = Integer.parseInt(line.split(",")[0]);
                int y = Integer.parseInt(line.split(",")[1]);
                out.write(map[y][x] + "\n");
            }

            in.close();
            out.close();
        }
    }
}
