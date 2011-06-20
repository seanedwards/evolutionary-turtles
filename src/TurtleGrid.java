
import java.awt.Point;

/**
 * A class responsible for managing the turtle grid.
 */
public class TurtleGrid {

    public static double growRate = 0.05;

    private int horizontalCount;
    private int verticalCount;
    private int patchWidth;
    private int patchHeight;
    GridPatch[][] grid;

    public TurtleGrid(int hcount, int vcount, int width, int height) {
        patchWidth = width;
        patchHeight = height;

        horizontalCount = hcount;
        verticalCount = vcount;

        grid = new GridPatch[horizontalCount][verticalCount];

        for (int x = 0; x < horizontalCount; ++x) {
            for (int y = 0; y < verticalCount; ++y) {
                grid[x][y] = new GridPatch();
            }
        }
    }

    public int getWidth() {
        return horizontalCount;
    }

    public int getHeight() {
        return verticalCount;
    }

    public void growGrass(double time)
    {
        for (int x = 0; x < getWidth(); ++x)
        {
            for (int y = 0; y < getHeight(); ++y)
            {
                GridPatch p = this.getPatch(new Point(x, y));
                double mygrass = 1; //p.getGrass();
                p.setGrass(p.getGrass() + (mygrass * growRate * time));
                if (p.getGrass() > 0.25)
                {
                    GridPatch north = this.getPatch(new Point(x, y + 1));
                    GridPatch south = this.getPatch(new Point(x, y - 1));
                    GridPatch east = this.getPatch(new Point(x + 1, y));
                    GridPatch west = this.getPatch(new Point(x - 1, y));

                    north.setGrass(north.getGrass() + (mygrass * growRate * time));
                    south.setGrass(south.getGrass() + (mygrass * growRate * time));
                    east.setGrass(east.getGrass() + (mygrass * growRate * time));
                    west.setGrass(west.getGrass() + (mygrass * growRate * time));
                }
            }
        }
    }

    /**
     * Retrieves the GridPatch at the specified index.
     * If index exceeds the bounds of the grid, it will
     * be wrapped to within the grid's bounds.
     *
     * @ensures returned GridPatch != null
     */
    public GridPatch getPatch(Point index) {
        index = wrap(index);
        return grid[index.x][index.y];
    }

    /**
     * Converts world space coordinates to grid space coordinates.
     */
    Point worldToGrid(Vector2d coord) {
        int x = (int) (coord.getX() / patchWidth);
        int y = (int) (coord.getY() / patchHeight);
        return wrap(new Point(x, y));
    }

    /**
     * Wraps a point to within the grid bounds.
     * @param point The point in unbounded grid space.
     * @return A point within the bounds of the turtle grid.
     */
    Point wrap(Point point) {
        return new Point(point.x - (int) (Math.floor(point.x / (double) horizontalCount) * horizontalCount),
                point.y - (int) (Math.floor(point.y / (double) verticalCount) * verticalCount));
    }
}
