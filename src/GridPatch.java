
import java.util.*;
import java.awt.*;

public class GridPatch implements Iterable<AbstractTurtle> {
    public static double maxGrass = 50;
    private Set<AbstractTurtle> turtles;
    private int collisions;
    private double grass;

    public GridPatch() {
        turtles = new HashSet<AbstractTurtle>();
        setGrass(maxGrass * Math.random());
    }

    public void addTurtle(AbstractTurtle t) {
        turtles.add(t);
    }

    public void removeTurtle(AbstractTurtle t) {
        turtles.remove(t);
    }

    public void incrementCollisions() {
        ++collisions;
    }

    public void resetCollisions() {
        collisions = 0;
    }

    public double getGrass()
    {
        return this.grass;
    }

    public void setGrass(double grass)
    {
        this.grass = Math.min(Math.max(grass, 0), maxGrass);
    }

    public double consumeGrass(double amount)
    {
        double consumed = Math.min(amount, grass);
        setGrass(getGrass() - consumed);
        return consumed;
    }

    /**
     * Returns an iterator over all turtles on the patch.
     * @ensures A turtle will only exist once in a patch.
     */
    public Iterator<AbstractTurtle> iterator() {
        return turtles.iterator();
    }

    // Heatmap functions:
    //b = 0.004x^2 - 2x + 256
    //g = 4x - 0.016x^2
    //r = 0.004x^2 + 0.016x
    // W|A: plot (0.004x^2 - 2x + 256) and (4x - 0.016x^2) and (0.004x^2 + 0.016x) and ((0.004x^2 - 2x + 256) + (4x - 0.016x^2) + (0.004x^2 + 0.016x)) for x from 0 to 256
    public Color getColor() {
        int x = getCollisionCount() * 10;

        int r = x + 64;
        int g = x + 64;
        int b = x + 64;
        /*int r = (int)((0.004 * Math.pow(x, 2)) - (2 * x) + 256);
        int g = (int)(4 * x - (0.016 * Math.pow(x, 2)));
        int b = (int)(0.004 * Math.pow(x, 2) + (0.016 * x));*/
        return new Color((grass == 0 ? 255 : 0), (int)((grass / maxGrass) * 255), 0);
        //return new Color(bcv(r), bcv(g), bcv(b));
    }

    private int bcv(int i) {
        return Math.max(0, Math.min(255, i));
    }

    public int getCollisionCount() {
        return collisions;
    }
}
