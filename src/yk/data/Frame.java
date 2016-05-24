package yk.data;

import java.util.List;
import java.util.Map;

/**
 * Created by ykushla on 23.05.2016.
 */
public class Frame {

    private List<String> names;
    private List<Map<String, String>> items;

    public Frame(List<String> names, List<Map<String, String>> items) {
        this.names = names;
        this.items = items;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void printToConsole() {
        for (String name : names) {
            System.out.print(name + "\t");
        }
        System.out.println();

        for (Map<String, String> item : items) {
            for (String name : names) {
                String value = item.get(name);
                if (value.isEmpty()) {
                    value = "-";
                }
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
}
