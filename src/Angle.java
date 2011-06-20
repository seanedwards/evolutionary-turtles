


/**
 * Handles angles in radians or degrees.
 * 
 * @author Sean Edwards
 */
public class Angle {

    public enum AngleType {

        DEGREES, RADIANS
    }
    private double degrees;

    public Angle() {
        setDegrees(0);
    }

    public Angle(AngleType t, double a) {
        if (t == AngleType.DEGREES) {
            setDegrees(a);
        } else if (t == AngleType.RADIANS) {
            setRadians(a);
        }
    }

    /**
     * Sets the angle in degrees. If greater than 360 degrees, this will wrap
     * the value to between 0 and 360.
     */
    private void setDegrees(double deg) {
        degrees = deg % 360;
    }

    /**
     * Sets the angle in radians. This converts the angle to degrees and then
     * calls setDegrees.
     */
    private void setRadians(double rad) {
        setDegrees(rad * (180 / java.lang.Math.PI));
    }

    /**
     * Gets the angle in degrees.
     */
    public double getDegrees() {
        return degrees;
    }

    /**
     * Gets the angle in radians.
     */
    public double getRadians() {
        return degrees * (java.lang.Math.PI / 180);
    }

    /**
     * Returns the angle equivalent to the angle, negative.
     */
    public Angle negate() {
        return new Angle(AngleType.DEGREES, -this.getDegrees());
    }

    /**
     * Adds two angles together and returns the new resulting angle.
     */
    public static Angle add(Angle a1, Angle a2) {
        return new Angle(AngleType.DEGREES, a1.getDegrees() + a2.getDegrees());
    }

    /**
     * Adds a scalar degree value to an angle, and returns the resulting angle.
     */
    public static Angle addDegrees(Angle a, double deg) {
        return new Angle(AngleType.DEGREES, a.getDegrees() + deg);
    }

    /**
     * Adds a scalar radian value to an angle, and returns the resulting angle.
     */
    public static Angle addRadians(Angle a, double rad) {
        return new Angle(AngleType.RADIANS, a.getRadians() + rad);
    }

    public String toString() {
        return getDegrees() + "d";
    }
}
