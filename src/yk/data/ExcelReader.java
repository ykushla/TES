package yk.data;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

/**
 * Created by ykushla on 23.05.2016.
 */
public class ExcelReader {

    public static Map<String, Frame> readFromFile(String fileName, List<String> sheetNameList)
            throws IOException, InvalidFormatException {

        InputStream inputFile = new FileInputStream(fileName);
        try {
            Workbook wb = WorkbookFactory.create(inputFile);

            Map<String, Frame> frames = new HashMap<>();

            for (String sheetName : sheetNameList) {
                Sheet sheet = wb.getSheet(sheetName);
                if (sheet != null) {
                    List<String> names = new ArrayList<>();

                    Iterator<Row> rows = sheet.rowIterator();
                    if (rows.hasNext()) {
                        Row row = rows.next();
                        for (Cell cell : row) {
                            names.add(cell.toString());
                        }
                    }

                    List<Map<String, String>> items = new ArrayList<>();

                    while (rows.hasNext()) {
                        Row row = rows.next();

                        Map<String, String> rowValues = new HashMap<>();

                        for (int i = 0; i < names.size(); i++) {
                            Cell cell = row.getCell(i);
                            if (cell == null) {
                                rowValues.put(names.get(i), "");
                            } else {
                                rowValues.put(names.get(i), cell.toString());
                            }
                        }

                        items.add(rowValues);
                    }

                    frames.put(sheetName, new Frame(names, items));
                }
            }

            return frames;
        }
        finally {
            inputFile.close();
        }
    }

    @Nullable
    public static Frame readFromFile(String fileName, String sheetName)
            throws IOException, InvalidFormatException {

        List<String> sheetNameList = new ArrayList<>();
        sheetNameList.add(sheetName);
        Map<String, Frame> frameList = readFromFile(fileName, sheetNameList);
        if (frameList.size() > 0) {
            return (Frame)frameList.values().toArray()[0];
        }
        else {
            return null;
        }
    }
}