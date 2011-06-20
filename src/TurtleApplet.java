import javax.swing.*;
import java.awt.event.*;
/**
 *
 */
public class TurtleApplet extends JApplet {
  Timer time;
  JPanel frame;

  public void init()
  {
    frame = TurtleTest.getNewWorld();
    getContentPane().add(frame);

    final JApplet a = this;


    time = new Timer(1, new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        repaint();
      }
    });
    time.setInitialDelay(0);
    time.setCoalesce(false);
  }

  public void start()
  {
    time.start();
  }

  public void stop()
  {
    time.stop();
  }

}

/*!
 * \mainpage COS 225 Project 5: Infected Turtles (Sean Edwards)
 *
 * <h1> Graph Key</h1>
 * <p>Values on the graph represent the average values of all turtles in the world.</p>
 *
 * <div>
 * <h2>Population (White)</h2>
 * <p>The number of turtles currently populating the world.</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:green">Food Density</h2>
 * <p>Each grid patch has a maximum level of energy it can store. This value
 * indicates exactly what percentage of the whole map is saturated with food.</p>
 * <p><strong>Range:</strong> Percentage of food saturation.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:red">Energy Reserves</h2>
 * <p>The average energy reserves of the turtles.</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:cyan">Appetite</h2>
 * <p>How much grass a turtle will eat over time, and how much energy it requires to move.</p>
 * <p>A turtle's appetite is represented by it's size.</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:pink">Reproduction</h2>
 * <p>How much energy is required to reproduce</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:magenta">Reproduction Ratio</h2>
 * <p>The percentage of a turtle's own energy store that it will give to it's offspring.</p>
 * <p><strong>Range:</strong> Percentage of current energy stores.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:blue">Speed</h2>
 * <p>How quickly a turtle moves over time.</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <div>
 * <h2 style="color:orange">Lifetime</h2>
 * <p>How long a turtle is permitted to live before being killed by the system.</p>
 * <p><strong>Range:</strong> Percentage of observed previous values.</p>
 * </div>
 *
 * <applet code="TurtleApplet" archive="applet.jar", width="800", height="400" />
 *
 * 
 *<!--         g.addVariable(new GenomeVariable(w, "appetite", Color.CYAN));
        g.addVariable(new GenomeVariable(w, "reproduction", Color.PINK, false));
        g.addVariable(new GenomeVariable(w, "reproductionRatio", Color.MAGENTA, 0, 1));
        g.addVariable(new GenomeVariable(w, "speed", Color.BLUE));
        g.addVariable(new GenomeVariable(w, "lifetime", Color.ORANGE));-->
 */