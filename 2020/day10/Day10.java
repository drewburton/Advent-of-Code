package day10;

import java.util.*;
import java.io.*;

public class Day10 {
    private ArrayList<Integer> joltages;
    private HashMap<Integer, ArrayList<Integer>> combinations;
    private HashMap<Integer, Long> memoizedPath;

    public Day10() {
        joltages = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day10\\joltages.txt"));
            // Scanner scanner = new Scanner(new File("day10\\test.txt"));
            joltages.add(0);
            while (scanner.hasNextInt()) {
                joltages.add(scanner.nextInt());
            }
            Collections.sort(joltages);
            joltages.add(joltages.get(joltages.size() - 1) + 3);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void run() {
        // part 1
        // int oneJoltDifferences = 0;
        // int threeJoltDifferences = 0;

        // for (int i = 0; i < joltages.size() - 1; i++) {
        //     int difference = joltages.get(i + 1) - joltages.get(i);
        //     if (difference == 1) {
        //         oneJoltDifferences++;
        //     } else if (difference == 3) {
        //         threeJoltDifferences++;
        //     }
        // }
        // System.out.println(oneJoltDifferences * threeJoltDifferences);

        // part 2
        combinations = new HashMap<>();
        for (int i = 0; i < joltages.size() - 1; i++) {
            ArrayList<Integer> nextAdapters = new ArrayList<>();
            int difference = joltages.get(i + 1) - joltages.get(i);
            int j = 0;
            while (difference <= 3) {
                nextAdapters.add(joltages.get(i + j + 1));
                j++;
                if (i + j + 1 >= joltages.size()) break;
                difference = joltages.get(i + 1 + j) - joltages.get(i);
            }
            combinations.put(joltages.get(i), nextAdapters);
        }

        memoizedPath = new HashMap<>();
        System.out.println(getCombinations(0));
    }

    private long getCombinations(int adapter) {
        if (memoizedPath.containsKey(adapter)) {
            return memoizedPath.get(adapter);
        } else if (adapter == joltages.get(joltages.size() - 1)) {
            return 1;
        }

        long count = 0;
        for (int num : combinations.get(adapter)) {
            count += getCombinations(num);
        }
        memoizedPath.put(adapter, count);
        return count;
    }
}
