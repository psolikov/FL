package ru.spbau.solikov.lparser;

public enum  Token {
    Ident,
    KW,
    Literal,
    Op,
    LPAREN, RPAREN, COMMA, SEMICOLON, END;

    boolean isLong = false;
    int length = 0;
    int line;
    int pos;
    public double number;
    public String value = "";
}
