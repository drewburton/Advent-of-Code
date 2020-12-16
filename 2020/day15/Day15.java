package day15;

import java.util.*;
import java.io.*;

public class Day15 {
    HashMap<Integer, Integer> spokenNumbers;

    public Day15() {
        spokenNumbers = new HashMap<>();
        try {
            // Scanner scanner = new Scanner(new File("day15\\startingNumbers.txt"));
            Scanner scanner = new Scanner(new File("day15\\test.txt"));
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            for (int i = 0; i < parts.length; i++) {
                spokenNumbers.put(i + 1, Integer.parseInt(parts[i]));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        int numbersSpoken = spokenNumbers.size();
        int previous = spokenNumbers.get(numbersSpoken);
        spokenNumbers.remove(numbersSpoken);
        int current;
        while (numbersSpoken < 30000000) {
            current = speakNumber(previous, numbersSpoken);
            spokenNumbers.put(numbersSpoken, previous);
            previous = current;
            numbersSpoken++;
        }
        spokenNumbers.put(numbersSpoken, previous);
        System.out.println(spokenNumbers.get(numbersSpoken));
    }

    private int speakNumber(int previous, int numbersSpoken) {
        for (int i = spokenNumbers.size(); i > 0; i--) {
            if (spokenNumbers.get(i) == previous) 
                return numbersSpoken - i;
        }
        return 0;
    }
}
