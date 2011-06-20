import java.awt.Color;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwards
 */
public class EnergyVariable extends AverageVariable {
    private TurtleWorld world;
    public EnergyVariable(TurtleWorld w)
    {
        super(false, true);
        this.world = w;
    }

    public Color getColor() {
        return Color.RED;
    }

    public String getName() {
        return "Energy";
    }

    public double getValue() {
        double sum = 0;
        for (AbstractTurtle t : world)
        {
            CreatureTurtle c = (CreatureTurtle)t;
            sum += c.getEnergy();
        }
        return sum / world.turtleCount();
    }
}
