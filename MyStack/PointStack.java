package MyStack;

public class PointStack {

    private Node head;
    private int sizeCounter = 0;

    public void push(Point p) {
        if(head == null) head = new Node(p, null);
        else head = new Node(p, head);

        sizeCounter++;
    }

    public void pushStack(PointStack other) {
        if(other.head == null) return;

        Node otherNode = other.head;
        for(int i = 0; i < other.size(); i++) {
            this.push(otherNode.getPoint());
            otherNode = otherNode.next();
        }
    }

    public Point[] getAll() {
        Point[] points = new Point[sizeCounter];
        if (head == null) return null;
        Node iNode = this.head;
        for(int i = 0; i < sizeCounter; i++) {
            points[i] = iNode.getPoint();
            iNode = iNode.next();
        }
        return points;
    }


    public int size() {
        return this.sizeCounter;
    }


}
