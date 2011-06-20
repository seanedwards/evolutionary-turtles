import java.awt.Color;
/**
 *
 * @author Edwards
 */
public class PopulationVariable extends AverageVariable {
    private TurtleWorld world;
    private double maxTurtles;
    public PopulationVariable(TurtleWorld w)
    {
        super(false, true);
        this.world = w;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public String getName() {
        return "Population";
    }

    public double getValue() {
        maxTurtles = Math.max(maxTurtles, world.turtleCount());
        return world.turtleCount();
    }
}
