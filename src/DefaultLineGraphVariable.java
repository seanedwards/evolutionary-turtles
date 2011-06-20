
import java.awt.*;

public class DefaultLineGraphVariable implements ILineGraphVariable {

    public double value;
    public double max;
    public double min;
    public Color color;
    public String name;

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
