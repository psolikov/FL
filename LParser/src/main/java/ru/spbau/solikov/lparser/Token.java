package ru.spbau.solikov.lparser;

public enum  Token {
    Ident,
    KW,
    Literal,
    Op,
    LPAREN, RPAREN, COMMA, SEMICOLON, END;

    boolean isLong = false;
    int length = 0;
    public int line;
    public int pos;
    public double number;
    public String value = "";
}
