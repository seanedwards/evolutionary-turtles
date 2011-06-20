
/**
 *
 * @author Edwards
 */
public abstract class AverageVariable implements ILineGraphVariable
{
    public AverageVariable(double min, double max)
    {
        this.maxval = max;
        this.minval = min;
        this.useMin = false;
        this.useMax = false;
    }
    public AverageVariable(boolean useMin, boolean useMax)
    {
        this.useMin = useMin;
        this.useMax = useMax;
        if (useMax)
            maxval = Double.NaN;
        if (useMin)
            minval = Double.NaN;
    }
    public AverageVariable()
    {
        this.useMin = false;
        this.useMax = true;
        maxval = Double.NaN;
        minval = 0;
    }
    boolean useMax;
    boolean useMin;

    double maxval;
    double minval;

    public double getMax() {
        if (!useMax)
            return maxval;

        if (Double.isNaN(maxval))
            maxval = getValue();
        maxval = Math.max(getValue(), maxval);
        return maxval;
    }

    public double getMin()
    {
        if (!useMin)
            return minval;
        
        if (Double.isNaN(minval))
            minval = getValue();
        minval = Math.min(getValue(), minval);
        return minval;
    }
}
