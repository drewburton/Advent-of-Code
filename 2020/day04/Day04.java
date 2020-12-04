package day04;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

public class Day04 {
    ArrayList<String> passports;

    public Day04() {
        try {
            Scanner scanner = new Scanner(new File("day04\\batchFile.txt"));
            String allText = "";
            while (scanner.hasNext()) {
                allText += scanner.nextLine() + "\n";
            }
            passports = new ArrayList<>(List.of(allText.split("\n\n")));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void run() {
        int valid = 0;
        for (String passport : passports) {
            valid += isValid(passport) ? 1 : 0;
        }
        System.out.println(valid);
    }

    private boolean isValid(String passport) {
        String[] fields = passport.split("\\s");

        boolean byr = false;
        boolean iyr = false;
        boolean eyr = false;
        boolean hgt = false;
        boolean hcl = false;
        boolean ecl = false;
        boolean pid = false;
        Pattern pattern = Pattern.compile("(byr|iyr|eyr|hgt|hcl|ecl|pid|cid):(\\S+)");
        for (String field : fields) {
            Matcher matcher = pattern.matcher(field);
            if (matcher.find()) {
                switch(matcher.group(1)) {
                    case "byr": byr = verify("byr", matcher.group(2));
                        break; 
                    case "iyr": iyr = verify("iyr", matcher.group(2));
                        break;
                    case "eyr": eyr = verify("eyr", matcher.group(2));
                        break;
                    case "hgt": hgt = verify("hgt", matcher.group(2));
                        break;
                    case "hcl": hcl = verify("hcl", matcher.group(2));
                        break;
                    case "ecl": ecl = verify("ecl", matcher.group(2));
                        break;
                    case "pid": pid = verify("pid", matcher.group(2));
                }
            }
        }
        return byr && iyr && eyr && hgt && hcl && ecl && pid;
    }

    private boolean verify(String field, String value) {
        switch(field) {
            case "byr": int year = Integer.parseInt(value);
                return (year / 1000) > 0 && year >= 1920 && year <= 2002;
            case "iyr": year = Integer.parseInt(value);
                return (year / 1000) > 0 && year >= 2010 && year <= 2020;
            case "eyr": year = Integer.parseInt(value);
                return (year / 1000) > 0 && year >= 2020 && year <= 2030;
            case "hgt":
                if (value.contains("cm")) {
                    int num = Integer.parseInt(value.substring(0, value.length() - 2));
                    return num >= 150 && num <= 193;
                } else if (value.contains("in")) {
                    int num = Integer.parseInt(value.substring(0, value.length() - 2));
                    return num >= 59 && num <= 76;
                }
            case "hcl": Pattern pattern = Pattern.compile("#[0-9a-f]{6}");
                return pattern.matcher(value).find();
            case "ecl": pattern = Pattern.compile("(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)");
                return pattern.matcher(value).find();
            case "pid": return value.length() == 9;
        }
        return false;
    }
}