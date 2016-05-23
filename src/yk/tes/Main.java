package yk.tes;

import yk.data.ExcelReader;
import yk.data.Frame;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Frame frame = ExcelReader.readFromFile("C:\\PERSONAL\\projects\\TES\\test.xlsx", "data");
            frame.printToConsole();

            Scheme scheme = new Scheme(frame);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
