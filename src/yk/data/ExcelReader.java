package yk.data;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * Created by ykushla on 23.05.2016.
 */
public class ExcelReader {

    public static List<Frame> readFromFile(String fileName, List<String> sheetNameList)
            throws IOException, InvalidFormatException {

        InputStream inputFile = new FileInputStream(fileName);
        Workbook wb = WorkbookFactory.create(inputFile);

        List<Frame> frameList = new ArrayList<>();

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

                frameList.add(new Frame(names, items));
            }
        }

        return frameList;
    }

    public static Frame readFromFile(String fileName, String sheetName)
            throws IOException, InvalidFormatException {

        List<String> sheetNameList = new ArrayList<>();
        sheetNameList.add(sheetName);
        List<Frame> frameList = readFromFile(fileName, sheetNameList);
        if (frameList.size() > 0) {
            return frameList.get(0);
        }
        else {
            return null;
        }
    }
}