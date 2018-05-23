package ru.spbau.solikov.lparser;

import java.io.IOException;
import java.io.InputStream;

import ru.spbau.solikov.lparser.Token;
import ru.spbau.solikov.lparser.Token.T;

public class LexicalAnalyzer {

    private InputStream is;
    private int curChar;

    int getCurPos() {
        return curPos;
    }

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
//        if (curChar == -1 && curPos == 0 && curLine == 0) throw new ParseException("Empty input", 0);
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
                expect("if", 1, T.KW, pos, line);

                break;
            case ('t'):
                pos = curPos;
                line = curLine;
                nextChar();
                if (curChar == 'r') {
                    nextChar();
                    expect("true", 2, T.Literal, pos, line);
                } else if (curChar == 'h') {
                    nextChar();
                    expect("then", 2, T.KW, pos, line);
                } else {
                    if (isBlank(curChar) || curChar == -1) {
                        curToken = new Token();
                        curToken.kind = T.Ident;
                        curToken.value = "t";
                        curToken.isLong = true;
                        curToken.pos = pos;
                        curToken.line = line;
                        curToken.length = 1;
                    } else {
                        curToken = new Token();
                        curToken.kind = T.Ident;
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
                expect("else", 1, T.KW, pos, line);

                break;
            case 'w':
                pos = curPos;
                line = curLine;
                nextChar();
                if (curChar == 'r') {
                    nextChar();
                    expect("write", 2, T.KW, pos, line);
                } else if (curChar == 'h') {
                    nextChar();
                    expect("while", 2, T.KW, pos, line);
                } else {
                    if (isBlank(curChar) || curChar == -1) {
                        curToken = new Token();
                        curToken.kind = T.Ident;
                        curToken.value = "w";
                        curToken.isLong = true;
                        curToken.pos = pos;
                        curToken.line = line;
                    } else {
                        curToken = new Token();
                        curToken.kind = T.Ident;
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
                expect("def", 1, T.KW, pos, line);

                break;
            case 'r':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("read", 1, T.KW, pos, line);

                break;
            case 'f':
                pos = curPos;
                line = curLine;
                nextChar();
                expect("false", 1, T.Literal, pos, line);

                break;
            case '+':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "+";

                break;
            case '-':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "-";

                break;
            case '*':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "*";

                break;
            case '/':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "/";

                break;
            case '%':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "%";

                break;
            case '>':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == '=' || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                if (curChar == '=') {
                    nextChar();
//                    if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                        throw new ParseException("Invalid operator", curPos);
                    curToken = new Token();
                    curToken.kind = T.Op;
                    curToken.line = line;
                    curToken.pos = pos;
                    curToken.length = 2;
                    curToken.value = ">=";
                    break;
                }
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = ">";

                break;
            case '<':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == '=' || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                if (curChar == '=') {
                    nextChar();
//                    if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                        throw new ParseException("Invalid operator", curPos);
                    curToken = new Token();
                    curToken.kind = T.Op;
                    curToken.line = line;
                    curToken.pos = pos;
                    curToken.length = 2;
                    curToken.value = "<=";
                    break;
                }
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "<";

                break;
            case '=':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(isBlank(curChar) || curChar == '=' || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                if (curChar == '=') {
                    nextChar();
//                    if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                        throw new ParseException("Invalid operator", curPos);
                    curToken = new Token();
                    curToken.kind = T.Op;
                    curToken.line = line;
                    curToken.pos = pos;
                    curToken.length = 2;
                    curToken.value = "==";
                    break;
                }
                curToken = new Token();
                curToken.kind = T.ASSIGN;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;
                curToken.value = "=";

                break;




                /*pos = curPos;
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

                break;*/
            case '&':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(curChar == '&')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "&&";

                break;
            case '|':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(curChar == '|')) throw new ParseException("Invalid operator", curPos);
                nextChar();
                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "||";

                break;
            case '!':
                pos = curPos;
                line = curLine;
                nextChar();
//                if (!(curChar == '=')) throw new ParseException("Invalid operator", curPos);
                nextChar();
//                if (!(isBlank(curChar) || curChar == -1 || Character.isLetterOrDigit(curChar)))
//                    throw new ParseException("Invalid operator", curPos);
                curToken = new Token();
                curToken.kind = T.Op;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 2;
                curToken.value = "!=";

                break;
            case '(':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.LPAREN;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case '{':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.LCURV;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case '}':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.RCURV;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ')':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.RPAREN;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ';':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.SEMICOLON;
                curToken.line = line;
                curToken.pos = pos;
                curToken.length = 1;

                break;
            case ',':
                pos = curPos;
                line = curLine;
                nextChar();
                curToken = new Token();
                curToken.kind = T.COMMA;
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
                curToken = new Token();
                curToken.kind = T.Literal;
                curToken.value = String.valueOf((char) curChar);
                curToken.line = curLine;
                curToken.pos = curPos;
                curToken.length = 1;
                nextChar();
                StringBuilder sb = new StringBuilder(curToken.value);
                StringBuilder sb_copy = new StringBuilder(curToken.value);
                boolean flag = false;
                while (!(isBlank(curChar) || curChar == -1 || isDelimiter((char) curChar) || isOperator((char) curChar))) {
                    curToken.length += 1;
                    sb.append((char) curChar);
                    sb_copy.append((char) curChar);
                    if ('e' == (char) curChar) {
                        flag = true;
                    }
                    nextChar();
                    if (flag && '+' == (char) curChar) {
                        sb_copy.append('+');
                        nextChar();
                    }
                }
                curToken.value = sb_copy.toString();
                try {
                    curToken.number = Double.parseDouble(sb.toString());
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
            case 'k':
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
                curToken = new Token();
                curToken.kind = T.Ident;
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
                curToken = new Token();
                curToken.kind = T.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    private void expect(String s, int index, T to, int pos, int line) throws ParseException {
        int i = index;
        curToken = new Token();
        curToken.kind = T.Ident;
        curToken.length = index + 1;
        curToken.line = line;
        curToken.pos = pos;
        curToken.isLong = true;
        curToken.value = s.substring(0, index);
        while (i < s.length() - 1) {
            if (curChar == s.charAt(i)) {
                char prev = (char) curChar;
                nextChar();
                if (isBlank(curChar) || curChar == -1) {
                    curToken = new Token();
                    curToken.kind = T.Ident;
                    curToken.length = i + 1;
                    curToken.isLong = true;
                    curToken.value = s.substring(0, i + 1);
                    return;
                }
                curToken.value += prev;
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
        if (isBlank(curChar) || curChar == -1 || (to == T.KW && (char) curChar == '(')) {
            curToken.kind = match ? to : T.Ident;
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
