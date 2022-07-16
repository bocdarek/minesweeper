package minesweeper;

import java.util.*;

public class Board {

    private final int DIM = 9;
    private final Field[][] board = new Field[DIM][DIM];
    private final List<Field> availableFields = new ArrayList<>();
    private final Set<Field> mines = new HashSet<>();

    public Board() {
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
        for (Field[] row : board) {
            for (Field field : row) {
                System.out.print(field.getSymbol());
            }
            System.out.println();
        }
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
