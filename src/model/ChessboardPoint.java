package model;

/**
 * This class represents positions on the checkerboard, such as (0, 0), (0, 7), and so on
 * Where, the upper left corner is (0, 0), the lower left corner is (7, 0), the upper right corner is (0, 7), and the lower right corner is (7, 7).
 */
public class ChessboardPoint {
    private final int row;
    private final int col;

    public ChessboardPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return row + col;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ChessboardPoint temp = (ChessboardPoint) obj;
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }

    @Override
    public String toString() {
        return   row + "," + col ;
    }
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    public ChessboardPoint getNeighbor(Direction direction){
        switch (direction) {
            case UP:
                return new ChessboardPoint(row - 1, col);
            case RIGHT:
                return new ChessboardPoint(row, col + 1);
            case DOWN:
                return new ChessboardPoint(row + 1, col);
            case LEFT:
                return new ChessboardPoint(row, col - 1);
            default:
                return null;
        }
    }
}