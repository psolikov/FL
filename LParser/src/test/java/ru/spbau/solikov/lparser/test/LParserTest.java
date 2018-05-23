package ru.spbau.solikov.lparser.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.solikov.lparser.ParseException;
import ru.spbau.solikov.lparser.Parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;    

public class LParserTest {

    private Parser parser;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        parser = new Parser();
    }

    @Test(expected = ParseException.class)
    public void testException() throws ParseException {
        String s = "if then else while def read write";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream);
    }

    @Test
    public void testAssign() throws ParseException {
        String s = "x = 43;";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Assign:\n" +
                "        ├── x\n" +
                "        └── 43\n");
    }

    @Test
    public void testEmpty() throws ParseException {
        String s = "";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n");
    }

    @Test
    public void testDefSimple() throws ParseException {
        String s = "def kek(){}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Definitions\n" +
                "        └── kek\n");
    }

    @Test
    public void testDefArg() throws ParseException {
        String s = "def kek(x){}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Definitions\n" +
                "        └── kek\n" +
                "            └── Arg : x\n");
    }

    @Test
    public void testDefManyArgAndBody() throws ParseException {
        String s = "def kek(x,r,sdv, fwef32e){x = 1; kek(ld);}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Definitions\n" +
                "        └── kek\n" +
                "            ├── Arg : x\n" +
                "            ├── Arg : r\n" +
                "            ├── Arg : sdv\n" +
                "            ├── Arg : fwef32e\n" +
                "            ├── Assign:\n" +
                "            │   ├── x\n" +
                "            │   └── 1\n" +
                "            └── Call : kek\n" +
                "                └── ld\n");
    }

    @Test
    public void testManyDefManyArgAndBody() throws ParseException {
        String s = "def kek(x,r,sdv, fwef32e){x = 1; kek(ld);} " +
                "def kek(x,r,sdv, fwef32e){x = 1; kek(ld);}" +
                "def kek(x,r,sdv, fwef32e){x = 1; kek(ld);}" +
                "def kek(x,r,sdv, fwef32e){x = 1; kek(ld);}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Definitions\n" +
                "        ├── kek\n" +
                "        │   ├── Arg : x\n" +
                "        │   ├── Arg : r\n" +
                "        │   ├── Arg : sdv\n" +
                "        │   ├── Arg : fwef32e\n" +
                "        │   ├── Assign:\n" +
                "        │   │   ├── x\n" +
                "        │   │   └── 1\n" +
                "        │   └── Call : kek\n" +
                "        │       └── ld\n" +
                "        ├── kek\n" +
                "        │   ├── Arg : x\n" +
                "        │   ├── Arg : r\n" +
                "        │   ├── Arg : sdv\n" +
                "        │   ├── Arg : fwef32e\n" +
                "        │   ├── Assign:\n" +
                "        │   │   ├── x\n" +
                "        │   │   └── 1\n" +
                "        │   └── Call : kek\n" +
                "        │       └── ld\n" +
                "        ├── kek\n" +
                "        │   ├── Arg : x\n" +
                "        │   ├── Arg : r\n" +
                "        │   ├── Arg : sdv\n" +
                "        │   ├── Arg : fwef32e\n" +
                "        │   ├── Assign:\n" +
                "        │   │   ├── x\n" +
                "        │   │   └── 1\n" +
                "        │   └── Call : kek\n" +
                "        │       └── ld\n" +
                "        └── kek\n" +
                "            ├── Arg : x\n" +
                "            ├── Arg : r\n" +
                "            ├── Arg : sdv\n" +
                "            ├── Arg : fwef32e\n" +
                "            ├── Assign:\n" +
                "            │   ├── x\n" +
                "            │   └── 1\n" +
                "            └── Call : kek\n" +
                "                └── ld\n");
    }

    @Test
    public void testCall() throws ParseException {
        String s = "function(arg1, arg2, agr3);";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── Call : function\n" +
                "        ├── arg1\n" +
                "        ├── arg2\n" +
                "        └── agr3\n");
    }

    @Test(expected = ParseException.class)
    public void testCallBad() throws ParseException {
        String s = "function(arg1, arg2, agr3,);";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testCallBad2() throws ParseException {
        String s = "function(arg1, arg2, agr3;";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testCallBad3() throws ParseException {
        String s = "function(arg1";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testCallBad4() throws ParseException {
        String s = "function(arg1));";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testIfNoCondition() throws ParseException {
        String s = "if(){}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test
    public void testIf() throws ParseException {
        String s = "if(cond){x = 43;}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── if\n" +
                "        ├── condition\n" +
                "        │   └── cond\n" +
                "        └── Assign:\n" +
                "            ├── x\n" +
                "            └── 43\n");

    }

    @Test(expected = ParseException.class)
    public void testIfBad2() throws ParseException {
        String s = "if(kek){";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testReadNoArg() throws ParseException {
        String s = "read();";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testReadBad() throws ParseException {
        String s = "read;";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test(expected = ParseException.class)
    public void testReadExpr() throws ParseException {
        String s = "read(1+1)";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test
    public void testRead() throws ParseException {
        String s = "read(var);";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── read: var\n");
    }

    @Test(expected = ParseException.class)
    public void testWriteNoArg() throws ParseException {
        String s = "write();";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
    }

    @Test
    public void testWrite() throws ParseException {
        String s = "write(123 + 321 * 3e432 / 10.0 || true && kek(3));";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── write: \n" +
                "        └── ||\n" +
                "            ├── +\n" +
                "            │   ├── 123\n" +
                "            │   └── *\n" +
                "            │       ├── 321\n" +
                "            │       └── /\n" +
                "            │           ├── 3e432\n" +
                "            │           └── 10.0\n" +
                "            └── &&\n" +
                "                ├── true\n" +
                "                └── Call : kek\n" +
                "                    └── 3\n");
    }

    @Test(expected = ParseException.class)
    public void testWhileNoCond() throws ParseException {
        String s = "while(){}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── read: var\n");
    }

    @Test
    public void testWhileKek() throws ParseException {
        String s = "while(kek){}";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── while\n" +
                "        └── condition\n" +
                "            └── kek\n");
    }

   /* @Test
    public void testWhile() throws ParseException {
        String s = "while(1 + 1 + 2.0 + 342 / 2 == 1 - kek(3)){" +
                "if (3){} xx= 2; while(1){} }";
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        parser.parse(targetStream).print();
        assertEquals(outContent.toString(), "└── AST\n" +
                "    └── while\n" +
                "        └── condition\n" +
                "            └── kek\n");
    }*/

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}
