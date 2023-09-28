package no.uib.inf101.doubleDash.view;

import java.awt.Color;

public class ColorPalette {
    private int callCounter = 255;
    private boolean counterGoingDown = true;

    /**
     * At 0 deaths the method return white. As the deathcount go up the color will
     * get more and more red until it's completely red. Caps out at 255 deaths
     * 
     * @param deathCount how many times the player has died
     * @return the death text color
     */
    public Color getDeathTextColor(int deathCount) {
        return new Color(255, Math.max(0, 255 - deathCount), Math.max(0, 255 - deathCount));
    }

    /**
     * 
     * @return a semi transparent black color
     */
    public Color getOverlayColor() {
        return new Color(0, 0, 0, 150);
    }

    /**
     * fades in or out every time the method is called
     * 
     * @return the header color
     */
    public Color getHeaderColor() {
        callCounter += counterGoingDown ? -3 : 3;
        if (callCounter <= 0) {
            counterGoingDown = false;
            callCounter = 0;
        } else if (callCounter >= 255) {
            counterGoingDown = true;
            callCounter = 255;
        }

        return new Color(255, 255, 255, callCounter);
    }
}
