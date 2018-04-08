package ru.spbau.solikov.aeparser.test;

import org.junit.Test;
import ru.spbau.solikov.aeparser.ParseException;
import ru.spbau.solikov.aeparser.Parser;
import ru.spbau.solikov.aeparser.Tree;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test(expected = ParseException.class)
    public void testEmptyString() throws ParseException {
        String s = "";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test(expected = ParseException.class)
    public void testJustBrackets() throws ParseException {
        String s = "()";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test(expected = ParseException.class)
    public void testLetter() throws ParseException {
        String s = "a";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test
    public void testZero() {
        String s = "0";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        try {
            t = parser.parse(targetStream);
            assertEquals(t.getValue(), "0");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBigNumber() {
        String s = "12425729834792";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        try {
            t = parser.parse(targetStream);
            assertEquals(t.getValue(), "12425729834792");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ParseException.class)
    public void testNumberStartsWith0() throws ParseException {
        String s = "012324425729834792";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test(expected = ParseException.class)
    public void testNumberWithLetter() throws ParseException {
        String s = "12324t425729834792";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test
    public void testAddition() throws ParseException {
        String s = "1+2";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "+");
        assertEquals(t.getLeft().getValue(), "1");
        assertEquals(t.getRight().getValue(), "2");
    }

    @Test
    public void testMultiplicationWithSpaces() throws ParseException {
        String s = "14343 *    23232";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "*");
        assertEquals(t.getLeft().getValue(), "14343");
        assertEquals(t.getRight().getValue(), "23232");
    }

    @Test
    public void testCorrectExpression2() throws ParseException {
        String s = "(0 + 13) * ((42 - 7) / 0)";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "*");
        assertEquals(t.getLeft().getValue(), "+");
        assertEquals(t.getRight().getValue(), "/");
        assertEquals(t.getRight().getLeft().getValue(), "-");
        assertEquals(t.getRight().getRight().getValue(), "0");
        assertEquals(t.getLeft().getLeft().getValue(), "0");
        assertEquals(t.getLeft().getRight().getValue(), "13");
        assertEquals(t.getRight().getLeft().getLeft().getValue(), "42");
        assertEquals(t.getRight().getLeft().getRight().getValue(), "7");
    }

    @Test(expected = ParseException.class)
    public void testCorrectExpressionWithoutOneBracket() throws ParseException {
        String s = "(0 + 13) * ((42 - 7) / 0";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
    }

    @Test
    public void testCorrectExpression1() throws ParseException {
        String s = "0 + 13 * 42 - 7 / 0";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "-");
        assertEquals(t.getLeft().getValue(), "+");
        assertEquals(t.getRight().getValue(), "/");
        assertEquals(t.getRight().getLeft().getValue(), "7");
        assertEquals(t.getRight().getRight().getValue(), "0");
        assertEquals(t.getLeft().getLeft().getValue(), "0");
        assertEquals(t.getLeft().getRight().getValue(), "*");
        assertEquals(t.getLeft().getRight().getLeft().getValue(), "13");
        assertEquals(t.getLeft().getRight().getRight().getValue(), "42");
    }

    @Test
    public void testCorrectExpression3() throws ParseException {
        String s = "(((((13)))))";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "13");
        assertEquals(t.getLeft(), null);
        assertEquals(t.getRight(), null);
    }

    @Test
    public void testCorrectExpression4() throws ParseException {
        String s = "(42 ^ (24 - 156) * 123)";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        Parser parser = new Parser();
        Tree t;
        t = parser.parse(targetStream);
        assertEquals(t.getValue(), "*");
        assertEquals(t.getLeft().getValue(), "^");
        assertEquals(t.getLeft().getLeft().getValue(), "42");
        assertEquals(t.getLeft().getRight().getValue(), "-");
        assertEquals(t.getLeft().getRight().getLeft().getValue(), "24");
        assertEquals(t.getLeft().getRight().getRight().getValue(), "156");
        assertEquals(t.getRight().getValue(), "123");
    }
}
