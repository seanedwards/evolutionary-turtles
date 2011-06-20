
import java.awt.*;

/**
 *
 * @author Sean Edwards
 */
public class DefaultTurtleDecorator implements ITurtleDecorator {

    public void draw(Graphics g, AbstractTurtle t) {
        g.setColor(t.getColor());

        Vector2d pos = t.getPosition();
        g.drawOval((int) pos.getX() - (int)(t.getSize() / 2),
                (int) pos.getY() - (int)(t.getSize() / 2),
                (int)t.getSize(), (int)t.getSize());

        Vector2d nose = new Vector2d(t.getOrientation(), (t.getSize() * 0.75));
        g.drawLine((int) pos.getX(), (int) pos.getY(), (int) nose.getX() + (int) pos.getX(), (int) nose.getY() + (int) pos.getY());
    }
}
