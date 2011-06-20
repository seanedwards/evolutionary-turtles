/**
 * @author Sean Edwards
 */
public class CreatureTurtle extends WanderingTurtle {
    private double energy;
    private double lifetime;
    private Genome dna;

    public CreatureTurtle(Genome genes, double initEnergy) {
        super();
        this.dna = genes;
        this.energy = initEnergy;
        lifetime = this.dna.getGene("lifetime") * Math.random();
    }

    public double getEnergy() {
        return this.energy;
    }

    public void setEnergy(double energy)
    {
        this.energy = energy;
        if (energy <= 0)
        {
            this.kill();
        }
    }

    public double getSize()
    {
        return dna.getGene("appetite") * 5;
    }

    public void setSize(double s)
    {
    }

    public Genome getDna()
    {
        return this.dna;
    }

    public void think(double timescale) {
        if (!this.isAlive())
            System.out.println("Dead turtle thinking: " + this);
        super.think(timescale);
        lifetime += timescale;
        if (lifetime > dna.getGene("lifetime") || dna.getGene("appetite") <= 0)
        {
            this.kill();
            return;
        }
        
        this.setEnergy(this.getEnergy() + this.dna.getMouth().eat(this.getPosition(), 
                1.5 * timescale * this.dna.getGene("appetite")));

        if (!this.isAlive())
            return;

        double reproduction = dna.getGene("reproduction");
        if (this.getEnergy() > reproduction && reproduction > 1)
        {
            this.reproduced(this.reproduce());
        }
    }

    public CreatureTurtle reproduce()
    {
        double ratio = Math.max(0, Math.min(1, dna.getGene("reproductionRatio")));
        CreatureTurtle c = new CreatureTurtle(this.dna.mutate(), this.getEnergy() * ratio);
        c.addDecorator(new DefaultTurtleDecorator());
        c.setPosition(this.getPosition());
        c.setOrientation(new Angle(Angle.AngleType.DEGREES, Math.random() * 360));
        this.setEnergy(this.getEnergy() - (this.getEnergy() * ratio));
        return c;
    }

    public double getSpeed()
    {
        return this.dna.getGene("speed");
    }

    public void forward(double dist)
    {
        super.forward(dist);
        this.setEnergy(this.getEnergy() - (dist * 0.1 * this.dna.getGene("appetite")));
    }
}
