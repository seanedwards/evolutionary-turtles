

import java.util.*;
/**
 *
 * @author Edwards
 */
public class Genome implements Iterable<Gene> {
    Map<Gene, Double> dna;
    List<Gene> genes;
    IMouth mouth;

    public Genome(IMouth mouth)
    {
        dna = new HashMap<Gene, Double>();
        this.mouth = mouth;
        genes = new ArrayList<Gene>();
    }

    public Genome mutate()
    {
        Genome g = new Genome(this.mouth);
        for (Map.Entry<Gene, Double> e : dna.entrySet())
        {
            g.dna.put(e.getKey(), e.getKey().mutate(e.getValue()));
            g.genes.add(e.getKey());
        }
        return g;
    }

    public void addGene(Gene g)
    {
        dna.put(g, g.getInitialValue());
        genes.add(g);
    }

    public double getGene(String name)
    {
        for (Map.Entry<Gene, Double> e : dna.entrySet())
        {
            if (e.getKey().getName().equals(name))
                return e.getValue();
        }
        return Double.NaN;
    }

    public IMouth getMouth()
    {
        return this.mouth;
    }

    public Iterator<Gene> iterator() {
        return this.genes.iterator();
    }
}
