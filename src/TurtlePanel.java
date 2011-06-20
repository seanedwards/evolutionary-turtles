import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwards
 */
public class TurtlePanel extends JPanel {
    private TurtleWorld world;
    private LineGraph graph;
    private long lasttick;
    
    public TurtlePanel(TurtleWorld world, LineGraph graph)
    {
        super();
        this.graph = graph;
        lasttick = System.nanoTime();
        this.world = world;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        long thistick = System.nanoTime();
        double timediff = (thistick - lasttick) / 1000000000.0;
        world.tick(timediff);
        lasttick = thistick;

        Dimension size = new Dimension(400, 400);

        for (int x = 0; x < world.getGrid().getWidth(); ++x) {
            for (int y = 0; y < world.getGrid().getHeight(); ++y) {
                GridPatch p = world.getGrid().getPatch(new Point(x, y));
                g.setColor(p.getColor());
                g.fillRect(x * (size.width / world.getGrid().getWidth()), y * (size.height / world.getGrid().getHeight()),
                        (size.width / world.getGrid().getWidth()), (size.height / world.getGrid().getHeight()));
            }
        }

        for (AbstractTurtle t : world) {
            t.draw(g);
        }

        graph.update(timediff);
    }
}
