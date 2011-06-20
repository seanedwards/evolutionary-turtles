


import java.util.*;
import java.lang.*;
import java.awt.Point;

/**
 * Implements the Bresenham algorithm adopted from 
 * http://www.gamedev.net/reference/articles/article1275.asp
 */
public class LineTracer implements Iterable<Point> {

    Point s;
    Point e;

    public LineTracer(Point start, Point end) {
        s = start;
        e = end;
    }

    public Iterator<Point> iterator() {
        return new Bresenham(s, e);
    }
}

/**
 * The Bresenham iterator, used by the LineTracer class.
 */
class Bresenham implements Iterator<Point> {

    int x, y;
    int xinc1, xinc2, yinc1, yinc2;
    int den, num, numadd, numpixels;
    int curpixel = 0;

    /**
     * Creates a new Bresenham iterator between two points.
     */
    public Bresenham(Point start, Point end) {
        int deltax = Math.abs(end.x - start.x);
        int deltay = Math.abs(end.y - start.y);

        x = start.x;
        y = start.y;

        if (end.x >= start.x) {
            xinc1 = xinc2 = 1;
        } else {
            xinc1 = xinc2 = -1;
        }

        if (end.y >= start.y) {
            yinc1 = yinc2 = 1;
        } else {
            yinc1 = yinc2 = -1;
        }


        if (deltax >= deltay) {
            xinc1 = 0;
            yinc2 = 0;
            den = deltax;
            num = deltax / 2;
            numadd = deltay;
            numpixels = deltax;
        } else {
            xinc2 = 0;
            yinc1 = 0;
            den = deltay;
            num = deltay / 2;
            numadd = deltax;
            numpixels = deltay;
        }
    }

    /**
     * @return Whether the iterator has more points to process.
     */
    public boolean hasNext() {
        return curpixel <= numpixels;
    }

    /**
     * @return The next point in the line.
     */
    public Point next() {
        Point p = new Point(x, y);

        num += numadd;
        if (num >= den) {
            num -= den;
            x += xinc1;
            y += yinc1;
        }
        x += xinc2;
        y += yinc2;

        ++curpixel;

        return p;
    }

    /**
     * Required by the iterator interface, but does nothing.
     */
    public void remove() {
    }
}
