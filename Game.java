package minesweeper;

import java.util.Scanner;

public class Game {

    private final Scanner sc = CommonScanner.getInstance();
    private final int numberOfMines;
    private final int boardSize = 9;

    private final Board board = new Board(boardSize);
    private final Player player = new Player();

    private final int WRONG_INPUT = -1;
    private final int FREE = 0;
    private final int MINE = 1;

    {
        board.setPlayer(player);
    }

    public Game(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void play() {
        boolean isFirstExploration = true;
        board.display();
        while (!player.getMarkedFields().equals(board.getMines())
                || board.getAvailableFields().isEmpty() || isFirstExploration) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            int[] userInput = takeCommand();
            if (userInput[2] == WRONG_INPUT) {
                continue;
            }

            int x = userInput[0];
            int y = userInput[1];
            Field field = board.getBoard()[y][x];

            if (userInput[2] == FREE) {
                if (isFirstExploration) {
                    board.getAvailableFields().remove(field);
                    board.generateMines(numberOfMines);
                    board.getAvailableFields().add(field);
                    board.addAllHints();
                    isFirstExploration = false;
                }
                if (board.getMines().contains(field)) {
                    board.display(true);
                    System.out.println("You stepped on a mine and failed!");
                    return;
                } else if (board.getAvailableFields().contains(field)) {
                    board.explore(field);
                }
            }

            if (userInput[2] == MINE && !player.getExploredFields().contains(field)) {
                player.updateMarkedFields(field);
            }

            board.display();
        }
        System.out.println("Congratulations! You found all mines!");
    }

    private int[] takeCommand() {
        int[] userInput = new int[3];
        String[] coordinates = sc.nextLine().trim().toLowerCase().split("\\s+");
        if (coordinates.length < 2 || !coordinates[0].matches("^[1-9]$")
                                    || !coordinates[1].matches("^[1-9]$")) {
            System.out.println("You need provide two digits separated by space and command!");
            userInput[2] = WRONG_INPUT;
            return userInput;
        }

        for (int i = 0; i < 2; i++) {
            if (Integer.parseInt(coordinates[i]) > boardSize) {
                System.out.printf("Each coordinate cannot be bigger than %s%n.", boardSize);
                userInput[2] = WRONG_INPUT;
                return userInput;
            }
        }

        if (coordinates.length >= 3 && coordinates[2].matches("(^free$)|(^mine$)")) {
            userInput[2] = coordinates[2].matches("free") ? FREE : MINE;
        } else {
            System.out.println("Possible commands are \"free\" and \"mine\".");
            userInput[2] = WRONG_INPUT;
            return userInput;
        }

        userInput[0] = Integer.parseInt(coordinates[0]) - 1;
        userInput[1] = Integer.parseInt(coordinates[1]) - 1;
        return userInput;
    }
}
