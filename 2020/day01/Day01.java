package day01;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day01 {
    ArrayList<Integer> numbers;

    public Day01() {
        numbers = new ArrayList<>();
        try {
            Scanner console = new Scanner(new File("day01\\report.txt"));
            while (console.hasNextInt()) {
                numbers.add(console.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }
    
    public void run() {
        System.out.println("running day 1");

        for (int num1 : numbers) {
            for (int num2 : numbers) {
                if (num1 + num2 > 2020)
                    continue;
                for (int num3 : numbers) {
                    if (num1 + num2 + num3 == 2020) {
                        System.out.println(num1 * num2 * num3);
                        return;
                    }
                }   
            }
        }
    }
}
