import day01.Day01;
import day02.Day02;
import day03.Day03;
import day04.Day04;
import day05.Day05;
import day06.Day06;
import day07.Day07;
import day08.Day08;
import day09.Day09;
import day10.Day10;
import day11.Day11;

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
            case 5: (new Day05()).run();
                return true;
            case 6: (new Day06()).run();
                return true;
            case 7: (new Day07()).run();
                return true;
            case 8: (new Day08()).run();
                return true;
            case 9: (new Day09()).run();
                return true;
            case 10: (new Day10()).run();
                return true;
            case 11: (new Day11()).run();
                return true;
            default: return false;
        }
    }    
}
