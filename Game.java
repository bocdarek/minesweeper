package minesweeper;

import java.awt.*;
import java.util.Scanner;

public class Game {

    private final Scanner sc = CommonScanner.getInstance();
    private final int numberOfMines;
    private final int boardSize = 9;

    private final Board board = new Board(boardSize);
    private final Player player = new Player();

    {
        board.setPlayer(player);
    }

    public Game(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void play() {
        board.generateMines(numberOfMines);
        board.display();
        while (!player.getVisitedFields().equals(board.getMines())) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            boolean areCoordinatesValid = takeCoordinates();
            if (!areCoordinatesValid) {
                continue;
            }
            board.display();
        }
        System.out.println("Congratulations! You found all mines!");
    }

    private boolean takeCoordinates() {
        String[] coordinates = sc.nextLine().trim().split("\\s+");
        if (coordinates.length != 2 || !coordinates[0].matches("^[1-9]$")
                                    || !coordinates[1].matches("^[1-9]$")) {
            System.out.println("You need provide two digits separated by space!");
            return false;
        }

        for (String coordinate: coordinates) {
            if (Integer.parseInt(coordinate) > boardSize) {
                System.out.printf("Each coordinate cannot be bigger than %s%n.", boardSize);
                return false;
            }
        }

        int x = Integer.parseInt(coordinates[0]) - 1;
        int y = Integer.parseInt(coordinates[1]) - 1;
        Field field = board.getBoard()[y][x];
        if (!"X.".contains(field.getSymbol())) {
            System.out.println("There is a number here!");
            return false;
        }

        if (!player.getVisitedFields().contains(field)) {
            player.getVisitedFields().add(field);
        } else {
            player.getVisitedFields().remove(field);
        }
        return true;
    }
}
