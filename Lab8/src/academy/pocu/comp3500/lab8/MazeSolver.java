package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public final class MazeSolver {
    public static List<Point> findPath(final char[][] maze, final Point start) {
        Queue<Node> queue = new LinkedList<>();
        ArrayList<Point> path = new ArrayList<>();

        Node sudoParent = new Node(null, null);
        queue.add(new Node(start, sudoParent));

        while (!queue.isEmpty()) {
            Node next = queue.remove();

            // visit
            if (maze[next.getPoint().getY()][next.getPoint().getX()] == 'E') {
                while (next.getPoint() != null) {
                    path.add(0, next.getPoint());
                    next = next.getParent();
                }
                return path;
            }


            // add child
            Point child = new Point(next.getPoint().getX(), next.getPoint().getY() - 1);
            Node node = new Node(child, next);
            if ((0 <= child.getX() && child.getX() < maze[0].length) && (0 <= child.getY() && child.getY() < maze.length)) {
                if (maze[child.getY()][child.getX()] != 'x') {
                    queue.add(node);
                }
            }
            child = new Point(next.getPoint().getX() + 1, next.getPoint().getY());
            node = new Node(child, next);
            if ((0 <= child.getX() && child.getX() < maze[0].length) && (0 <= child.getY() && child.getY() < maze.length)) {
                if (maze[child.getY()][child.getX()] != 'x') {
                    queue.add(node);
                }
            }
            child = new Point(next.getPoint().getX(), next.getPoint().getY() + 1);
            node = new Node(child, next);
            if ((0 <= child.getX() && child.getX() < maze[0].length) && (0 <= child.getY() && child.getY() < maze.length)) {
                if (maze[child.getY()][child.getX()] != 'x') {
                    queue.add(node);
                }
            }
            child = new Point(next.getPoint().getX() - 1, next.getPoint().getY());
            node = new Node(child, next);
            if ((0 <= child.getX() && child.getX() < maze[0].length) && (0 <= child.getY() && child.getY() < maze.length)) {
                if (maze[child.getY()][child.getX()] != 'x') {
                    queue.add(node);
                }
            }
            maze[next.getPoint().getY()][next.getPoint().getX()] = 'x';
        }
        return path;
    }

}