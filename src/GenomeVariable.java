
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwards
 */
public class GenomeVariable extends AverageVariable {
    private String geneName;
    private TurtleWorld world;
    private Color color;

    public GenomeVariable(TurtleWorld w, String gene, Color c, boolean usemin)
    {
        super(usemin, true);
        this.color = c;
        this.world = w;
        this.geneName = gene;
    }

    public GenomeVariable(TurtleWorld w, String gene, Color c, double min, double max)
    {
        super(min, max);
        this.color = c;
        this.world = w;
        this.geneName = gene;
    }

    public GenomeVariable(TurtleWorld w, String gene, Color c)
    {
        super(true, true);
        this.color = c;
        this.world = w;
        this.geneName = gene;
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return geneName;
    }

    public double getValue() {
        double sum = 0;
        for (AbstractTurtle t : world)
        {
            CreatureTurtle c = (CreatureTurtle)t;
            sum += c.getDna().getGene(geneName);
        }
        return sum / world.turtleCount();
    }

}
