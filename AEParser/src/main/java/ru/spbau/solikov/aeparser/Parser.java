package ru.spbau.solikov.aeparser;

import java.io.InputStream;

public class Parser {

    private LexicalAnalyzer lex;

    private Tree Eparser() throws ParseException {
        Tree t = E();

        if (lex.curToken != Token.END) {
            throw new ParseException("Parse error", lex.curPos);
        }

        return t;
    }

    private Tree E() throws ParseException {
        Tree t = T();

        while (lex.curToken == Token.PLUS || lex.curToken == Token.MINUS){
            String op = lex.curToken.value;
            lex.nextToken();
            Tree t1 = T();
            t = new Tree(op, t, t1);
        }

        return t;
    }

    private Tree T() throws ParseException {
        Tree t = F();

        while (lex.curToken == Token.MULT || lex.curToken == Token.DIV){
            String op = lex.curToken.value;
            lex.nextToken();
            Tree t1 = F();
            t = new Tree(op, t, t1);
        }

        return t;
    }

    private Tree F() throws ParseException {
        Tree t = P();

        while (lex.curToken == Token.POW){
            String op = lex.curToken.value;
            lex.nextToken();
            Tree t1 = F();
            t = new Tree(op, t, t1);
        }

        return t;
    }

    private Tree P() throws ParseException {
        Tree t;

        switch (lex.curToken){
            case NUM:
                t = new Tree(lex.curToken.value);
                lex.nextToken();
                return t;
            case LPAREN:
                lex.nextToken();
                t = E();
                if (lex.curToken != Token.RPAREN) {
                    throw new ParseException("Parse error", lex.curPos);
                }
                lex.nextToken();
                return t;
            default:
                throw new ParseException("Parse error", lex.curPos);
        }
    }

    public Tree parse(InputStream is) throws ParseException{
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return Eparser();
    }
}
