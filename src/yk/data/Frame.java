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
            System.out.print(name + " ");
        }
        System.out.println();

        for (Map<String, String> item : items) {
            for (int i = 0; i < names.size(); i++) {
                String value = item.get(names.get(i));
                if (value.isEmpty()) {
                    value = "-";
                }
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
