import day01.Day01;
import day02.Day02;
import day03.Day03;
import day04.Day04;

public class Day {
    public boolean run(int day) {
        switch(day) {
            case 1: (new Day01()).run();
                return true;
            case 2: (new Day02()).run();
                return true;
            case 3: (new Day03()).run();
                return true;
            case 4: (new Day04()).run();
                return true;
            default: return false;
        }
    }    
}
