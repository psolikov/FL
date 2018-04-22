package ru.spbau.solikov.lparser.test;

import org.junit.Test;
import ru.spbau.solikov.lparser.LexicalAnalyzer;
import ru.spbau.solikov.lparser.ParseException;
import ru.spbau.solikov.lparser.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class LParserTest {

    @Test
    public void testKeyWords() throws ParseException {
        String s = "if then else while do read write";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "if");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "then");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "else");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "while");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "do");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "read");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
    }

    @Test
    public void testBadKeyWord() throws ParseException {
        String s = "them";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "them");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        lexicalAnalyzer.nextToken();
    }

    @Test
    public void testNumberActualyNumber() throws ParseException {
        String s = "123.321";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Literal);
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
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "-");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "*");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "/");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "%");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "!=");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, ">");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, ">=");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "<");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "<=");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "&&");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "||");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
    }

    @Test(expected = ParseException.class)
    public void testBadOperator() throws ParseException {
        String s = "++";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
    }

    @Test(expected = ParseException.class)
    public void testBadOperator2() throws ParseException {
        String s = "====";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
    }

    @Test
    public void testSimpleProgram() throws ParseException {
        String s = "x==3";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Literal);
        assertEquals(Double.valueOf(lexicalAnalyzer.getCurToken().number), Double.valueOf(3));
    }

    @Test
    public void testExample() throws ParseException {
        String s = "read x; if y + 1 == x then write y else write x";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(targetStream);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "read");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.SEMICOLON);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "if");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "y");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "+");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Literal);
        assertEquals(lexicalAnalyzer.getCurToken().value, "1");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Op);
        assertEquals(lexicalAnalyzer.getCurToken().value, "==");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "x");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "then");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
        assertEquals(lexicalAnalyzer.getCurToken().value, "y");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "else");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.KW);
        assertEquals(lexicalAnalyzer.getCurToken().value, "write");
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Ident);
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

    @Test(expected = ParseException.class)
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
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Literal);
        lexicalAnalyzer.nextToken();
        assertEquals(lexicalAnalyzer.getCurToken().value, "false");
        assertEquals(lexicalAnalyzer.getCurToken(), Token.Literal);
    }
}
