package day09;

import java.util.*;
import java.io.*;

public class Day09 {
    ArrayList<Long> numbers;

    public Day09() {
        try {
            Scanner scanner = new Scanner(new File("day09\\numbers.txt"));
            // Scanner scanner = new Scanner(new File("day09\\dadsInput.txt"));
            numbers = new ArrayList<>();
            while (scanner.hasNextLine()) {
                numbers.add(Long.parseLong(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        long invalidNumber = 0;
        for (int i = 25; i < numbers.size(); i++) {
            if (!isSumOfPrevious(i)) {
                invalidNumber = numbers.get(i);
                System.out.println(invalidNumber);
                break;
            }
        }

        long smallest = Long.MAX_VALUE, largest = 0, sum = 0;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i; j < numbers.size(); j++) {
                sum += numbers.get(j);
                if (sum > invalidNumber) {
                    smallest = Long.MAX_VALUE;
                    largest = 0;
                    sum = 0;
                    break;
                } else if (sum == invalidNumber) {
                    System.out.println(smallest + largest);
                    return;
                }

                if (numbers.get(j) < smallest) {
                    smallest = numbers.get(j);
                }
                if (numbers.get(j) > largest) {
                    largest = numbers.get(j);
                }
            }
        }
    }

    private boolean isSumOfPrevious(int index) {
        long number = numbers.get(index);
        List<Long> preamble = numbers.subList(index - 25, index);
        for (int i = 0; i < preamble.size(); i++) {
            if (preamble.contains(number - preamble.get(i))) {
                return true;
            }
        }
        return false;
    }
}