package day13;

import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Day13 {
    private int departTime = 0;
    private ArrayList<Integer> busIDs;

    public Day13() {
        busIDs = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day13\\buses.txt"));
            // Scanner scanner = new Scanner(new File("day13\\test.txt"));
            departTime = scanner.nextInt();
            scanner.nextLine();
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            for (String s : parts) {
                if (!"x".equals(s)) {
                    busIDs.add(Integer.parseInt(s));
                } 
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {

    }
}
