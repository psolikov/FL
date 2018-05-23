package ru.spbau.solikov.lparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainParser {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(args[0]);
        Parser parser = new Parser();
        try {
            parser.parse(fileInputStream).print();
        } catch (ParseException e) {
            System.out.print("Error on position " + e.pos + " - " + e.message);
        }
    }
}
