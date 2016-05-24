package yk.tes.process;

import yk.tes.objects.Car;
import yk.tes.objects.EntryParam;
import yk.tes.objects.EntryParams;
import yk.tes.objects.Scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykushla on 24.05.2016.
 */
class TrafficGenerator {

    private static final double MSEC_PROB_RATE = 1d / 60 / 1000;

    private Scheme scheme;
    private EntryParams params;
    private long timeout;

    TrafficGenerator(Scheme scheme, EntryParams params, long timeout) {
        this.scheme = scheme;
        this.params = params;
        this.timeout = timeout;
    }

    List<Car> generate() {
        List<Car> carList = null;

        for (EntryParam param : params) {
            double prob = param.getProb() * MSEC_PROB_RATE * timeout;
            double prob_rand = Math.random();
            if (prob > prob_rand) {
                Car car = new Car(param.getDirection(), scheme.getInitialSegments().get(param.getEntryName()));
                if (carList == null) {
                    carList = new ArrayList<>();
                }
                carList.add(car);
            }
        }

        return carList;
    }
}
