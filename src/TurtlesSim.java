import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwards
 */
public class TurtlesSim {

    public static void main(String[] args)
    {
        TurtleWorld world = new TurtleWorld();
        IMouth herbavore = new GrassMouth(world);

        Genome genesis = new Genome(herbavore);
        genesis.addGene(new Gene("appetite", 1, 0.25));
        genesis.addGene(new Gene("reproduction", 15, 1));
        genesis.addGene(new Gene("reproductionRatio", 0.5, 0.25));
        genesis.addGene(new Gene("speed", 10, 0.5));
        genesis.addGene(new Gene("lifetime", 60, 5));

        StatsLogger log = new StatsLogger(world, genesis, "data15.txt");
        
        for (int x = 0; x < 250; ++x) {
            CreatureTurtle t = new CreatureTurtle(genesis.mutate(), Math.random() * 30);
            t.setPosition(new Vector2d(Math.random() * 400, Math.random() * 400));
            t.setOrientation(new Angle(Angle.AngleType.DEGREES, Math.random() * 360));
            world.addTurtle(t);
            log.setupTurtle(t);
        }

        int secs = 60*60;
        for (int x = 0; x < secs; ++x) {
            if (x % 100 == 0)
                System.out.println((int)((x / (double)secs) * 100) + "%");
            
            world.tick(1);
        }
        
        try {
            log.close();
        } catch (IOException ex) {
            Logger.getLogger(TurtlesSim.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Done.");
    }
}

class StatsLogger implements ITurtleListener
{
    TurtleWorld world;
    BufferedWriter turtleWriter;
    Map<CreatureTurtle, TurtleData> data;

    class TurtleData
    {
        public int reproductionCount;
        public double distanceTravled;
        public double birthTime;
        public double deathTime;
        public double lifespan;
        public Genome dna;
    }

    public StatsLogger(TurtleWorld world, Genome genesis, String turtleFile)
    {
        data = new HashMap<CreatureTurtle, TurtleData>();
        this.world = world;
        
        try {
            turtleWriter = new BufferedWriter(new FileWriter(new File(turtleFile)));
            turtleWriter.write("children birthtime deathtime distance lifespan");

            for (Gene g : genesis)
            {
                turtleWriter.write(" dna" + g.getName());
            }
            turtleWriter.newLine();
        } catch (IOException ex) {
            Logger.getLogger(StatsLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void writeFrame(TurtleData data) throws IOException
    {
        if (data.lifespan < 1)
            return;

        turtleWriter.write(data.reproductionCount + " " +
                data.distanceTravled + " " +
                data.birthTime + " " + data.deathTime + " " + data.lifespan);

        Genome dna = data.dna;
        for (Gene g : dna)
        {
            turtleWriter.write(" " + dna.getGene(g.getName()));
        }
        turtleWriter.newLine();
    }

    public void close() throws IOException
    {
        for (Map.Entry<CreatureTurtle, TurtleData> e : this.data.entrySet())
        {
            writeFrame(e.getValue());
        }
        turtleWriter.close();

    }
    public void turtlePositionChanged(AbstractTurtle t, Vector2d oldPosition, Vector2d newPosition)
    {
        if (t instanceof CreatureTurtle)
        {
            CreatureTurtle turtle = (CreatureTurtle)t;

            TurtleData tdat = data.get(turtle);

            if (tdat == null)
            {
                return;
            }
            tdat.distanceTravled += Vector2d.subtract(newPosition, oldPosition).length();
        }
    }

    public void turtleOrientationChanged(AbstractTurtle t, Angle oldOrientation, Angle newOrientation) {
    }

    public void turtleCollision(AbstractTurtle t1, AbstractTurtle t2) {
    }

    public void turtleDied(AbstractTurtle t)
    {
        TurtleData tdat = data.get((CreatureTurtle) t);
        tdat.deathTime = world.getTimeElapsed();
        tdat.lifespan = tdat.deathTime - tdat.birthTime;
        try {
            this.writeFrame(tdat);
        } catch (IOException ex) {
            Logger.getLogger(StatsLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            data.remove((CreatureTurtle) t);
        }
    }

    public void setupTurtle(CreatureTurtle t)
    {
            TurtleData tdat = new TurtleData();
            tdat.reproductionCount = 0;
            tdat.birthTime = world.getTimeElapsed();
            tdat.dna = t.getDna();
            data.put(t, tdat);
            t.addListener(this);
    }

    public void turtleReproduced(AbstractTurtle parent, AbstractTurtle child)
    {
        if (parent instanceof CreatureTurtle)
        {
            CreatureTurtle turtle = (CreatureTurtle)parent;

            TurtleData tdat = data.get(turtle);

            if (tdat == null)
            {
                Logger.getLogger(StatsLogger.class.getName()).log(Level.SEVERE, null, "No TurtleData present!");
            }
            else
            {
                ++tdat.reproductionCount;
            }
        }

        if (child instanceof CreatureTurtle)
        {
            this.setupTurtle((CreatureTurtle)child);
        }
    }
}
