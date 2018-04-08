package ru.spbau.solikov.aeparser;

public enum Token {
    LPAREN, RPAREN, PLUS, MINUS, MULT, DIV, POW, NUM, END;

    boolean isNumber = false;
    int length = 0;
    String value = "";
}
