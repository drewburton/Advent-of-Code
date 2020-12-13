package day08;

import java.util.*;
import java.io.*;

public class Day08 {
    private ArrayList<String> instructions;    
    private int accumulator = 0;

    public Day08() {
        try {
            Scanner scanner = new Scanner(new File("day08\\instructions.txt"));
            instructions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                instructions.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        for (int i = 0; i < instructions.size(); i++) {
            switchInstruction(i);
            if (runInstructions()) {
                System.out.println(accumulator);
                return;
            }
            switchInstruction(i);
        }
        
        System.out.println(accumulator);
    }

    private void switchInstruction(int i) {
        String[] parts = instructions.get(i).split(" ");
        if ("nop".equals(parts[0])) {
            instructions.set(i, "jmp " + parts[1]);
        } else if ("jmp".equals(parts[0])) {
            instructions.set(i, "nop " + parts[1]);
        }
    }

    private boolean runInstructions() {
        accumulator = 0;
        ArrayList<Integer> indexes = new ArrayList<>();
        int i = 0;
        while (!indexes.contains(i) && i < instructions.size()) {
            indexes.add(i);
            String[] parts = instructions.get(i).split(" ");
            switch(parts[0]) {
                case "acc": accumulator += Integer.parseInt(parts[1]);
                    break;
                case "jmp": i += Integer.parseInt(parts[1]) - 1;
                    break;
            }
            i++;
        }

        if (i >= instructions.size()) {
            return true;
        }
        return false;
    }
}
