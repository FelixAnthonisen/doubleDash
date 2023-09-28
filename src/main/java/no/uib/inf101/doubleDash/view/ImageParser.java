package no.uib.inf101.doubleDash.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import no.uib.inf101.doubleDash.model.GameModel;

public class ImageParser {

    public static BufferedImage parseTiles(int offsetX, int offsetY) {
        BufferedImage tiles = Inf101Graphics.loadImageFromResources("tilemap_32x32.png");
        int tileSize = 32;
        return tiles.getSubimage(offsetX * tileSize, offsetY * tileSize, tileSize, tileSize);
    }

    public static BufferedImage parseSprites(int offsetX, int offsetY) {
        BufferedImage sprites = Inf101Graphics.loadImageFromResources("sprites_26x47.png");
        int width = 26;
        int height = 47;
        return sprites.getSubimage(offsetX * width, offsetY * height, width, height);
    }

    public static Color[][] parseLevels(int level) {
        BufferedImage levels = Inf101Graphics.loadImageFromResources("levels_18x24.png");
        int width = 18;
        int height = 24;
        return getLevelPixels(levels.getSubimage(level * width, 0, width, height));
    }

    // the right shift operations to find color value has been taken from
    // https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
    public static Color[][] getLevelPixels(BufferedImage level) {
        Color[][] colors = new Color[GameModel.LEVEL_ROWS][GameModel.LEVEL_COLS];
        for (int i = 0; i < colors.length; ++i) {
            for (int j = 0; j < colors[0].length; ++j) {
                int color = level.getRGB(j, i);
                int r = (color & 0x00ff0000) >> 16;
                int g = (color & 0x0000ff00) >> 8;
                int b = color & 0x000000ff;
                colors[i][j] = new Color(r, g, b);
            }
        }
        return colors;
    }
}