package minesweeper;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = CommonScanner.getInstance();

    public static void main(String[] args) {

        int minesNumber = 0;
        while (true) {
            System.out.print("How many mines do you want on the field? ");
            String input = sc.nextLine().trim();
            if (input.matches("^[1-9][0-9]?$")) {
                minesNumber = Integer.parseInt(input);
                break;
            } else {
                System.out.println("You must enter a number.\n");
            }
        }

        Game game = new Game(minesNumber);
        game.play();



    }
}
