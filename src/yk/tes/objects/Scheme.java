package yk.tes.objects;

import yk.data.Frame;

import java.util.*;

/**
 * Created by ykushla on 23.05.2016.
 */
public class Scheme {

    private static final String NAME = "Name";
    private static final String X1 = "X1";
    private static final String Y1 = "Y1";
    private static final String X2 = "X2";
    private static final String Y2 = "Y2";
    private static final String DIRECTION = "Direction";
    private static final String VELOCITY = "Velocity";

    private List<Segment> segmentList = new ArrayList<>();
    private Map<String, Segment> initialSegments = new HashMap<>();
    private List<Segment> terminalSegmentList = new ArrayList<>();
    private Map<Point, List<Segment>> segmentListByStartPoint = new HashMap<>();
    private Map<Point, List<Segment>> segmentListByEndPoint = new HashMap<>();
    private Set<String> directions = new HashSet<>();

    public Scheme(Frame frame) throws Exception {
        // builds the scheme object from the frame dataset
        for (Map<String, String> item : frame.getItems()) {
            String name = item.get(NAME);
            if (name.isEmpty()) {
                name = null;
            }

            float x1 = Float.parseFloat(item.get(X1));
            float y1 = Float.parseFloat(item.get(Y1));
            float x2 = Float.parseFloat(item.get(X2));
            float y2 = Float.parseFloat(item.get(Y2));
            float velocity = Float.parseFloat(item.get(VELOCITY));

            String direction = item.get(DIRECTION);

            // adds the direction subset to the general direction set
            if (!direction.isEmpty()) {
                directions.addAll(Arrays.asList(direction.split(",")));
            }
            else {
                direction = null;
            }

            Point startPoint = new Point(x1, y1);
            Point endPoint = new Point(x2, y2);
            Segment segment = new Segment(name, startPoint, endPoint, direction, velocity);
            segmentList.add(segment);

            // fills in auxiliary dictionaries

            List<Segment> localSegmentList = segmentListByStartPoint.get(startPoint);
            if (localSegmentList == null) {
                localSegmentList = new ArrayList<>();
                segmentListByStartPoint.put(startPoint, localSegmentList);
            }
            localSegmentList.add(segment);

            localSegmentList = segmentListByEndPoint.get(endPoint);
            if (localSegmentList == null) {
                localSegmentList = new ArrayList<>();
                segmentListByEndPoint.put(endPoint, localSegmentList);
            }
            localSegmentList.add(segment);
        }

        // links segments between each other, check on the directions definitions
        for (Segment segment : segmentList) {
            List<Segment> localSegmentList = segmentListByStartPoint.get(segment.getEndPoint());
            if (localSegmentList == null) {
                segment.setTerminal();
                terminalSegmentList.add(segment);
            }
            else {
                for (Segment segment2 : localSegmentList) {

                    // check if all segments on crossings have a defined direction
                    if ((localSegmentList.size() > 1) && (segment2.getDirection() == null)) {
                        throw new Exception(String.format("Segment (name: \"%s\"; points: %f, %f, %f, %f) must have a direction definition!",
                                segment2.getName(),
                                segment2.getStartPoint().getX(), segment2.getStartPoint().getY(),
                                segment2.getEndPoint().getX(), segment2.getEndPoint().getY()));
                    }

                    segment.getNextSegmentList().add(segment2);
                    segment2.getPrevSegmentList().add(segment);
                }
            }

            localSegmentList = segmentListByEndPoint.get(segment.getStartPoint());
            if (localSegmentList == null) {
                if (segment.getName() == null) {
                    throw new Exception(String.format("Initial segment (points: %f, %f, %f, %f) must have a name definition!",
                            segment.getStartPoint().getX(), segment.getStartPoint().getY(),
                            segment.getEndPoint().getX(), segment.getEndPoint().getY()));
                }
                segment.setInitial();
                initialSegments.put(segment.getName(), segment);
            }
        }
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public Map<String, Segment> getInitialSegments() {
        return initialSegments;
    }

    public List<Segment> getTerminalSegmentList() {
        return terminalSegmentList;
    }

    public Set<String> getDirections() {
        return directions;
    }

    public List<Point> getBoundaryPoints() {
//        if (segmentList.size() == 0) return null;

        float minX = 0, maxX = 0, minY = 0, maxY = 0;

        for (Segment segment : segmentList) {
            minX = Math.min(minX, segment.getStartPoint().getX());
            minX = Math.min(minX, segment.getEndPoint().getX());
            minY = Math.min(minY, segment.getStartPoint().getY());
            minY = Math.min(minY, segment.getEndPoint().getY());

            maxX = Math.max(maxX, segment.getStartPoint().getX());
            maxX = Math.max(maxX, segment.getEndPoint().getX());
            maxY = Math.max(maxY, segment.getStartPoint().getY());
            maxY = Math.max(maxY, segment.getEndPoint().getY());
        }

        List<Point> result = new ArrayList<>(4);
        result.add(new Point(minX, minY));
        result.add(new Point(maxX, minY));
        result.add(new Point(minX, maxY));
        result.add(new Point(maxX, maxY));
        return result;
    }
}
