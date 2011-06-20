
public class WanderingTurtle extends AbstractTurtle {

    private double tickelapsed;

    public WanderingTurtle() {
        tickelapsed = 0;
    }

    public boolean collidesWith(AbstractTurtle t) {
        double d = Vector2d.subtract(t.getPosition(), this.getPosition()).squaredLength();
        double size = t.getSize() + this.getSize();
        return d <= (size * size);
    }

    public void think(double timescale) {
        tickelapsed += timescale;
        if (tickelapsed > 1) {
            this.turnRight(new Angle(Angle.AngleType.DEGREES, Math.random() * 30));
            this.turnLeft(new Angle(Angle.AngleType.DEGREES, Math.random() * 30));
            tickelapsed = 0;
        }

        this.forward(this.getSpeed() * timescale);
    }

    public void forward(double dist) {
        this.setPosition(Vector2d.wrap(Vector2d.add(this.getPosition(), this.getOrientation(), dist), new Vector2d(400, 400)));
    }

    public void backward(double dist) {
        this.forward(-dist);
    }

    public void turnRight(Angle a) {
        this.setOrientation(Angle.add(a, this.getOrientation()));
    }

    public void turnLeft(Angle a) {
        turnRight(a.negate());
    }

    public double getSpeed()
    {
        return 10;
    }
}
