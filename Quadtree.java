import MyStack.*;
import MyStack.Point;

import java.awt.*;

public class Quadtree {

    public final int QT_CAPACITY = 4;

    // Boundaries of current 'node'
    Square boundary;

    // Array of points within boundary
    PointStack points = new PointStack();

    // children
    Quadtree nw;
    Quadtree ne;
    Quadtree sw;
    Quadtree se;

    public Quadtree(Square boundary) {
        this.boundary = boundary;
    }

    public boolean insert(Point p) {
        if (!boundary.contains(p)) {
            return false;
        }

        // if there is space in the quad and its not subdivided, add object here
        if (points.size() < QT_CAPACITY && nw == null) {
            points.push(p);
            return true;
        }

        // otherwise, subdivide and add the point to whichever node will accept it and
        // insert all node into the subclasses
        if (nw == null) {
            subdivide();
            Point[] ps = points.getAll();
            for (Point sP : ps) {
                this.insert(sP);
            }
        }

        if (nw.insert(p))
            return true;
        if (ne.insert(p))
            return true;
        if (sw.insert(p))
            return true;
        if (se.insert(p))
            return true;

        return false;
    }

    public void subdivide() {
        double x = this.boundary.getCenter().getX();
        double y = this.boundary.getCenter().getY();
        double halfLength = this.boundary.getHalfLength() / 2;

        Square nw = new Square(new Point(x - halfLength, y + halfLength), halfLength);
        this.nw = new Quadtree(nw);
        Square ne = new Square(new Point(x + halfLength, y + halfLength), halfLength);
        this.ne = new Quadtree(ne);
        Square sw = new Square(new Point(x - halfLength, y - halfLength), halfLength);
        this.sw = new Quadtree(sw);
        Square se = new Square(new Point(x + halfLength, y - halfLength), halfLength);
        this.se = new Quadtree(se);

    }

    public PointStack queryRange(Square range) {
        PointStack pointsInRange = new PointStack();

        if (!this.boundary.intersects(range))
            return pointsInRange;

        // only if its the end node add points to stack
        if (this.nw == null) {
            Point[] pointsArray = this.points.getAll();
            if (pointsArray != null) {
                for (Point p : pointsArray) {
                    if (range.contains(p))
                        pointsInRange.push(p);
                }
            }
            return pointsInRange;
        }

        pointsInRange.pushStack(nw.queryRange(range));
        pointsInRange.pushStack(ne.queryRange(range));
        pointsInRange.pushStack(sw.queryRange(range));
        pointsInRange.pushStack(se.queryRange(range));

        return pointsInRange;
    }

    public static void main(String[] args) {
        int length = 1000;
        StdDraw.setCanvasSize(length, length);
        StdDraw.setScale(0, length);

        Square rootBoundry = new Square(new Point(length / 2, length / 2), length / 2);
        Quadtree root = new Quadtree(rootBoundry);

        for (int i = 0; i < 800; i++) {
            double rX = Math.random() * length;
            double rY = Math.random() * length;
            Point p = new Point(rX, rY);
            StdDraw.setPenColor(Color.GRAY);
            StdDraw.filledCircle(p.getX(), p.getY(), 2);
            if (root.insert(p)) {
                StdDraw.setPenColor(Color.GRAY);
                StdDraw.filledCircle(p.getX(), p.getY(), 2);
                StdDraw.show();
            }
        }

        Square searchBoundary = new Square(new Point(250, 250), 200);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.rectangle(250, 250, 200, 200);
        StdDraw.show();

        StdDraw.setPenColor(Color.GREEN);
        Point[] selectedPoints = root.queryRange(searchBoundary).getAll();
        System.out.println(selectedPoints.toString());
        for (Point p : selectedPoints) {
            StdDraw.filledCircle(p.getX(), p.getY(), 2);
            StdDraw.show();
        }

    }
}
