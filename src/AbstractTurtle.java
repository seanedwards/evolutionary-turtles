
import java.util.*;
import java.awt.*;

/**
 * A turtle for a turtle graphics system.
 */
public abstract class AbstractTurtle {
    /**
     * Instance variables
     */
    private Angle orientation;
    private Vector2d position;
    private double size;
    private Set<ITurtleListener> listeners;
    private java.util.List<ITurtleDecorator> decorators;
    private boolean pen;
    protected boolean alive = true;

    /**
     * Abstract methods
     */
    abstract void think(double timescale);

    abstract boolean collidesWith(AbstractTurtle t);

    void draw(Graphics g) {
        if (decorators != null)
        {
            for (ITurtleDecorator d : decorators) {
                d.draw(g, this);
            }
        }
    }

    // Constructors:
    /**
     * Create a new Turtle with heading due north (0 degrees), and
     * position at the origin (0,0)
     */
    public AbstractTurtle() {
        setOrientation(new Angle());
        setPosition(new Vector2d(0.0, 0.0));
    }

    /**
     * Create a new Turtle with specified heading and position
     */
    public AbstractTurtle(Angle o, Vector2d p) {
        setOrientation(o);
        setPosition(p);
    }

    /**
     * Create a new Turtle with specified heading and
     * position at the origin (0,0)
     */
    public AbstractTurtle(double inith) {
        setOrientation(new Angle(Angle.AngleType.DEGREES, inith));
        setPosition(new Vector2d(0.0, 0.0));
    }

    /**
     * Create a new Turtle with heading due north (0 degrees) and
     * specified position
     */
    public AbstractTurtle(Vector2d position) {
        setOrientation(new Angle());
        setPosition(position);
    }

    /**
     * Retrieves the orientation of the turtle.
     */
    public Angle getOrientation() {
        return this.orientation;
    }

    /**
     * Sets the orientation of the turtle.
     * @ensures All listeners are informed of the change in orientation.
     * This occurs after the actual change has taken place, so calls to
     * t.getOrientation() will return the new orientation.
     */
    public void setOrientation(Angle a) {
        assert (a != null);

        Angle old = this.orientation;
        this.orientation = a;

        if (this.listeners != null) {
            for (ITurtleListener l : this.listeners) {
                l.turtleOrientationChanged(this, old, a);
            }
        }
    }

    /**
     * Retrieves the turtle's position within global world space.
     */
    public Vector2d getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the turtle.
     * @ensures All listeners are informed of the change in position.
     * This occurs after the actual change has taken place, so calls to
     * t.getPosition() will return the new position.
     */
    public void setPosition(Vector2d p) {
        assert (p != null);

        Vector2d old = this.position;
        this.position = p;

        if (this.listeners != null) {
            for (ITurtleListener l : this.listeners) {
                l.turtlePositionChanged(this, old, p);
            }
        }
    }

    public double getSize()
    {
        return this.size;
    }

    public void setSize(double s)
    {
        this.size = s;
    }

    public void kill()
    {
        alive = false;
        if (this.listeners != null)
        {
            for (ITurtleListener l : this.listeners)
            {
                l.turtleDied(this);
            }
            this.listeners.clear();
        }
    }

    public boolean isAlive()
    {
        return this.alive;
    }

    public void reproduced(AbstractTurtle a)
    {
        if (this.listeners != null)
        {
            for (ITurtleListener l : this.listeners)
            {
                l.turtleReproduced(this, a);
            }
        }
    }

    public void collidedWith(AbstractTurtle t) {
        if (this.listeners != null) {
            for (ITurtleListener l : this.listeners) {
                l.turtleCollision(this, t);
            }
        }
    }

    public java.awt.Color getColor() {
        return java.awt.Color.white;
    }

    /**
     * Returns the status of the turtle pen.
     */
    public boolean isPenDown() {
        return this.pen;
    }

    /**
     * Sets the status of the turtle pen.
     * @ensures If down is true, further turtle movements will
     * produce straight lines in the world. Otherwise, no lines
     * will be drawn.
     */
    public void setPenDown(boolean down) {
        this.pen = down;
    }

    /**
     * Adds listener L to the turtle.
     * @ensures Listener L will receive updates when this turtle
     * changes position or orientation.
     */
    public void addListener(ITurtleListener l) {
        if (this.listeners == null) {
            this.listeners = new HashSet<ITurtleListener>();
        }

        this.listeners.add(l);
    }

    /**
     * Removes listener L from the turtle.
     * @ensures Listener L will no longer receive updates when
     * this turtle changes position or orientation.
     */
    public void removeListener(ITurtleListener l) {
        if (this.listeners != null) {
            this.listeners.remove(l);
        }
    }

    /**
     * Adds listener L to the turtle.
     * @ensures Listener L will now be drawn with the turtle.
     */
    public void addDecorator(ITurtleDecorator l) {
        if (this.decorators == null) {
            this.decorators = new ArrayList<ITurtleDecorator>();
        }

        this.decorators.add(l);
    }

    /**
     * Removes decorator L from the turtle.
     * @ensures Decorator L will no longer be drawn.
     */
    public void removeDecorator(ITurtleDecorator l) {
        if (this.decorators != null) {
            this.decorators.remove(l);
        }
    }
}
