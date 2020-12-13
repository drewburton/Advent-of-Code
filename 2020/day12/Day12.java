package day12;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Day12 {
    private ArrayList<String> instructions;
    private int east = 0;
    private int north = 0;
    private int direction = 0;

    public Day12() {
        instructions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day12\\instructions.txt"));
            // Scanner scanner = new Scanner(new File("day12\\test.txt"));
            while (scanner.hasNextLine()) {
                instructions.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        Pattern pattern = Pattern.compile("([NESWFLR])([0-9]+)");
        for (String instruction : instructions) {
            Matcher matcher = pattern.matcher(instruction);
            if (matcher.find()) {
                switch(matcher.group(1)) {
                    case "N": north += Integer.parseInt(matcher.group(2));
                        break;
                    case "E": east += Integer.parseInt(matcher.group(2));
                        break;
                    case "S": north -= Integer.parseInt(matcher.group(2));
                        break;
                    case "W": east -= Integer.parseInt(matcher.group(2));
                        break;
                    case "F": east += Integer.parseInt(matcher.group(2)) * Math.round(Math.cos(Math.toRadians(direction)));
                        north += Integer.parseInt(matcher.group(2)) * Math.round(Math.sin(Math.toRadians(direction)));
                        break;
                    case "L": direction += Integer.parseInt(matcher.group(2));
                        direction %= 360;
                        break;
                    case "R": direction -= Integer.parseInt(matcher.group(2));
                        direction %= 360;
                }
            }
        }
        System.out.println(Math.abs(east) + Math.abs(north));
    }
}
