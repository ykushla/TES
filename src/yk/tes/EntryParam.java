package yk.tes;

/**
 * Created by ykushla on 24.05.2016.
 */
public class EntryParam {

    private String entryName;
    private String direction;
    private float prob;

    public EntryParam(String entryName, String direction, float probability) {
        this.entryName = entryName;
        this.direction = direction;
        this.prob = probability;
    }

    public String getEntryName() {
        return entryName;
    }

    public String getDirection() {
        return direction;
    }

    public float getProb() {
        return prob;
    }
}
