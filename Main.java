package minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int minesNumber = 0;
        try {
            minesNumber = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error! It's not a number.");
            return;
        }

        Board board = new Board();
        board.generateMines(minesNumber);
        board.display();
    }
}
