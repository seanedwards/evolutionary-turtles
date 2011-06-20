import java.awt.Color;
/**
 *
 * @author Edwards
 */
public class FoodVariable implements ILineGraphVariable {
    private TurtleWorld world;
    public FoodVariable(TurtleWorld w)
    {
        this.world = w;
    }

    public Color getColor() {
        return Color.GREEN;
    }

    public String getName() {
        return "Food Supply";
    }

    public double getValue() {
        double ret = 0;
        for (int x = 0; x < 100; ++x)
        {
            for (int y = 0; y < 100; ++y)
            {
                Vector2d v = new Vector2d(x, y);
                GridPatch p = world.getPatch(v);
                ret += p.getGrass();
            }
        }
        return ret;
    }

    public double getMax() {
        return GridPatch.maxGrass * 100 * 100;
    }

    public double getMin() {
        return 0;
    }
}
