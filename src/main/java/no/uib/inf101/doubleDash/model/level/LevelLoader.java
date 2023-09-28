package no.uib.inf101.doubleDash.model.level;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import no.uib.inf101.doubleDash.grid.CellPosition;
import no.uib.inf101.doubleDash.model.GameModel;
import no.uib.inf101.doubleDash.model.tiles.*;
import no.uib.inf101.doubleDash.view.ImageParser;

public class LevelLoader {
    private final Map<Color, Tile> tileColors = new HashMap<>();

    /**
     * Call to create a new LevelLoader object
     */
    public LevelLoader() {
        tileColors.put(new Color(255, 0, 0), new PortalTile());
        tileColors.put(new Color(100, 100, 100), new BrickTile());
        tileColors.put(new Color(0, 100, 0), new GrassTile());
        tileColors.put(new Color(75, 45, 15), new DirtTile());
        tileColors.put(new Color(0, 0, 0), new SpikeTile());
        tileColors.put(new Color(255, 255, 0), new PowerupTile());
        tileColors.put(new Color(175, 175, 175), new SignTile());
    }

    /**
     * Call to load a level
     * 
     * @param levelNum the number of the level that should be loaded
     * @return the level that is loaded
     */
    public Level loadLevel(int levelNum) {
        Random patternGenerator = new Random(123456789);
        Level level = new Level(GameModel.LEVEL_ROWS, GameModel.LEVEL_COLS);
        Color[][] colors = ImageParser.parseLevels(levelNum);
        for (int i = 0; i < level.rows(); ++i) {
            for (int j = 0; j < level.cols(); ++j) {
                Color color = colors[i][j];
                if (color.equals(Color.decode("#0000ff"))) {
                    level.setSpawnPos(new CellPosition(i, j));
                }
                level.set(new CellPosition(i, j),
                        tileColors.get(color) == null ? null
                                : tileColors.get(color).patternize(patternGenerator.nextInt(16)));
            }
        }
        return level;
    }
}
