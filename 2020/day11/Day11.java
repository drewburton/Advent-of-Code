package day11;

import java.util.*;
import java.io.*;

public class Day11 {
    ArrayList<String> map;

    public Day11() {
        map = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day11\\layout.txt"));
            // Scanner scanner = new Scanner(new File("day11\\test.txt"));
            while (scanner.hasNextLine()) {
                map.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        while (change()) {}
        System.out.println(getOccupied());
    }

    private boolean change() {
        int numChanges = 0;
        ArrayList<ArrayList<Integer>> changes = new ArrayList<>();
        for (int row = 0; row < map.size(); row++) {
            ArrayList<Integer> rowChanges = new ArrayList<>();
            for (int column = 0; column < map.get(row).length(); column++) {
                if (map.get(row).charAt(column) == 'L' && seatsSurrounding(row, column) == 0) {
                    rowChanges.add(column);
                    numChanges++;
                } else if (map.get(row).charAt(column) == '#' && seatsSurrounding(row, column) >= 5) {
                    rowChanges.add(column);
                    numChanges++;
                }
            }
            changes.add(rowChanges);
        }
        update(changes);
        return numChanges != 0;
    }

    private void update(ArrayList<ArrayList<Integer>> changes) {
        for (int row = 0; row < map.size(); row++) {
            for (int column = 0; column < changes.get(row).size(); column++) {
                int changesColumn = changes.get(row).get(column);
                if (map.get(row).charAt(changesColumn) == 'L') {
                    String s = map.get(row);
                    map.set(row, insert(s, changesColumn, "#"));
                } else if (map.get(row).charAt(changesColumn) == '#') {
                    String s = map.get(row);
                    map.set(row, insert(s, changesColumn, "L"));
                }
            }
        }
    }

    private int seatsSurrounding(int row, int column) {
        int count = 0;

        if (isUpRightOccupied(row, column))
            count++;
        if (isUpLeftOccupied(row, column))
            count++;
        if (isDownRightOccupied(row, column))
            count++;
        if (isDownLeftOccupied(row, column))
            count++;

        if (isUpOccupied(row, column))
            count++;
        if (isDownOccupied(row, column))
            count++;

        if (isRightOccupied(row, column))
            count++;
        if (isLeftOccupied(row, column))
            count++;

        return count;
    }

    private boolean isUpRightOccupied(int row, int column) {
        if (row <= 0 || column >= map.get(row).length() - 1)
            return false;
        for (int i = 1; row - i >= 0 && column + i < map.get(row - i).length(); i++) {
            if (map.get(row - i).charAt(column + i) == 'L') {
                return false;
            } else if (map.get(row - i).charAt(column + i) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isUpLeftOccupied(int row, int column) {
        if (row <= 0 || column <= 0)
            return false;
        for (int i = 1; row - i >= 0 && column - i >= 0; i++) {
            if (map.get(row - i).charAt(column - i) == 'L') {
                return false;
            } else if (map.get(row - i).charAt(column - i) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isDownRightOccupied(int row, int column) {
        if (row >= map.size() - 1 || column >= map.get(row + 1).length() - 1)
            return false;
        for (int i = 1; row + i < map.size() && column + i < map.get(row + i).length(); i++) {
            if (map.get(row + i).charAt(column + i) == 'L') {
                return false;
            } else if (map.get(row + i).charAt(column + i) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isDownLeftOccupied(int row, int column) {
        if (row >= map.size() - 1 || column <= 0)
            return false;
        for (int i = 1; row + i < map.size() && column - i >= 0; i++) {
            if (map.get(row + i).charAt(column - i) == 'L') {
                return false;
            } else if (map.get(row + i).charAt(column - i) == '#') {
                return true;
            }
        }
        return false;

    }

    private boolean isUpOccupied(int row, int column) {
        if (row <= 0)
            return false;
        for (int i = row - 1; i >= 0; i--) {
            if (map.get(i).charAt(column) == 'L') {
                return false;
            } else if (map.get(i).charAt(column) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isDownOccupied(int row, int column) {
        if (row >= map.size() - 1)
            return false;
        for (int i = row + 1; i < map.size(); i++) {
            if (map.get(i).charAt(column) == 'L') {
                return false;
            } else if (map.get(i).charAt(column) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isRightOccupied(int row, int column) {
        if (column >= map.get(row).length() - 1)
            return false;
        for (int i = column + 1; i < map.get(row).length(); i++) {
            if (map.get(row).charAt(i) == 'L') {
                return false;
            } else if (map.get(row).charAt(i) == '#') {
                return true;
            }
        }
        return false;
    }

    private boolean isLeftOccupied(int row, int column) {
        if (column <= 0)
            return false;
        for (int i = column - 1; i >= 0; i--) {
            if (map.get(row).charAt(i) == 'L') {
                return false;
            } else if (map.get(row).charAt(i) == '#') {
                return true;
            }
        }
        return false;
    }

    private String insert(String original, int column, String insertion) {
        String firstHalf = original.substring(0, column);
        String secondHalf = original.substring(column + 1);
        return firstHalf + insertion + secondHalf;

    }

    private int getOccupied() {
        int count = 0;
        for (String s : map) {
            for (int i = 0; i < s.length(); i++) {
                count += s.charAt(i) == '#' ? 1 : 0;
            }
        }
        return count;
    }
}
