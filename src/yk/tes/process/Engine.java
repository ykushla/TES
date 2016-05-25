package yk.tes.process;

import yk.tes.objects.Car;
import yk.tes.objects.EntryParams;
import yk.tes.objects.Scheme;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ykushla on 24.05.2016.
 */
public class Engine implements Runnable {

    private long timeout;
    private Scheme scheme;
    private TrafficGenerator generator;
    private List<Car> carList = new LinkedList<>();

    private List<IEngineCycleHandler> handlerList = new ArrayList<>();

    public Engine(Scheme scheme, EntryParams params, long timeout) {
        this.scheme = scheme;
        this.timeout = timeout;
        generator = new TrafficGenerator(scheme, params, timeout);
    }

    private void moveCars() throws Exception {
        for (int i = 0; i < carList.size(); i++) {
            if (! carList.get(i).move(timeout)) {
                carList.remove(i--);
            }
        }
    }

    private void generateNewCars() {
        List<Car> newCarList = generator.generate();
        if ((newCarList != null) && (newCarList.size() > 0)) {
            carList.addAll(newCarList);
        }
    }

    private void logCars() {
        if (carList.size() > 0) {
            for (Car car : carList) {
                log(String.format("dirr: %s, segm: %s, pos: %s",
                        car.getDirection(),
                        car.getCurrentSegment(),
                        car.getPositionInSegment()));
            }
        }
        else {
            log("");
        }
    }

    public void run() {
        try {
            while (true) {
                moveCars();
                logCars();
                generateNewCars();

                callHandlers();

                // freeze thread for the defined milliseconds time
                Thread.sleep(timeout);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void log(String msg) {
        System.out.println(String.format("%tT: %s", LocalTime.now(), msg));
    }

    public Scheme getScheme() {
        return scheme;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void registerHandler(IEngineCycleHandler handler) {
        handlerList.add(handler);
    }

    private void callHandlers() {
        for (IEngineCycleHandler handler : handlerList) {
            handler.engineCycleCall(this);
        }
    }
}
