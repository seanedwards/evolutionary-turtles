


import java.util.*;
import java.awt.*;
import javax.swing.*;

public class LineGraph extends JPanel {

    static final int xmargin = 35;
    static final int ymargin = 25;
    static final double graphUpdate = 0.1;
    private double secondTimer;
    private int steps;
    private java.util.List<GraphLine> lines;
    int[] xpoints;

    public LineGraph(int steps) {
        this.steps = steps;
        this.xpoints = new int[steps];
        this.lines = new ArrayList<GraphLine>();

        this.setBackground(Color.BLACK);
    }

    public void addVariable(ILineGraphVariable var) {
        lines.add(new GraphLine(this, var));
    }

    public void update(double timescale) {
        secondTimer += timescale;

        if (secondTimer > graphUpdate) {
            secondTimer -= graphUpdate;
            for (GraphLine g : lines) {
                g.update();
            }
        }

        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = this.getSize();

        g.setColor(Color.WHITE);
        g.drawLine(LineGraph.xmargin, 0, LineGraph.xmargin, size.height - LineGraph.ymargin);
        g.drawLine(LineGraph.xmargin, size.height - LineGraph.ymargin, size.width, size.height - LineGraph.ymargin);

        g.drawString("0%", 15, size.height - LineGraph.ymargin);
        g.drawString("50%", 7, (size.height - LineGraph.ymargin) / 2);
        g.drawString("100%", 0, 10);

        g.drawString("0s", size.width - 15, size.height - LineGraph.ymargin + 15);
        int duration = (int) (steps * graphUpdate);
        g.drawString(duration + "s", LineGraph.xmargin, size.height - LineGraph.ymargin + 15);

        g.drawString((duration / 2) + "s", LineGraph.xmargin + (size.width - LineGraph.xmargin) / 2, size.height - LineGraph.ymargin + 15);

        g.setColor(Color.GRAY);

        for (int x = 1; x < 4; ++x) {
            int y = x * (size.height - LineGraph.ymargin) / 4;
            g.drawLine(LineGraph.xmargin + 1, y, size.width, y);
        }

        for (int i = 0; i < this.steps; ++i) {
            xpoints[i] = (int) (((double) (size.width - LineGraph.xmargin + 1) / (double) this.steps) * i + LineGraph.xmargin + 1);
        }

        for (GraphLine l : lines) {
            int[] vals = new int[this.steps];
            int counter = 0;
            for (double d : l.getHistory()) {
                double range = l.getVar().getMax() - l.getVar().getMin();
                double nval = d - l.getVar().getMin();
                double percentage = nval / range;

                int height = size.height - LineGraph.ymargin;
                int value = (int) (height * percentage);
                vals[counter++] = height - value;
                ;
            }

            g.setColor(l.getVar().getColor());
            g.drawPolyline(xpoints, vals, this.steps);
        }
    }

    public int getSteps() {
        return this.steps;
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }
}

class GraphLine {

    private ILineGraphVariable var;
    double[] history;

    public GraphLine(LineGraph graph, ILineGraphVariable var) {
        this.var = var;
        this.history = new double[graph.getSteps()];
    }

    public ILineGraphVariable getVar() {
        return this.var;
    }

    public void update() {
        for (int x = 0; x < history.length - 1; ++x) {
            history[x] = history[x + 1];
        }
        history[history.length - 1] = this.var.getValue();
    }

    public double[] getHistory() {
        return history;
    }
}
