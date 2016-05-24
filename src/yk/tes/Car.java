package yk.tes;

/**
 * Created by ykushla on 24.05.2016.
 */
public class Car {

    private static final double RATE_1_3600 = 1d / 3600;
    private static final int RATE_3600 = 3600;

    private String direction;

    private Segment currentSegment;
    private double positionInSegment;

    public Car(String direction, Segment startingSegment) {
        this.direction = direction;
        currentSegment = startingSegment;
        positionInSegment = 0;
    }

    // returns false if went out of scheme
    public boolean move(double msecPassed) throws Exception {
        if (currentSegment == null) {
            return false;
        }

        while (msecPassed > 0) {
            double projectedDistance = currentSegment.getVelocity() * RATE_1_3600 * msecPassed;
            double remainingDistance = currentSegment.getLength() - positionInSegment;
            if (projectedDistance < remainingDistance) {
                positionInSegment += projectedDistance;
                msecPassed = 0;
//                return true
            }
            else {
                if (currentSegment.isTerminal()) {
                    currentSegment = null;
                    return false;
                }

                boolean nextSegmentFound = false;
                for (Segment nextSegment : currentSegment.getNextSegmentList()) {
                    if ((nextSegment.getDirection() == null) || nextSegment.getDirection().contains(direction)) {
                        double msecOnSegment = remainingDistance / currentSegment.getVelocity() * RATE_3600;
                        msecPassed -= msecOnSegment;
                        currentSegment = nextSegment;
                        positionInSegment = 0;
                        nextSegmentFound = true;
                        break;
                    }
                }
                if (! nextSegmentFound) {
                    throw new Exception("Needed segment by direction not found!");
                }
            }
        }

        return true;
    }

    public String getDirection() {
        return direction;
    }

    public Segment getCurrentSegment() {
        return currentSegment;
    }

    public double getPositionInSegment() {
        return positionInSegment;
    }
}
