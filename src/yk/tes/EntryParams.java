package yk.tes;

import yk.data.Frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ykushla on 24.05.2016.
 */
public class EntryParams {

    private static final String ENTRANCE = "Entrance";
    private static final String DIRECTION = "Direction";
    private static final String PROB = "Prob/min";

    private List<EntryParam> entryParamList = new ArrayList<>();

    public EntryParams(Frame frame) throws Exception {
        for (Map<String, String> item : frame.getItems()) {
            String entryName = item.get(ENTRANCE);
            if (entryName.isEmpty()) {
                throw new Exception("Entry param entrance name missing!");
            }

            String direction = item.get(DIRECTION);
            if (direction.isEmpty()) {
                throw new Exception("Entry param direction value missing!");
            }

            float prob = Float.parseFloat(item.get(PROB));

            entryParamList.add(new EntryParam(entryName, direction, prob));
        }
    }

    public List<EntryParam> getEntryParamList() {
        return entryParamList;
    }

    public void validateWithScheme(Scheme scheme) throws Exception {
        for (EntryParam param : entryParamList) {
            if (!scheme.getInitialSegments().containsKey(param.getEntryName())) {
                throw new Exception(String.format("Segment \"%s\" is not found in the scheme!",
                        param.getEntryName()));
            }
            if (!scheme.getDirections().contains(param.getDirection())) {
                throw new Exception(String.format("Direction \"%s\" is not found in the scheme!",
                        param.getDirection()));
            }
        }
    }
}
