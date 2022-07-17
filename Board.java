package minesweeper;

import java.util.*;

public class Board {

    private final int boardSize;
    private Field[][] board;
    private final List<Field> availableFields = new ArrayList<>();
    private final Set<Field> mines = new HashSet<>();
    private Player player;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        createBoard();
    }

    public Set<Field> getMines() {
        return mines;
    }

    public Field[][] getBoard() {
        return board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void createBoard() {
        board = new Field[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Field field = new Field(j, i, ".", false);
                board[j][i] = field;
                availableFields.add(field);
            }
        }
        fillFieldsWithAdjacentFields();
    }

    public void generateMines(int numberOfMines) {
        for (int i = 0; i < numberOfMines; i++) {
            int index = (int) (Math.random() * availableFields.size());
            Field chosenField = availableFields.remove(index);
            chosenField.setMine(true);
            mines.add(chosenField);
        }
        addAllHints();
    }

    public void display() {
        printTop();
        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j <board[i].length; j++) {
                Field field = board[i][j];
                if (player.getVisitedFields().contains(field)) {
                    System.out.print("*");
                    continue;
                }
                String fieldSymbol = field.getSymbol();
                System.out.print("X".equals(fieldSymbol) ? "." : fieldSymbol);
            }
            System.out.println("|");
        }
        printBottom();
    }

    public void displayWithMines() {
        printTop();
        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j <board[i].length; j++) {
                System.out.print(board[i][j].getSymbol());
            }
            System.out.println("|");
        }
        printBottom();
    }

    private void printTop() {
        System.out.print("\n |");
        for (int i = 1; i <= boardSize; i++) {
            System.out.print(i);
        }
        System.out.println("|");
        printBottom();
    }

    private void printBottom() {
        System.out.println("-|" + "-".repeat(boardSize) + "|");
    }


    private void fillFieldsWithAdjacentFields() {
        for (Field[] row : board) {
            for (Field field : row) {
                fillFieldWithAdjacentFields(field);
            }
        }
    }

    private void fillFieldWithAdjacentFields(Field field) {
        int x = field.getX();
        int y = field.getY();
        Set<Field> adjacentFields = new HashSet<>();
        for (int i = Math.max(0, y - 1); i <= Math.min(board.length - 1, y + 1); i++) {
            for (int j = Math.max(0, x - 1); j <= Math.min(board[0].length - 1, x + 1); j++) {
                if (i == y && j == x) {
                    continue;
                }
                adjacentFields.add(board[j][i]);
            }
        }
        field.setAdjacentFields(adjacentFields);
    }

    private void addAllHints() {
        for (Field field : availableFields) {
            field.addHint();
        }
    }
}
