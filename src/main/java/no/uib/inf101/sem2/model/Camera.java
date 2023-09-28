package no.uib.inf101.sem2.model;

import no.uib.inf101.util.linearAlgebra.Vector;

class Camera {
    private Vector eye = new Vector(new double[] { 0, 0 });
    private int shakeCounter = 0;

    /**
     * 
     * @return the eye position of the camera given as a 2D vector
     */
    Vector getEye() {
        return eye;
    }

    /**
     * Call to shake the camera. The camera will continue to shake in random
     * directions for 60 ticks
     */
    void shake() {
        shakeCounter = 60;
    }

    /**
     * Call to handle one game tick with respect to the camera. The method sets the
     * eye to a random value in the range -1.5<=x,y<1.5 if it should shake
     */
    void tick() {
        if (shakeCounter != 0) {
            if (--shakeCounter != 0) {

                eye = new Vector(new double[] { (Math.random() - 0.5) * 3, (Math.random() - 0.5) * 3 });
            } else {
                eye = new Vector(new double[] { 0, 0 });
            }
        }
    }
}
