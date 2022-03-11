package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

public class Node {
    private Point point;
    private Node parent;

    public Node(Point point, Node parent) {
        this.point = point;
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public Point getPoint() {
        return point;
    }

    public void setParent(Point parent) {
        parent = parent;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
