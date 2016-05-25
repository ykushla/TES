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

//    private Pane root;
    private GraphicsContext gc1;
    private GraphicsContext gc2;

//    private WritableImage image;

    private int width;
    private int height;
    private int zeroX;
    private int zeroY;
    private float scaleInReal;
    private int scaleOnCanvas;

    private boolean firstTime = true;

    public SchemeRenderer(GraphicsContext gc1, GraphicsContext gc2,
                          int width, int height, int zeroX, int zeroY, float scaleInReal, int scaleOnCanvas) {
//        this.root = root;
        this.gc1 = gc1;
        this.gc2 = gc2;
        this.width = width;
        this.height = height;
        this.zeroX = zeroX;
        this.zeroY = zeroY;
        this.scaleInReal = scaleInReal;
        this.scaleOnCanvas = scaleOnCanvas;
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
        gc2.clearRect(0, 0, width, height);
        if (carList.size() > 0) {
            gc2.setFill(new Color(1, 0, 0, 1));
            for (Car car : carList) {
                gc2.fillOval(convertX(car.getX()) - 5, convertY(car.getY()) - 5, 10, 10);
            }
        }
    }
}
