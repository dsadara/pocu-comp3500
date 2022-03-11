package academy.pocu.comp3500.lab8;

import academy.pocu.comp3500.lab8.maze.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class MazeSolver {
    public static List<Point> findPath(final char[][] maze, final Point start) {
        Stack<Point> points = new Stack<>();
        ArrayList<Point> path = new ArrayList<>();

        Point sudoPoint1 = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point sudoPoint2 = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        points.push(sudoPoint1);
        points.push(sudoPoint2);
        points.push(start);

        findPathRecursive(maze, new Point(start.getX(), start.getY()), points, path);
        // remove sudopoints
        if (!path.isEmpty()) {
            path.remove(0);
            path.remove(0);
        }
        return path;
    }

    private static boolean findPathRecursive(final char[][] maze, final Point point, Stack<Point> points, ArrayList<Point> path) {
        // 벽에 막힌 경우
        if (maze[point.getY()][point.getX()] == 'x') {
            points.pop();
            return false;
        }
        // 출구를 찾은 경우
        if (maze[point.getY()][point.getX()] == 'E') {
            while (!points.empty()) {
                path.add(0, points.pop());
            }
            return true;
        }

        Point nextPoint;
        Point currPoint = points.pop();
        Point priorPoint = points.peek();
        points.push(currPoint);

        // go up
        nextPoint = new Point(point.getX(), point.getY() - 1);
        if (!(nextPoint.getX() == priorPoint.getX() && nextPoint.getY() == priorPoint.getY())) {
            points.push(nextPoint);
            boolean isExitfinded = findPathRecursive(maze, nextPoint, points, path);
            if (isExitfinded)
                return isExitfinded;

        }
        // go right
        nextPoint = new Point(point.getX() + 1, point.getY());
        if (!(nextPoint.getX() == priorPoint.getX() && nextPoint.getY() == priorPoint.getY())) {
            points.push(nextPoint);
            boolean isExitfinded = findPathRecursive(maze, nextPoint, points, path);
            if (isExitfinded)
                return isExitfinded;
        }
        // go down
        nextPoint = new Point(point.getX(), point.getY() + 1);
        if (!(nextPoint.getX() == priorPoint.getX() && nextPoint.getY() == priorPoint.getY())) {
            points.push(nextPoint);
            boolean isExitfinded = findPathRecursive(maze, nextPoint, points, path);
            if (isExitfinded)
                return isExitfinded;
        }
        // go left
        nextPoint = new Point(point.getX() - 1, point.getY());
        if (!(nextPoint.getX() == priorPoint.getX() && nextPoint.getY() == priorPoint.getY())) {
            points.push(nextPoint);
            boolean isExitfinded = findPathRecursive(maze, nextPoint, points, path);
            if (isExitfinded)
                return isExitfinded;
        }

        points.pop();
        return false;
    }
}