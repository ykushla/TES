package yk.tes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykushla on 23.05.2016.
 */
public class Segment {

    private String name;
    private Point startPoint;
    private Point endPoint;
    private String direction;
    private float velocity;

    private double length;

    private boolean isTerminal = false;
    private boolean isInitial = false;

    private List<Segment> prevSegmentList = new ArrayList<>();
    private List<Segment> nextSegmentList = new ArrayList<>();

    public Segment(String name, Point startPoint, Point endPoint, String direction, float velocity) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.direction = direction;
        this.velocity = velocity;

        length = Point.getDistanceBetweenPoints(startPoint, endPoint);
    }

    public String getName() {
        return name;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public String getDirection() {
        return direction;
    }

    public double getLength() {
        return length;
    }

    public List<Segment> getPrevSegmentList() {
        return prevSegmentList;
    }

    public List<Segment> getNextSegmentList() {
        return nextSegmentList;
    }

    public void setTerminal() {
        isTerminal = true;
    }

    public void setInitial() {
        isInitial = true;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public float getVelocity() {
        return velocity;
    }
}
