package day03;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Day03 {
    ArrayList<String> map;

    public Day03() {
        map = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day03\\map.txt"));
            while (scanner.hasNextLine()) {
                map.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void run() {
        /*
            Right 1, down 1.
            Right 3, down 1. (This is the slope you already checked.)
            Right 5, down 1.
            Right 7, down 1.
            Right 1, down 2.
        */
        long trees1 = getTreesForSlope(1, 1);
        int trees2 = getTreesForSlope(3, 1);
        int trees3 = getTreesForSlope(5, 1);
        int trees4 = getTreesForSlope(7, 1);
        int trees5 = getTreesForSlope(1, 2);

        System.out.println(trees1 * trees2 * trees3 * trees4 * trees5);
    }

    public int getTreesForSlope(int right, int down) {
        int rowIndex = 0;
        int columnIndex = 0;
        int treeCount = 0;

        for (int i = 1; rowIndex < map.size(); i++) {
            if (map.get(rowIndex).charAt(columnIndex) == '#')
                treeCount++;
            columnIndex = (i * right) % map.get(rowIndex).length();
            rowIndex = i * down;
        }

        return treeCount;
    }
}
