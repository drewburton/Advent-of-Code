import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Enter what day you would like to run: ");
        while(!(new Day()).run(console.nextInt())) {
            System.out.println("That is not a valid day, please try again");
        }
        console.close();
    }
}
