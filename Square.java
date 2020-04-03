import MyStack.Point;

public class Square {
    private double halfLength;
    private Point center;

    public Square(Point center, double halfLength) {
        this.halfLength = halfLength;
        this.center = center;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(center.getX(), center.getY(), halfLength, halfLength);
    }

    public boolean contains(Point p) {
        if(center.getX() - halfLength < p.getX() && center.getX() + halfLength > p.getX() && center.getY() - halfLength < p.getY() && center.getY() + halfLength > p.getY())
            return true;
        return false;
    }

    public boolean intersects (Square other){
        double otherUpperLimit = other.center.getY() + other.getHalfLength();
        double otherLowerLimit = other.center.getY() - other.getHalfLength();
        double otherLeftLimit =  other.center.getX() - other.getHalfLength();
        double otherRightLimit =  other.center.getX() + other.getHalfLength();

        double thisUpperLimit = this.center.getY() + this.halfLength;
        double thisLowerLimit = this.center.getY() - this.halfLength;
        double thisLeftLimit = this.center.getX() - this.halfLength;
        double thisRightLimit = this.center.getX() + this.halfLength;

        boolean bool = !(otherLeftLimit > thisRightLimit ||
                otherRightLimit < thisLeftLimit ||
                otherLowerLimit > thisUpperLimit ||
                otherUpperLimit < thisLowerLimit
        );

        return bool;
    }

    public Point getCenter() {
        return this.center;
    }

    public double getHalfLength() {
        return this.halfLength;
    }


}