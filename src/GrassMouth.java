/**
 *
 * @author Edwards
 */
public class GrassMouth implements IMouth {
    TurtleWorld world;

    public GrassMouth(TurtleWorld w)
    {
        this.world = w;
    }
    
    public double eat(Vector2d v, double amount) {
        //System.out.println("Turtle ate " + amount + " grass");
        return world.getPatch(v).consumeGrass(amount);
    }

}
