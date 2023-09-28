package no.uib.inf101.doubleDash.model.direction;

public enum DashDirection {
    NORTH(0, -0.15),
    EAST(0.15, 0),
    SOUTH(0, 0.15),
    WEST(-0.15, 0),
    NORTH_EAST(Math.sqrt(0.01125), -Math.sqrt(0.01125)),
    NORTH_WEST(-Math.sqrt(0.01125), -Math.sqrt(0.01125)),
    SOUTH_EAST(Math.sqrt(0.01125), Math.sqrt(0.01125)),
    SOUTH_WEST(-Math.sqrt(0.01125), Math.sqrt(0.01125));

    private final double xVel, yVel;

    private DashDirection(double xVel, double yVel) {
        this.xVel = xVel;
        this.yVel = yVel;
    }

    /**
     * 
     * @param dashCounter how many ticks are left of the dash
     * @return the xVelocity the player should move in
     */
    public double getXVel(int dashCounter) {
        if (xVel != 0) {
            if (xVel > 0) {
                return Math.max(xVel * dashCounter, 1);
            }
            return Math.min(xVel * dashCounter, -1);
        }
        return 0;
    }

    /**
     * 
     * @param dashCounter how many ticks are left of the dash
     * @return the yVelocity the player should move in
     */
    public double getYVel(int dashCounter) {
        if (yVel != 0) {
            if (yVel > 0) {
                return Math.max(yVel * dashCounter, 1);
            }
            return Math.min(yVel * dashCounter, -1);
        }
        return 0;
    }

}
