


/**
 * Handles 2d vector math.
 * 
 * @author Sean Edwards
 */
public class Vector2d {

    private double x;
    private double y;

    public Vector2d() {
    }

    public Vector2d(double x, double y) {
        set(x, y);
    }

    public Vector2d(Angle a, double dist) {
        setPolar(a, dist);
    }

    public Vector2d(Vector2d v) {
        set(v.x, v.y);
    }

    private void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the vector's value in terms of polar coordinates.
     */
    private void setPolar(Angle a, double dist) {
        set(dist * java.lang.Math.cos(a.getRadians()), dist * java.lang.Math.sin(a.getRadians()));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2d negate() {
        return new Vector2d(-this.getX(), -this.getY());
    }

    /**
     * Get the angle component of the vector as polar coordinates.
     * @ensure 0 degrees = positive Y
     */
    public Angle angle() {
        if (this.getX() > 0) {
            return new Angle(Angle.AngleType.RADIANS, Math.asin(this.getY() / this.length()));
        } else if (this.getX() < 0) {
            return new Angle(Angle.AngleType.RADIANS, -Math.asin(this.getY() / this.length()) + Math.PI);
        }

        return new Angle();
    }

    /**
     * Normalize a vector to length of 1.
     * @ensures The returned vector will be of length 1.
     */
    public Vector2d normalize() {
        return Vector2d.scale(this, this.length());
    }

    /**
     * Get the length component of the vector as polar coordinates.
     * If possible, use squaredLength() instead.
     * This method requires finding the square root, which is slow.
     */
    public double length() {
        return Math.sqrt(this.squaredLength());
    }

    /**
     * Finds the squared length of the vector.
     */
    public double squaredLength() {
        return getX() * getX() + getY() * getY();
    }

    /**
     * Finds the squared distance between two vectors.
     */
    public static double squaredDistance(Vector2d v1, Vector2d v2) {
        return (add(v1, v2.negate()).squaredLength());
    }

    public static double distance(Vector2d v1, Vector2d v2) {
        return Math.sqrt(Vector2d.squaredDistance(v1, v2));
    }

    /**
     * Adds two vectors together and returns the resulting vector.
     */
    public static Vector2d add(Vector2d v1, Vector2d v2) {
        return new Vector2d(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Subtracts one vector from the other.
     * Note: A good way of thinking of subtraction is:
     * B - A yields A ---> B
     */
    public static Vector2d subtract(Vector2d v1, Vector2d v2) {
        return new Vector2d(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Scales a vector by a given magnitude.
     */
    public static Vector2d scale(Vector2d v, double scalar) {
        return new Vector2d(v.x * scalar, v.y * scalar);
    }

    public static Vector2d wrap(Vector2d v, Vector2d extents) {
        return new Vector2d(v.getX() - Math.floor(v.getX() / extents.getX()) * extents.getX(),
                v.getY() - Math.floor(v.getY() / extents.getY()) * extents.getY());
    }

    /**
     * Calculates the dot product of two vectors.
     */
    public static double dot(Vector2d v1, Vector2d v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Adds a polar coordinate to a vector and returns the resulting position.
     */
    public static Vector2d add(Vector2d v, Angle a, double radius) {
        return add(v, new Vector2d(a, radius));
    }

    public String toString() {
        return "{" + getX() + "," + getY() + "}";
    }
}
