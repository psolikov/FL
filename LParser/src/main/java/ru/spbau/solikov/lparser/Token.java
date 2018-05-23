package ru.spbau.solikov.lparser;

/*public enum  Token {
    Ident,
    KW,
    Literal,
    Op,
    LPAREN, RPAREN, COMMA, SEMICOLON, END, LCURV, RCURV, ASSIGN;

    boolean isLong = false;
    int length = 0;
    public int line;
    public int pos;
    public double number;
    public String value = "";
}*/

public class Token {
    public enum T{
        Ident,
        KW,
        Literal,
        Op,
        LPAREN, RPAREN, COMMA, SEMICOLON, END, LCURV, RCURV, ASSIGN;
    }

    public T kind;

    boolean isLong = false;
    int length = 0;
    public int line;
    public int pos;
    public double number;
    public String value = "";
}
