package yk.tes;

/**
 * Created by ykushla on 23.05.2016.
 */
public class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistanceBetweenPoints(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    String getHash() {
        return Float.toString(x) + " " + Float.toString(y);
    }
}
