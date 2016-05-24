package yk.tes.process;

import yk.tes.Car;
import yk.tes.EntryParams;
import yk.tes.Scheme;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ykushla on 24.05.2016.
 */
interface Loggable {
    void log(String msg);
}

public class Engine implements Runnable, Loggable {

    private long timeout;
    private Scheme scheme;
//    private EntryParams params;

    private TrafficGenerator generator;

    private List<Car> carList = new LinkedList<>();

    public Engine(Scheme scheme, EntryParams params, long timeout) {
        this.scheme = scheme;
//        this.params = params;
        this.timeout = timeout;

        generator = new TrafficGenerator(scheme, params, timeout);
    }

    private void moveCars() throws Exception {
        for (int i = 0; i < carList.size(); i++) {
//        for (Car car : carList) {
            if (! carList.get(i).move(timeout)) {
                carList.remove(i--);
            }
        }
    }

    private void generateNewCars() {
        // TEMP IMPLEMENTATION

        if (carList.size() > 0) return;

        List<Car> newCarList = generator.generate();
        if ((newCarList != null) && (newCarList.size() > 0)) {
            carList.add(newCarList.get(0));
        }
    }

    public void run() {
        try {
            while (true) {
                moveCars();
                generateNewCars();

                // freeze thread for the defined milliseconds time
                Thread.sleep(timeout);

                if (carList.size() > 0) {
                    log(String.format("dirr: %s, segm: %s, pos: %s",
                            carList.get(0).getDirection(),
                            carList.get(0).getCurrentSegment(),
                            carList.get(0).getPositionInSegment()));
                }
                else {
                    log("");
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void log(String msg) {
        System.out.println(String.format("%tT: %s", LocalTime.now(), msg));
    }
}
