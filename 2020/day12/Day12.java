package day12;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Day12 {
    private ArrayList<String> instructions;
    private int shipEast = 0;
    private int shipNorth = 0;
    private int waypointEast = 10;
    private int waypointNorth = 1;

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
                    case "N": waypointNorth += Integer.parseInt(matcher.group(2));
                        break;
                    case "E": waypointEast += Integer.parseInt(matcher.group(2));
                        break;
                    case "S": waypointNorth -= Integer.parseInt(matcher.group(2));
                        break;
                    case "W": waypointEast -= Integer.parseInt(matcher.group(2));
                        break;
                    case "F": shipEast += waypointEast * Integer.parseInt(matcher.group(2));
                        shipNorth += waypointNorth * Integer.parseInt(matcher.group(2));
                        break;
                    case "L": rotate(Integer.parseInt(matcher.group(2)));
                        break;
                    case "R": rotate(-Integer.parseInt(matcher.group(2)));
                }
            }
        }
        System.out.println(Math.abs(shipEast) + Math.abs(shipNorth));
    }

    private void rotate(int angleDegrees) {
        if (Math.abs(angleDegrees) == 180) {
            waypointEast = -waypointEast;
            waypointNorth = -waypointNorth;
            return;
        }
        int temp = waypointEast;
        waypointEast = waypointEast * (int) Math.round(Math.cos(Math.toRadians(angleDegrees)))
                        - waypointNorth * (int) Math.round(Math.sin(Math.toRadians(angleDegrees)));
        waypointNorth = temp * (int) Math.round(Math.sin(Math.toRadians(angleDegrees)))
                        - waypointNorth * (int) Math.round(Math.cos(Math.toRadians(angleDegrees)));

    }
}
