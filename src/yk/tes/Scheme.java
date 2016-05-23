package yk.tes;

import com.sun.media.sound.SoftEnvelopeGenerator;
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

    private List<Segment> segmentList = new ArrayList<>();
    private List<Segment> initialSegmentList = new ArrayList<>();
    private List<Segment> terminalSegmentList = new ArrayList<>();
    private Map<String, List<Segment>> segmentListByStartPoint = new HashMap<>();
    private Map<String, List<Segment>> segmentListByEndPoint = new HashMap<>();
    private Set<String> directions = new HashSet<>();

    public Scheme(Frame frame) {
        for (Map<String, String> item : frame.getItems()) {
            String name = item.get(NAME);
            float x1 = Float.parseFloat(item.get(X1));
            float y1 = Float.parseFloat(item.get(Y1));
            float x2 = Float.parseFloat(item.get(X2));
            float y2 = Float.parseFloat(item.get(Y2));
            String direction = item.get(DIRECTION);

            if (!direction.isEmpty()) {
                directions.addAll(Arrays.asList(direction.split(",")));
            }

            Point startPoint = new Point(x1, y1);
            Point endPoint = new Point(x2, y2);
            Segment segment = new Segment(name, startPoint, endPoint, direction);
            segmentList.add(segment);

            List<Segment> localSegmentList = segmentListByStartPoint.get(startPoint.getHash());
            if (localSegmentList == null) {
                localSegmentList = new ArrayList<>();
                segmentListByStartPoint.put(startPoint.getHash(), localSegmentList);
            }
            localSegmentList.add(segment);

            localSegmentList = segmentListByEndPoint.get(endPoint.getHash());
            if (localSegmentList == null) {
                localSegmentList = new ArrayList<>();
                segmentListByEndPoint.put(endPoint.getHash(), localSegmentList);
            }
            localSegmentList.add(segment);
        }

        for (Segment segment : segmentList) {
            List<Segment> localSegmentList = segmentListByStartPoint.get(segment.getEndPoint().getHash());
            if (localSegmentList == null) {
                segment.setTerminal();
                terminalSegmentList.add(segment);
            }
            else {
                for (Segment segment2 : localSegmentList) {
                    segment.getNextSegmentList().add(segment2);
                    segment2.getPrevSegmentList().add(segment);
                }
            }

            localSegmentList = segmentListByEndPoint.get(segment.getStartPoint().getHash());
            if (localSegmentList == null) {
                segment.setInitial();
                initialSegmentList.add(segment);
            }
        }
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public List<Segment> getInitialSegmentList() {
        return initialSegmentList;
    }

    public List<Segment> getTerminalSegmentList() {
        return terminalSegmentList;
    }

    public Set<String> getDirections() {
        return directions;
    }
}
