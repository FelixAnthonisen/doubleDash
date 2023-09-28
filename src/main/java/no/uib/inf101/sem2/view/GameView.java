package no.uib.inf101.sem2.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.model.GameState;
import no.uib.inf101.sem2.model.tiles.Tile;
import no.uib.inf101.util.linearAlgebra.Vector;

public class GameView extends JPanel {
  private static final String FONT_NAME = "Luminari";
  private ViewableGameModel model;
  private ColorPalette colorPalette = new ColorPalette();
  private BufferedImage winScreenBackgroundImage;

  public GameView(int width, int height, ViewableGameModel model) {
    setFocusable(true);
    setPreferredSize(new Dimension(width, height));
    setBackground(Color.decode("#113244"));
    this.model = model;
    try {
      winScreenBackgroundImage = ImageIO.read(new File("./src/main/resources/background.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    drawGame(g2);
  }

  private void drawGame(Graphics2D g2) {
    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(getBounds(), model.getLevelDimension(),
        0);
    // Inf101Graphics.drawImage(g2, background, 0, 0, 1);
    drawLevel(g2, model.getLevelTiles(), converter, model.getEye());
    drawPlayer(g2, model.getPlayerBounds(), model.getPlayerSprite(), model.getEye());
    if (model.getGameState() == GameState.GAME_WON) {
      drawGameWonScreen(g2);
    }
    drawDeathCount(g2, model.getDeathCount(), getBounds());
    if (model.getGameState() == GameState.GAME_NOT_STARTED) {
      drawWelcomeScreen(g2);
    }
  }

  private void drawWelcomeScreen(Graphics2D g2) {
    g2.setColor(colorPalette.getOverlayColor());
    g2.fill(getBounds());
    g2.setColor(colorPalette.getHeaderColor());
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 50));
    Inf101Graphics.drawCenteredString(g2, "Double Dash", 0, 0, getWidth(), 3 * getHeight() / 4);
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 25));
    Inf101Graphics.drawCenteredString(g2, "Press ENTER to start", 0, 0, getWidth(), 7 * getHeight() / 8);
  }

  private static void drawPlayer(Graphics2D g2, Rectangle2D playerBounds, BufferedImage playerSprite,
      Vector cameraEye) {
    Inf101Graphics.drawImage(g2, playerSprite, playerBounds.getX() - 5 + cameraEye.get(0),
        playerBounds.getY() - 17 + cameraEye.get(1), 1);
    // g2.setStroke(new java.awt.BasicStroke(2));
    // g2.setColor(Color.decode("#ff0000"));
    // g2.draw(playerBounds);
  }

  private static void drawLevel(Graphics2D g2, Iterable<GridCell<Tile>> levelTiles,
      CellPositionToPixelConverter converter, Vector cameraEye) {
    for (GridCell<Tile> cell : levelTiles) {
      if (cell.value() == null) {
        continue;
      }
      if (cell.pos().row() == 0) {
        Rectangle2D bounds = converter.getBoundsForCell(new CellPosition(-1, cell.pos().col()));
        Inf101Graphics.drawImage(g2, cell.value().getTileImage(), bounds.getX() + cameraEye.get(0),
            bounds.getY() + cameraEye.get(1),
            bounds.getWidth() / 32);
      }
      if (cell.pos().row() == 23) {
        Rectangle2D bounds = converter.getBoundsForCell(new CellPosition(24, cell.pos().col()));
        Inf101Graphics.drawImage(g2, cell.value().getTileImage(), bounds.getX() + cameraEye.get(0),
            bounds.getY() + cameraEye.get(1),
            bounds.getWidth() / 32);

      }
      if (cell.pos().col() == 0) {
        Rectangle2D bounds = converter.getBoundsForCell(new CellPosition(cell.pos().row(), -1));
        Inf101Graphics.drawImage(g2, cell.value().getTileImage(), bounds.getX() + cameraEye.get(0),
            bounds.getY() + cameraEye.get(1),
            bounds.getWidth() / 32);
      }
      if (cell.pos().col() == 17) {
        Rectangle2D bounds = converter.getBoundsForCell(new CellPosition(cell.pos().row(), 18));
        Inf101Graphics.drawImage(g2, cell.value().getTileImage(), bounds.getX() + cameraEye.get(0),
            bounds.getY() + cameraEye.get(1),
            bounds.getWidth() / 32);
      }
      Rectangle2D bounds = converter.getBoundsForCell(cell.pos());
      Inf101Graphics.drawImage(g2, cell.value().getTileImage(), bounds.getX() + cameraEye.get(0),
          bounds.getY() + cameraEye.get(1),
          bounds.getWidth() / 32);
    }
  }

  private void drawDeathCount(Graphics2D g2, int deathCount, Rectangle2D windowBounds) {
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 25));
    g2.setColor(colorPalette.getDeathTextColor(deathCount));
    Inf101Graphics.drawCenteredString(g2, "Death count: " + Integer.toString(deathCount), 0, 0,
        windowBounds.getWidth(), windowBounds.getHeight() / 8);
  }

  private void drawGameWonScreen(Graphics2D g2) {
    Inf101Graphics.drawImage(g2, winScreenBackgroundImage, 0, 0, 1);
    g2.setColor(Color.WHITE);
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 50));
    Inf101Graphics.drawCenteredString(g2, "Congrats, you WON!", 0, 0, getWidth(), 3 * getHeight() / 4);
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 25));
    Inf101Graphics.drawCenteredString(g2, "As a reward, treat yourself to a biscuit", 0, 0, getWidth(),
        7 * getHeight() / 8);
    g2.setFont(new Font(FONT_NAME, Font.BOLD, 15));
    Inf101Graphics.drawCenteredString(g2, "P.S. if you are crazy enough to want to play again, press ENTER", 0, 0,
        getWidth(),
        getHeight());
    // Congrats, you won!
    // Take a biscuit
  }

}