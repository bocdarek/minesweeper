package minesweeper;

import java.util.Set;

public class Field {

    private int x;
    private int y;
    private boolean isMine;
    private String symbol;
    private Set<Field> adjacentFields;

    public Field(int x, int y, String symbol, boolean isMine) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.isMine = isMine;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
        symbol = "X";
    }

    public Set<Field> getAdjacentFields() {
        return adjacentFields;
    }

    public void setAdjacentFields(Set<Field> adjacentFields) {
        this.adjacentFields = adjacentFields;
    }

    public void addHint() {
        int mineCounter = 0;
        for (Field field : adjacentFields) {
            if ("X".equals(field.symbol)) {
                mineCounter++;
            }
        }
        if (mineCounter > 0) {
            symbol = String.valueOf(mineCounter);
        }
    }


}
