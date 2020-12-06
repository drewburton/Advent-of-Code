package day06;

import java.util.*;
import java.io.*;

public class Day06 {
    ArrayList<String> questions;

    public Day06() {
        try {
            Scanner scanner = new Scanner(new File("day06\\questions.txt"));
            String text = "";
            while (scanner.hasNextLine()) {
                text += scanner.nextLine() + "\n";
            }
            String[] groups = text.split("\n\n");
            questions = new ArrayList<>(List.of(groups));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void run() {
        int sum = 0;
        for (String group : questions) {
            String[] people = group.split("\n");
            HashSet<Character> answers = new HashSet<>();
            for (int firstPersonIndex = 0 ; firstPersonIndex < people[0].length(); firstPersonIndex++) {
                answers.add(people[0].charAt(firstPersonIndex));
            }

            for (int remainingPeopleIndex = 1; remainingPeopleIndex < people.length; remainingPeopleIndex++) {
                var c = answers.toArray();
                for (int i = 0; i < c.length; i++) {
                    if (!people[remainingPeopleIndex].contains(c[i] + "")) {
                        answers.remove(c[i]);
                    }
                }
            }
            sum += answers.size();
        }
        System.out.println(sum);
    }
}
