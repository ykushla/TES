package yk.tes.gui;/**
 * Created by ykushla on 24.05.2016.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yk.data.ExcelReader;
import yk.data.Frame;
import yk.tes.objects.EntryParams;
import yk.tes.objects.Scheme;
import yk.tes.process.Engine;

import java.util.Arrays;
import java.util.Map;

public class MainApp extends Application {

    private static final int TIMEOUT = 100;

    private static final int WIN_WIDTH = 600;
    private static final int WIN_HEIGHT = 600;

    private static final String FILE_NAME = "C:\\PERSONAL\\projects\\TES\\test.xlsx";

    private Scheme scheme;
    private EntryParams params;

    private Thread engineThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TES");
        Canvas c1 = new Canvas(WIN_WIDTH, WIN_HEIGHT);
        Canvas c2 = new Canvas(WIN_WIDTH, WIN_HEIGHT);
        GraphicsContext gc1 = c1.getGraphicsContext2D();
        GraphicsContext gc2 = c2.getGraphicsContext2D();
        Pane root = new Pane(c1, c2);

        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        SchemeRenderer sr = new SchemeRenderer(gc1, gc2, WIN_WIDTH, WIN_HEIGHT, WIN_WIDTH / 2, 500, 700, 450);

        initObjects();

        Engine engine = createEngine();
        engine.registerHandler(sr);

        engineThread = new Thread(engine);
        engineThread.start();
    }

    private void initObjects() {
        try {
            String sheetNames[] = {"data", "entry_params"};
            Map<String, Frame> frames = ExcelReader.readFromFile(FILE_NAME, Arrays.asList(sheetNames));

            Frame dataFrame = frames.get("data");
            Frame entryParamsFrame = frames.get("entry_params");

            // print to console
            dataFrame.printToConsole();
            entryParamsFrame.printToConsole();

            scheme = new Scheme(dataFrame);
            params = new EntryParams(entryParamsFrame);

            // validation
            params.validateWithScheme(scheme);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private Engine createEngine() {
        Engine engine = new Engine(scheme, params, TIMEOUT);
        return engine;
    }
}
