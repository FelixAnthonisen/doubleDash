package no.uib.inf101.doubleDash.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.doubleDash.grid.CellPosition;
import no.uib.inf101.doubleDash.grid.GridDimension;

/**
 * A class for converting a CellPosition object into it's respective bounds on
 * the canvas
 */
class CellPositionToPixelConverter {
  private Rectangle2D box;
  private GridDimension gd;
  private double margin;

  CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /**
   * 
   * @param pos position of the given cell
   * @return A Rectangle2D object representation of the cell bounds
   */
  Rectangle2D getBoundsForCell(CellPosition pos) {
    double x, y, width, height;
    width = (box.getWidth() - (gd.cols() + 1) * margin) / gd.cols();
    height = (box.getHeight() - (gd.rows() + 1) * margin) / gd.rows();
    x = box.getX() + margin * (pos.col() + 1) + width * pos.col();
    y = box.getY() + margin * (pos.row() + 1) + height * pos.row();
    return new Rectangle2D.Double(x, y, width, height);
  }
}
