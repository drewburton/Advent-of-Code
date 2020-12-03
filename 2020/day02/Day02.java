package day02;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Day02 {
    ArrayList<String> passwords;

    public Day02() {
        passwords = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("day02\\passwords.txt"));
            while (scanner.hasNextLine()) {
                passwords.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("cannot find file");
        }
    }    

    public void run() {
        int validCount = 0;
        for (String password : passwords) {
            validCount += isValidPassword(password) ? 1 : 0;
        }
        System.out.println(validCount);
    }

    private boolean isValidPassword(String policyPassword) {
        Pattern pattern = Pattern.compile("([0-9]+)-([0-9]+)\\s(\\w):\\s(\\w+)");
        Matcher matcher = pattern.matcher(policyPassword);

        if (matcher.find()) {
            int position1 = Integer.parseInt(matcher.group(1));
            int position2 = Integer.parseInt(matcher.group(2));
            // int min = Integer.parseInt(matcher.group(1));
            // int max = Integer.parseInt(matcher.group(2));
            char matchCharacter = matcher.group(3).charAt(0);
            String password = matcher.group(4);

            if ((password.charAt(position1 - 1) == matchCharacter || password.charAt(position2 - 1) == matchCharacter)
            && password.charAt(position1 - 1) != password.charAt(position2 - 1)) {
                return true;
            }
            // int matchCount = 0;
            // for (int i = 0; i < password.length(); i++) {
            //     if (password.charAt(i) == matchCharacter) {
            //         matchCount++;
            //     }
            // }
            // if (matchCount >= min && matchCount <= max) {
            //     return true;
            // }
        }
        return false;
    }
}
