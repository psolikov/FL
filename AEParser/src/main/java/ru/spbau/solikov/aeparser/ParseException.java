package ru.spbau.solikov.aeparser;

public class ParseException extends Exception {

    String message = "";
    int pos = 0;

    public ParseException(String message, int curPos) {
        this.message = message;
        pos = curPos;
    }
}
