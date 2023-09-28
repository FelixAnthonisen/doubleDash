package no.uib.inf101.sem2.view;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.Grid;
import no.uib.inf101.sem2.grid.GridDimension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

public class TestCellPositionToPixelConverter {
    @Test
    public void sanityTest() {
        GridDimension gd = new Grid<>(3, 4, null);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(29, 29, 340, 240), gd, 30);
        Rectangle2D expected = new Rectangle2D.Double(214, 129, 47.5, 40);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
    }

    @Test
    public void sanityTest2() {
        GridDimension gd = new Grid<>(3, 4, null);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(30, 30, 340, 240), gd, 30);
        Rectangle2D expected = new Rectangle2D.Double(215, 130, 47.5, 40);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
    }

}
