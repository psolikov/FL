package ru.spbau.solikov.aeparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        FileInputStream fileInputStream = new FileInputStream(args[0]);
        Parser parser = new Parser();
        parser.parse(fileInputStream).print();
    }
}
