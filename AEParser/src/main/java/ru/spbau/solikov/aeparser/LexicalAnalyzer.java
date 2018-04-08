package ru.spbau.solikov.aeparser;

import java.io.IOException;
import java.io.InputStream;

public class LexicalAnalyzer {

    InputStream is;
    int curChar;
    int curPos;
    Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private void nextChar() throws ParseException {
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }

        if (curChar != -1) curPos++;
    }

    private boolean isBlank(int c) {
        return (c == ' ') || (c == '\r') || (c == '\n') || (c == '\t');
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }

        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                curToken.value = "(";
                curToken.length = 1;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                curToken.value = ")";
                curToken.length = 1;
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                curToken.value = "+";
                curToken.length = 1;
                break;
            case '-':
                nextChar();
                curToken = Token.MINUS;
                curToken.value = "-";
                curToken.length = 1;
                break;
            case '*':
                nextChar();
                curToken = Token.MULT;
                curToken.value = "*";
                curToken.length = 1;
                break;
            case '^':
                nextChar();
                curToken = Token.POW;
                curToken.value = "^";
                curToken.length = 1;
                break;
            case '/':
                nextChar();
                curToken = Token.DIV;
                curToken.value = "/";
                curToken.length = 1;
                break;
            case '0':
                nextChar();
                curToken = Token.NUM;
                curToken.isNumber = true;
                curToken.length = 1;
                curToken.value = "0";
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                curToken = Token.NUM;
                curToken.isNumber = true;
                curToken.length = 1;
                curToken.value = String.valueOf((char) curChar);
                nextChar();
                StringBuilder sb = new StringBuilder(curToken.value);
                while (Character.isDigit(curChar)) {
                    curToken.length += 1;
                    sb.append((char) curChar);
                    nextChar();
                }
                curToken.value = sb.toString();
                break;

            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }

    public boolean isBinaryOperator(Token token) {
        return token == Token.MULT || token == Token.PLUS || token == Token.MINUS
                || token == Token.DIV || token == Token.POW;
    }
}
