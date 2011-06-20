/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Sean Edwards
 */
public class Gene {
    private double mutationFactor;
    private double initValue;
    private String name;

    public Gene(String name, double init, double mutation)
    {
        this.name = name;
        this.initValue = init;
        this.mutationFactor = mutation;
    }

    public double mutate(double source)
    {
        return source + (Math.random() * mutationFactor) - (mutationFactor / 2);
    }

    public String getName()
    {
        return this.name;
    }

    public double getInitialValue()
    {
        return this.initValue;
    }

    public boolean equals(Gene g)
    {
        return this.name.equals(g.name);
    }

}
