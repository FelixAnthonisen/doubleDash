package no.uib.inf101.sem2.grid;

/**
 * A GridCell consists of a position and a generic value.
 *
 * @param pos   The position of the cell
 * @param value The value of the cell
 */
public record GridCell<E>(CellPosition pos, E value) {
}
