package no.uib.inf101.sem2.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid<E> implements IGrid<E> {
    private final int rows, cols;

    private final List<List<E>> matrix = new ArrayList<>();

    /**
     * Construct a grid with rows*cols dimensions. The grid is
     * initially filled with the value of defaultValue.
     * 
     * @param rows         number of rows the grid should have
     * @param cols         number of cols the grid should have
     * @param defaultValue the defaultvalue a cell should have when the grid is
     *                     initialized
     */
    public Grid(int rows, int cols, E defaultValue) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; ++i) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < cols; ++j) {
                matrix.get(i).add(defaultValue);
            }
        }
    }

    /**
     * Construct a grid with {@link #rows}*{@link #cols} dimensions. The grid is
     * initially filled with the value null.
     * 
     * @param rows number of rows the grid should have
     * @param cols number of cols the grid should have
     */
    public Grid(int rows, int cols) {
        this(rows, cols, null);
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public Iterator<GridCell<E>> iterator() {
        List<GridCell<E>> arr = new ArrayList<>();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                CellPosition pos = new CellPosition(i, j);
                arr.add(new GridCell<>(pos, get(pos)));
            }
        }
        return arr.iterator();
    }

    @Override
    public void set(CellPosition pos, E value) {
        matrix.get(pos.row()).set(pos.col(), value);
    }

    @Override
    public E get(CellPosition pos) {
        return matrix.get(pos.row()).get(pos.col());

    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        boolean negative = pos.row() < 0 || pos.col() < 0;
        boolean withinBounds = pos.row() < rows && pos.col() < cols;
        return !negative && withinBounds;
    }
}