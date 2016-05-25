package yk.tes.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import yk.tes.objects.Car;
import yk.tes.objects.Point;
import yk.tes.objects.Scheme;
import yk.tes.objects.Segment;
import yk.tes.process.Engine;
import yk.tes.process.IEngineCycleHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ykushla on 24.05.2016.
 */
public class SchemeRenderer implements IEngineCycleHandler {

    private GraphicsContext gc1;
    private GraphicsContext gc2;

    private double width;
    private double height;
    private double zeroX;
    private double zeroY;
    private double scaleInReal;
    private double scaleOnCanvas;

    private boolean firstTime = true;

    public SchemeRenderer(GraphicsContext gc1, GraphicsContext gc2, Scheme scheme) {
        this.gc1 = gc1;
        this.gc2 = gc2;
        width = Math.max(gc1.getCanvas().getWidth(), gc2.getCanvas().getWidth());
        height = Math.max(gc1.getCanvas().getHeight(), gc2.getCanvas().getHeight());

        List<Point> boundaryPoints = scheme.getBoundaryPoints();
        float minX = Math.min(Math.min(boundaryPoints.get(0).getX(), boundaryPoints.get(1).getX()),
            Math.min(boundaryPoints.get(2).getX(), boundaryPoints.get(3).getX()));
        float minY = Math.min(Math.min(boundaryPoints.get(0).getY(), boundaryPoints.get(1).getY()),
                Math.min(boundaryPoints.get(2).getY(), boundaryPoints.get(3).getY()));
        float maxX = Math.max(Math.max(boundaryPoints.get(0).getX(), boundaryPoints.get(1).getX()),
                Math.max(boundaryPoints.get(2).getX(), boundaryPoints.get(3).getX()));
        float maxY = Math.max(Math.max(boundaryPoints.get(0).getY(), boundaryPoints.get(1).getY()),
                Math.max(boundaryPoints.get(2).getY(), boundaryPoints.get(3).getY()));
        float schemeWidth = maxX - minX;
        float schemeHeight = maxY - minY;

        double scaleHor = schemeWidth / width;
        double scaleVer = schemeHeight / height;

        if (scaleHor > scaleVer) {
            scaleInReal = maxX - minX;
            scaleOnCanvas = width - 80;
        }
        else {
            scaleInReal = maxY - minY;
            scaleOnCanvas = height - 80;
        }

        zeroX = 40 - minX / scaleInReal * scaleOnCanvas;
        zeroY = 40 + maxY / scaleInReal * scaleOnCanvas;

    }

    private long convertX(double x) {
        return Math.round(x * scaleOnCanvas / scaleInReal + zeroX);
    }

    private long convertY(double y) {
        return Math.round(zeroY - y * scaleOnCanvas / scaleInReal);
    }

    public void engineCycleCall(Engine engine) {
        if (firstTime) {
            drawSceme(engine.getScheme());
            firstTime = false;
        }

        drawCars(engine.getCarList());
    }

    private void drawSceme(Scheme scheme) {
        gc1.setLineWidth(2);
        gc1.setStroke(new Color(0.5, 0.5, 0.5, 1));

        for (Segment segment : scheme.getSegmentList()) {
            gc1.moveTo(convertX(segment.getStartPoint().getX()), convertY(segment.getStartPoint().getY()));
            gc1.lineTo(convertX(segment.getEndPoint().getX()), convertY(segment.getEndPoint().getY()));
            gc1.stroke();
        }
    }

    private void drawCars(List<Car> carList) {
        gc2.clearRect(0, 0, width - 1, height - 1);
        if (carList.size() > 0) {
            gc2.setFill(new Color(1, 0, 0, 1));
            for (Car car : carList) {
                gc2.fillOval(convertX(car.getX()) - 5, convertY(car.getY()) - 5, 10, 10);
            }
        }
    }
}
