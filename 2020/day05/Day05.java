package day05;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;

public class Day05 {
    ArrayList<String> passes;

    public Day05() {
        passes = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day05\\Passes.txt"));
            while(scanner.hasNextLine()) {
                passes.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void run() {
        ArrayList<Long> ids = new ArrayList<>();
        for (String pass : passes) {
            ids.add(getSeatId(pass));
        }
        Collections.sort(ids);

        for (int i = 1; i < ids.size(); i++) {
            if (ids.get(i) - ids.get(i - 1) == 2) {
                System.out.println(ids.get(i) - 1);
            }            
        }
    }

    private long getSeatId(String pass) {
        long rowBegin = 0;
        long rowEnd = 127;
        long columnBegin = 0;
        long columnEnd = 7;
        for (int i = 0; i < pass.length(); i++) {
            switch(pass.charAt(i)) {
                case 'F': rowEnd = (rowEnd - rowBegin) / 2 + rowBegin;
                    break;
                case 'B': rowBegin = Math.round((double)(rowEnd - rowBegin) / 2 + rowBegin);
                    break;
                case 'R': columnBegin = Math.round((double)(columnEnd - columnBegin) / 2 + columnBegin);
                    break;
                case 'L': columnEnd = (columnEnd - columnBegin) / 2 + columnBegin;
            }
        }

        return rowBegin * 8 + columnBegin;
    }
}
