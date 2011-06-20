import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
 * TurtleWorld provides a high level drawing context for a Turtle
 */
public class TurtleWorld implements ITurtleListener, Iterable<AbstractTurtle> {

    private TurtleGrid grid;
    private Set<AbstractTurtle> turtles;
    private Queue<AbstractTurtle> turtlesToRemove;
    private java.util.List<Point> collisions;
    private boolean paused;
    private double timeElapsed;

    public TurtleWorld() {
        paused = false;
        grid = new TurtleGrid(40, 40, 10, 10);
        turtles = new HashSet<AbstractTurtle>();
        turtlesToRemove = new LinkedList<AbstractTurtle>();
    }

    public void tick(double timediff) {
        timeElapsed += timediff;
        AbstractTurtle rem = null;
        while ((rem = turtlesToRemove.poll()) != null) {
            deleteTurtle(rem);
        }

        if (isPaused() || turtles.isEmpty()) {
            return;
        }

        grid.growGrass(timediff);

        int oldpop = turtles.size();

        ArrayList<AbstractTurtle> allturtles = new ArrayList<AbstractTurtle>(turtles);
        for (AbstractTurtle t : allturtles) {
            t.think(timediff);
        }

        if (turtles.size() > oldpop * 2)
        {
            System.out.println("Population explosion!");
        }

    }

    public TurtleGrid getGrid()
    {
        return this.grid;
    }

    public GridPatch getPatch(Vector2d loc)
    {
        return grid.getPatch(grid.worldToGrid(loc));
    }

    public Iterator<AbstractTurtle> iterator() {
        return turtles.iterator();
    }

    public void addTurtle(AbstractTurtle t) {
        turtles.add(t);

        GridPatch p = grid.getPatch(grid.worldToGrid(t.getPosition()));
        p.addTurtle(t);

        t.addListener(this);
    }

    public void removeTurtle(AbstractTurtle t) {
        turtlesToRemove.add(t);
    }

    public double getTimeElapsed()
    {
        return this.timeElapsed;
    }

    public int turtleCount() {
        return turtles.size();
    }

    private void deleteTurtle(AbstractTurtle t) {
        t.removeListener(this);

        GridPatch p = grid.getPatch(grid.worldToGrid(t.getPosition()));
        p.removeTurtle(t);

        turtles.remove(t);
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean p) {
        paused = p;
    }

    public void turtlePositionChanged(AbstractTurtle t, Vector2d oldPosition, Vector2d newPosition) {
        GridPatch oldp = grid.getPatch(grid.worldToGrid(oldPosition));
        GridPatch newp = grid.getPatch(grid.worldToGrid(newPosition));

        if (!oldp.equals(newp)) {
            oldp.removeTurtle(t);
            newp.addTurtle(t);
        }

/*        GridPatch patch = newp;
        // For each turtle in the patch...
        for (AbstractTurtle turtle : newp) {
            // ... except the one that just moved here...
            if (turtle.equals(t)) {
                continue;
            }

            // If they are colliding...
            if (turtle.collidesWith(t)) {
                t.collidedWith(turtle);
                turtle.collidedWith(t);

                newp.incrementCollisions();

                //Find the collision vector (turtle ---> t)
                Vector2d cvec = Vector2d.subtract(t.getPosition(), turtle.getPosition());

                // Set the turtles facing away from each other, like they're fighting.
                t.setOrientation(cvec.angle());
                turtle.setOrientation(cvec.negate().angle());
            }
        }*/
    }

    public void turtleOrientationChanged(AbstractTurtle t, Angle oldOrientation, Angle newOrientation) {
    }

    public void turtleCollision(AbstractTurtle t1, AbstractTurtle t2) {
    }

    public void turtleDied(AbstractTurtle t)
    {
        //System.out.println("Turtle died.");
        this.removeTurtle(t);
    }

    public void turtleReproduced(AbstractTurtle parent, AbstractTurtle child) {
        //System.out.println("BABY TURTLE!");
        this.addTurtle(child);
    }
}
