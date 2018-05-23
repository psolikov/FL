package ru.spbau.solikov.lparser.test;

import org.junit.Test;
import ru.spbau.solikov.lparser.LexicalAnalyzer;
import ru.spbau.solikov.lparser.ParseException;
import ru.spbau.solikov.lparser.Token.T;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class LLexerTest {

    @Test
    public void testKeyWords() throws ParseException {
        String s = "if then else while def read write";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "if");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "then");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "else");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "while");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "def");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "read");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
    }

    @Test
    public void testBadKeyWord() throws ParseException {
        String s = "them";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "them");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        lexicalAnalyzer.nextToken();
    }

    @Test
    public void testNumberActualyNumber() throws ParseException {
        String s = "123.321";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
        assertEquals(Double.valueOf(lexicalAnalyzer.getCurToken().number), Double.valueOf(123.321));
    }

    @Test(expected = ParseException.class)
    public void testBadNumber() throws ParseException {
        String s = "123.321.321";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
    }

    @Test
    public void testOperators() throws ParseException {
        String s = "+ - * / % == != > >= < <= && ||";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "+");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "-");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "*");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "/");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "%");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "!=");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, ">");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, ">=");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "<");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "<=");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "&&");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "||");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
    }

    @Test
    public void testBadOperator() throws ParseException {
        String s = "++";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "+");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "+");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
    }

    @Test
    public void testSimpleProgram() throws ParseException {
        String s = "x==3";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
        assertEquals(Double.valueOf(lexicalAnalyzer.getCurToken().number), Double.valueOf(3));
    }

    @Test
    public void testExample() throws ParseException {
        String s = "read x; if y + 1 == x then write y else write x";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "read");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.SEMICOLON);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "if");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "y");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "+");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
        assertEquals(lexicalAnalyzer.getCurToken().value, "1");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "then");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "y");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "else");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
    }

    @Test
    public void testComplicatedVarName() throws ParseException {
        String s = "fwe___32fewq_";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "fwe___32fewq_");
    }

    @Test
    public void testOnly_VarName() throws ParseException {
        String s = "________";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "________");
    }

    @Test
    public void testEmpty() throws ParseException {
        String s = "";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
    }

    @Test
    public void testBoolean() throws ParseException {
        String s = "true false";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "true");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "false");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
    }


    @Test
    public void testBrackets() throws ParseException {
        String s = "(var)";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.LPAREN);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "var");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.RPAREN);
    }

    @Test
    public void testManyLines() throws ParseException {
        String s = "a\nb\nc";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().line, 0);
        assertEquals(lexicalAnalyzer.getCurToken().value, "a");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().line, 1);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().line, 2);
    }

    @Test
    public void testWhite() throws ParseException {
        String s = "white";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "white");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
    }

    @Test
    public void testNum() throws ParseException {
        String s = "-975.31e+2468";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "-");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "975.31e+2468");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Literal);
    }

    @Test
    public void testComment() throws ParseException {
        String s = "// comment";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "/");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "/");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "comment");
        assertEquals(lexicalAnalyzer.getCurToken().kind, T.Ident);
    }
}
