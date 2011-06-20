import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
 * The TurtleTest class invokes the main program of the Turtle System.
 */
public class TurtleTest {

    static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Turtles and Stuff");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        reset();
        frame.setVisible(true);

        while (true) {
            frame.repaint();
        }
    }

    public static void reset() {
        frame.setContentPane(getNewWorld());
        frame.pack();
    }

    static JPanel getNewWorld() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        JPanel buttonPane = new JPanel();
        JPanel dataPane = new JPanel(new GridLayout(1, 2));

        LineGraph g = new LineGraph(100 - 35);
        g.setSize(400, 400);

        TurtleWorld w = new TurtleWorld();
        TurtlePanel p = new TurtlePanel(w, g);

        dataPane.add(p);
        dataPane.add(g);

        buttonPane.add(new JButton(new ResetAction("Reset", null)));
        buttonPane.add(new JButton(new PauseAction("Pause/Resume", null, w)));

        contentPane.add(dataPane);
        contentPane.add(buttonPane);

        IMouth herbavore = new GrassMouth(w);

        Genome genesis = new Genome(herbavore);
        genesis.addGene(new Gene("appetite", 1, 0.25));
        genesis.addGene(new Gene("reproduction", 15, 1));
        genesis.addGene(new Gene("reproductionRatio", 0.5, 0.25));
        genesis.addGene(new Gene("speed", 10, 0.5));
        genesis.addGene(new Gene("lifetime", 60, 5));

        for (int x = 0; x < 250; ++x) {
            AbstractTurtle t = new CreatureTurtle(genesis.mutate(), Math.random() * 30);
            t.addDecorator(new DefaultTurtleDecorator());
            t.setPosition(new Vector2d(Math.random() * 400, Math.random() * 400));
            t.setOrientation(new Angle(Angle.AngleType.DEGREES, Math.random() * 360));
            w.addTurtle(t);
        }

        g.addVariable(new PopulationVariable(w));
        g.addVariable(new FoodVariable(w));
        g.addVariable(new EnergyVariable(w));

        g.addVariable(new GenomeVariable(w, "appetite", Color.CYAN));
        g.addVariable(new GenomeVariable(w, "reproduction", Color.PINK, false));
        g.addVariable(new GenomeVariable(w, "reproductionRatio", Color.MAGENTA, 0, 1));
        g.addVariable(new GenomeVariable(w, "speed", Color.BLUE));
        g.addVariable(new GenomeVariable(w, "lifetime", Color.ORANGE));
        return contentPane;
    }
}

class PauseAction extends AbstractAction {

    TurtleWorld tw;

    public PauseAction(String text, ImageIcon icon, TurtleWorld tw) {
        super(text, icon);
        this.tw = tw;
    }

    public void actionPerformed(ActionEvent e) {
        tw.setPaused(!tw.isPaused());
    }
}

class ResetAction extends AbstractAction {

    public ResetAction(String text, ImageIcon icon) {
        super(text, icon);
    }

    public void actionPerformed(ActionEvent e) {
        TurtleTest.reset();
    }
}
