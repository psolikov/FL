package ru.spbau.solikov.lparser;

import java.io.IOException;
import java.io.InputStream;

public class LexicalAnalyzer {

    private InputStream is;
    private int curChar;
    private int curPos;
    private int curLine;

    public Token getCurToken() {
        return curToken;
    }

    Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        curLine = 0;
        nextChar();
        if (curChar == -1 && curPos == 0 && curLine == 0) throw new ParseException("Empty input", 0);
    }

    private void nextChar() throws ParseException {
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }

        if (curChar != -1) {
            if (curChar == '\n' || curChar == '\r') {
                curLine++;
                curPos = 0;
            } else {
                curPos++;
            }
        }
    }

    private boolean isBlank(int c) {
        return (c == ' ') || (c == '\r') || (c == '\n') || (c == '\t') || (c == '\f');
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }

        switch (curChar) {
            case ('i'):
                int pos = curPos;
                int line = curLine;
                nextChar();
                expect("if", 1, Token.KW, pos, line);

                break;
            case ('t'):
                pos = curPos;
                line = curLine;
                nextChar();
                if (curChar == 'r') {
                    nextChar();
                    expect("true", 2, Token.Literal, pos, line);
                } else if (curChar == 'h') {
                    nextChar();
                    expect("then", 2, Token.KW, pos, line);
                } else {
                    if (isBlank(curChar) || curChar == -1) {
                        curToken = Token.Ident;
                        curToken.value = "t";
                        curToken.isLong = true;
                        curToken.pos = pos;
                        curToken.line = line;
                        curToken.length = 1;
                    } else {
                        curToken = Token.Ident;
                        curToken.isLong = true;
                        curToken.value = "t";
                        curToken.pos = pos;
                        curToken.line = line;
                        curToken.length = 1;
                        StringBuilder sb = new StringBuilder(curToken.value);
                        while (Character.isLetterOrDigit(curChar) || curChar == '_') {
                            curToken.length += 1;
                            sb.append((char) curChar);
                            nextChar();
                        }
                        curToken.value = sb.toString();
                    }
                }

                break;
            case 'e':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("else", 1, Token.KW, pos, line);

                break;
            case 'w':
                pos = curPos;
                line = curLine;
                nextChar();
                if (curChar == 'r') {
                    nextChar();
                    expect("write", 2, Token.KW, pos, line);
                } else if (curChar == 'h') {
                    nextChar();
                    expect("while", 2, Token.KW, pos, line);
                } else {
                    if (isBlank(curChar) || curChar == -1) {
                        curToken = Token.Ident;
                        curToken.value = "w";
                        curToken.isLong = true;
                        curToken.pos = pos;
                        curToken.line = line;
                    } else {
                        curToken = Token.Ident;
                        curToken.isLong = true;
                        curToken.value = "w";
                        curToken.line = line;
                        curToken.pos = pos;
                        StringBuilder sb = new StringBuilder(curToken.value);
                        while (Character.isLetterOrDigit(curChar) || curChar == '_') {
                            curToken.length += 1;
                            sb.append((char) curChar);
                            nextChar();
                        }
                        curToken.value = sb.toString();
                    }
                }

                break;
            case 'd':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("do", 1, Token.KW, pos, line);

                break;
            case 'r':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("read", 1, Token.KW, pos, line);

                break;
            case 'f':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("false", 1, Token.Literal, pos, line);

                break;
            case '+':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "+";

                break;
            case '-':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "-";

                break;
            case '*':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "*";

                break;
            case '/':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "/";

                break;
            case '%':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "%";

                break;
            case '>':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == '=' || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                if (curChar == '=') {
                    nextChar();
                    if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                        throw new ParseException("Invalid operator", curPos);
                    curToken = Token.Op;
                    curToken.line = line;
                    curToken.pos = pos;
                    curToken.length = 2;
                    curToken.value = ">=";
                    break;
                }
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = ">";

                break;
            case '<':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(isBlank(curChar) || curChar == '=' || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                if (curChar == '=') {
                    nextChar();
                    if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                        throw new ParseException("Invalid operator", curPos);
                    curToken = Token.Op;
                    curToken.line = line;
                    curToken.pos = pos;
                    curToken.length = 2;
                    curToken.value = "<=";
                    break;
                }
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "<";

                break;
            case '=':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(curChar == '=')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "==";

                break;
            case '&':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(curChar == '&')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "&&";

                break;
            case '|':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(curChar == '|')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "||";

                break;
            case '!':
                pos = curPos;
                line = curLine;
                nextChar();
                if (!(curChar == '=')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = Token.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "!=";

                break;
            case '(':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = Token.LPAREN;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ')':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = Token.RPAREN;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ';':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = Token.SEMICOLON;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ',':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = Token.COMMA;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

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
            case '0':
                curToken = Token.Literal;
                curToken.value = String.valueOf((char) curChar);
                curToken.line = curLine;
                curToken.pos = curPos;
                curToken.length = 1;
                nextChar();
                StringBuilder sb = new StringBuilder(curToken.value);
                while (!(isBlank(curChar) || curChar == -1 || isDelimiter((char) curChar) || isOperator((char) curChar))) {
                    curToken.length += 1;
                    sb.append((char) curChar);
                    nextChar();
                }
                curToken.value = sb.toString();
                try {
                    curToken.number = Double.parseDouble(curToken.value);
                } catch (NumberFormatException e) {
                    throw new ParseException("Bad number format", curPos);
                }

                break;

            case '_':
            case 'a':
            case 'b':
            case 'c':
            case 'g':
            case 'h':
            case 'j':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 's':
            case 'u':
            case 'v':
            case 'x':
            case 'y':
            case 'z':
                curToken = Token.Ident;
                curToken.length = 1;
                curToken.pos = curPos;
                curToken.line = curLine;
                curToken.value = String.valueOf((char) curChar);
                nextChar();
                StringBuilder sb2 = new StringBuilder(curToken.value);
                while (Character.isLetterOrDigit(curChar) || curChar == '_') {
                    curToken.length += 1;
                    sb2.append((char) curChar);
                    nextChar();
                }
                curToken.value = sb2.toString();
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    private void expect(String s, int index, Token to, int pos, int line) throws ParseException {
        int i = index;
        curToken = Token.Ident;
        curToken.length = index + 1;
        curToken.line = line;
        curToken.pos = pos;
        curToken.isLong = true;
        curToken.value = s.substring(0, index);
        while (i < s.length() - 1) {
            if (curChar == s.charAt(i)) {
                nextChar();
                if (isBlank(curChar) || curChar == -1) {
                    curToken = Token.Ident;
                    curToken.length = i + 1;
                    curToken.isLong = true;
                    curToken.value = s.substring(0, i + 1);
                    return;
                }
            } else {
                StringBuilder sb = new StringBuilder(curToken.value);
                while (Character.isLetterOrDigit(curChar) || curChar == '_') {
                    curToken.length += 1;
                    sb.append((char) curChar);
                    nextChar();
                }
                curToken.value = sb.toString();
                return;
            }
            curToken.length++;
            i++;
        }
        boolean match = false;
        if (curChar == s.charAt(s.length() - 1)) match = true;
        curToken.value = s.substring(0, i);
        curToken.value += (char) curChar;
        nextChar();
        if (isBlank(curChar) || curChar == -1) {
            curToken = match ? to : Token.Ident;
            curToken.pos = pos;
            curToken.line = line;
            curToken.length = s.length();
            curToken.isLong = true;
            if (match) curToken.value = s;
        } else {
            StringBuilder sb = new StringBuilder(curToken.value);
            while (Character.isLetterOrDigit(curChar) || curChar == '_') {
                curToken.length += 1;
                sb.append((char) curChar);
                nextChar();
            }
            curToken.value = sb.toString();
        }
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '&' || ch == '*' || ch == '%' ||
                ch == '=' || ch == '>' || ch == '<' || ch == '!';
    }

    private boolean isDelimiter(char ch) {
        return ch == '(' || ch == ')' || ch == ';' || ch == ',';
    }
}
