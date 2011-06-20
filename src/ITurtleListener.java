
public interface ITurtleListener {

    void turtlePositionChanged(AbstractTurtle t, Vector2d oldPosition, Vector2d newPosition);

    void turtleOrientationChanged(AbstractTurtle t, Angle oldOrientation, Angle newOrientation);

    void turtleCollision(AbstractTurtle t1, AbstractTurtle t2);

    void turtleDied(AbstractTurtle t);

    void turtleReproduced(AbstractTurtle parent, AbstractTurtle child);
}
