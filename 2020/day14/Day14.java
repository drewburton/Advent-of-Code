package day14;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Day14 {
    private ArrayList<String> instructions;
    private String mask;
    private HashMap<Long, Long> mem;

    public Day14() {
        instructions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day14\\instructions.txt"));
            // Scanner scanner = new Scanner(new File("day14\\test.txt"));
            while (scanner.hasNextLine()) {
                instructions.add(scanner.nextLine().replaceAll(" ", ""));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        mem = new HashMap<>();
        Pattern pattern = Pattern.compile("(mask|mem)\\[{0,1}([0-9]*)\\]{0,1}=(\\w+)");
        for (String instruction : instructions) {
            Matcher matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                switch(matcher.group(1)) {
                    case "mask": mask = matcher.group(3);
                        break;
                    case "mem": ArrayList<Long> permutations = mask(Long.parseLong(matcher.group(2)));
                    for (Long address : permutations) {
                        mem.put(address, Long.parseLong(matcher.group(3)));
                    }
                        break;
                }
            }
        }
        long sum = 0;
        for (Long key : mem.keySet()) {
            sum += mem.get(key);
        }
        System.out.println(sum);
    }

    public ArrayList<Long> mask(Long value) {
        String binaryValue = Long.toBinaryString(value);
        int length = mask.length();
        binaryValue = String.format("%" + length + "s", binaryValue);
        binaryValue = binaryValue.replaceAll(" ", "0");
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) != '0') {
                String beginning = binaryValue.substring(0, i);
                String end = binaryValue.substring(i + 1);
                binaryValue = beginning + mask.charAt(i) + end;
            }
        }
        ArrayList<Long> permutations = new ArrayList<>();

        String current = "";
        for (int i = 0; i < binaryValue.length(); i++) {
            if (binaryValue.charAt(i) == 'X') {
                current += "0";
            }
        }
        length = current.length();
        
        do {
            int i = 0;
            String currentAddress = "";
            for (int j = 0; j < binaryValue.length(); j++) {
                if (binaryValue.charAt(j) == 'X') {
                    currentAddress += current.charAt(i);
                    i++;
                } else {
                    currentAddress += binaryValue.charAt(j);
                }
            }
            permutations.add(Long.parseLong(currentAddress, 2));
            current = nextPermutation(current);
        } while (current.length() <= length);

        return permutations;
    }

    public String nextPermutation(String current) {
        int length = current.length();
        long currentInt = Long.parseLong(current, 2);
        current = Long.toBinaryString(currentInt + 1);
        current = String.format("%" + length + "s", current);
        current = current.replaceAll(" ", "0");
        return current; 
    }
}
