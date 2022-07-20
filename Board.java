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

    public List<Field> getAvailableFields() {
        return availableFields;
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
    }

    public void display() {
        display(false);
    }

    public void display(boolean withMines) {
        printTop();
        for (int i = 0; i < board.length; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j <board[i].length; j++) {
                Field field = board[i][j];
                if (withMines && mines.contains(field)) {
                    System.out.print("X");
                    continue;
                }
                if (player.getMarkedFields().contains(field)) {
                    System.out.print("*");
                    continue;
                }
                String fieldSymbol = field.getSymbol();
                if (player.getExploredFields().contains(field)) {
                    System.out.print(".".equals(fieldSymbol) ? "/" : fieldSymbol);
                    continue;
                }
                System.out.print(".");
            }
            System.out.println("|");
        }
        printBottom();
    }

    public void explore(Field field) {
        if (!availableFields.contains(field)) {
            return;
        }
        player.getMarkedFields().remove(field); // just in case we chose previously marked field

        availableFields.remove(field);
        player.getExploredFields().add(field);
        if (field.getSymbol().equals(".")) {
            for (Field adjacentField : field.getAdjacentFields()) {
                if (!player.getExploredFields().contains(adjacentField)) {
                    explore(adjacentField);
                }
            }
        }
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

    public void addAllHints() {
        for (Field field : availableFields) {
            field.addHint();
        }
    }
}
