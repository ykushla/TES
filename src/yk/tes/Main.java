package yk.tes;

import yk.data.ExcelReader;
import yk.data.Frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            String sheetNames[] = {"data", "entry_params"};
            Map<String, Frame> frames = ExcelReader.readFromFile("C:\\PERSONAL\\projects\\TES\\test.xlsx",
                    Arrays.asList(sheetNames));

            Frame dataFrame = frames.get("data");
            Frame entryParamsFrame = frames.get("entry_params");

            dataFrame.printToConsole();
            entryParamsFrame.printToConsole();

            Scheme scheme = new Scheme(dataFrame);
            EntryParams params = new EntryParams(entryParamsFrame);
            params.validateWithScheme(scheme);


        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
