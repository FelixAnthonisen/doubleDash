package no.uib.inf101.sem2.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.awt.Color;

public class TestColorPalette {

    @Test
    void testGetOverlayColor() {
        ColorPalette palette = new ColorPalette();
        assertEquals(new Color(0, 0, 0, 150), palette.getOverlayColor());
    }

    @Test
    void testGetDeathTextColor() {
        ColorPalette palette = new ColorPalette();
        assertEquals(new Color(255, 255, 255), palette.getDeathTextColor(0));
        assertEquals(new Color(255, 245, 245), palette.getDeathTextColor(10));
        assertEquals(new Color(255, 0, 0), palette.getDeathTextColor(255));
    }

    @Test
    void testGetHeaderColor() {
        ColorPalette palette = new ColorPalette();
        assertEquals(new Color(255, 255, 255, 252), palette.getHeaderColor());
        assertEquals(new Color(255, 255, 255, 249), palette.getHeaderColor());
        assertEquals(new Color(255, 255, 255, 246), palette.getHeaderColor());
    }
}
