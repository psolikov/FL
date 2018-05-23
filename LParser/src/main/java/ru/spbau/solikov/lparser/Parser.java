package ru.spbau.solikov.lparser;

import ru.spbau.solikov.lparser.Token.T;

import java.io.InputStream;
import java.util.*;

public class Parser {

    private LexicalAnalyzer lex;
    private List<Token> tokens = new ArrayList<>();
    private int pos = 0;
    private HashMap<String, Integer> priority = new HashMap<>();

    {
        priority.put("+", 2);
        priority.put("-", 2);
        priority.put("*", 1);
        priority.put("/", 1);
        priority.put("%", 1);
        priority.put("==", 4);
        priority.put("!=", 4);
        priority.put(">", 3);
        priority.put("<", 3);
        priority.put(">=", 3);
        priority.put("<=", 3);
        priority.put("&&", 5);
        priority.put("||", 6);
        priority.put("=", 9);
    }

    public Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        do {
            lex.nextToken();
            tokens.add(lex.curToken);
        } while (lex.curToken.kind != Token.T.END);
        return parseP();
//        return new Tree("");
    }

    private Tree parseP() throws ParseException {
//        return def();
        List<Tree> p = new ArrayList<>();
        while (tokens.get(pos).kind == T.KW && tokens.get(pos).value.equals("def")) {
            Tree definition = def();
            p.add(definition);
//            pos++;
        }
        while (pos < tokens.size() && tokens.get(pos).kind != T.END) {
            Tree s = s();
            if (s != null) p.add(s);
            pos++;
        }
        expect(T.END);
        return new Tree("AST", p);
        /*Tree definitions = def();
        Tree statements = s();
//
        Tree t = new Tree("Program", Arrays.asList(definitions, statements));

        expect(Token.T.END);

        return t;*/
    }

    private Tree def() throws ParseException {
        List<Tree> children = new ArrayList<>();

        while (tokens.get(pos).kind == Token.T.KW) {
            if (!tokens.get(pos).value.equals("def")) break;
            pos++;
            switch (tokens.get(pos).kind) {
                case Ident:
                    String ident = tokens.get(pos).value;
//                    tokens.forEach(t -> System.out.println(t.kind + " " + t.value));
                    pos++;
                    expect(T.LPAREN);
                    pos++;
                    List<Tree> child = new ArrayList<>();
                    while (tokens.get(pos).kind == T.Ident) {
                        child.add(new Tree("Arg : " + tokens.get(pos).value));
                        pos++;
                        if (tokens.get(pos).kind == T.COMMA) pos++;
                    }
                    expect(T.RPAREN);
                    pos++;
                    expect(T.LCURV);
                    pos++;
                    List<Tree> ss = new ArrayList<>();
                    addStatement(ss);
//                    System.out.println("kakb");
//                        body.print();
//                        System.out.println(tokens.get(pos).value);
//                        System.out.println(tokens.get(pos).kind);
                    if (ss.size() != 0) {
                        child.addAll(ss);
//                        pos++;
                    }
                    expect(T.RCURV);
                    children.add(new Tree(ident, child));
                    pos++;
                    break;
                default:
                    throw new ParseException("Parse errorDef", tokens.get(pos).pos);
            }
        }

        return new Tree("Definitions", children);
    }

    private Tree ident() {
        return null;
    }

    private Tree s() throws ParseException {
        if ((tokens.get(pos).kind == T.Ident) && (pos + 1 < tokens.size()) && (tokens.get(pos + 1).kind == T.ASSIGN)) {
            Tree assign = assign();
            expect(T.SEMICOLON);
            return assign;
        }
        if (tokens.get(pos).kind == T.Ident) {
            Tree call = call();
            expect(T.SEMICOLON);
            return call;
        }
        if (tokens.get(pos).kind == T.KW) {
            if (tokens.get(pos).value.equals("while")) {
                return whileLoop();
            }
            if (tokens.get(pos).value.equals("if")) {
                return ifSt();
            }
            if (tokens.get(pos).value.equals("read") || tokens.get(pos).value.equals("write")) {
                Tree io = io();
                pos++;
                expect(T.SEMICOLON);
                return io;
            }
        }

        if (tokens.get(pos).kind == T.RCURV ) return null;
        throw new ParseException("Parse error ", tokens.get(pos).pos);
    }

    private Tree ifSt() throws ParseException {
        expect(T.KW);
        if (!tokens.get(pos).value.equals("if")) throw new ParseException("Parse error ", tokens.get(pos).pos);
        pos++;
        expect(T.LPAREN);
        pos++;
        Tree expr = expr(6);
        expect(T.RPAREN);
        pos++;
        expect(T.LCURV);
        pos++;
        List<Tree> ss = new ArrayList<>();
        ss.add(new Tree("condition", Collections.singletonList(expr)));
        addStatement(ss);
        expect(T.RCURV);
        if (pos < tokens.size() && tokens.get(pos + 1).kind == T.KW && tokens.get(pos + 1).value.equals("else")) {
            pos++;
            List<Tree> ssElse = new ArrayList<>();
            pos++;
            expect(T.LCURV);
            pos++;
            addStatement(ssElse);
            expect(T.RCURV);
            return new Tree("if", Arrays.asList(new Tree("then", ss), new Tree("else", ssElse)));
        }
        return new Tree("if", ss);
    }

    private void addStatement(List<Tree> ssElse) throws ParseException {
        while (tokens.get(pos).kind != T.RCURV) {
            Tree s = s();
            if (s != null) ssElse.add(s);
            if(tokens.get(pos).kind != T.RCURV) pos++;
        }
    }

    private Tree assign() throws ParseException {
//        expect(Token.Ident);
//        String ident = lex.curToken.value;
//        lex.nextToken();
//        expect(T.ASSIGN);
//        lex.nextToken();
//        Tree expr = expr();
//        return new Tree("Assign", Arrays.asList(new Tree(ident), expr));

        expect(T.Ident);
        String ident = tokens.get(pos).value;
        pos++;
        expect(T.ASSIGN);
        pos++;
        Tree expr = expr(6);

        return new Tree("Assign:", Arrays.asList(new Tree(ident), expr));
    }

    private Tree call() throws ParseException {
        expect(T.Ident);
        String name = tokens.get(pos).value;
        pos++;
        expect(T.LPAREN);
        pos++;
        List<Tree> list = new ArrayList<>();
        while (tokens.get(pos).kind != T.RPAREN) {
            Tree t = expr(6);
            list.add(t);
//            pos++;
            if (tokens.get(pos).kind != T.COMMA) {
                expect(T.RPAREN);
                pos--;
            } else if (tokens.get(pos + 1).kind == T.RPAREN) {
                throw new ParseException("Parse error ", tokens.get(pos).pos);
            }
            pos++;
        }
        pos++;
        return new Tree("Call : " + name, list);
    }

    private Tree expr(int p) throws ParseException {
        if (p == 0) {
            return e();
        }

        Token op = null;
        Tree expr = null;

        Tree next = expr(p - 1);
        if (pos < tokens.size()) {
            Token cur = tokens.get(pos);
            if ((cur.kind == T.Op) && (priority.getOrDefault(cur.value, -1) == p)) {
                pos++;
                op = cur;
                expr = expr(p);
            }
        }
        if (op != null && expr != null) {
            return new Tree(op.value, Arrays.asList(next, expr));
        }

        return next;
       /* if (tokens.get(pos).kind == T.LPAREN){
            Tree e = e();
            pos++;
            expect(T.RPAREN);
            pos++;
            expect(T.Op);
            String op = tokens.get(pos).value;
            pos++;
            Tree expr = expr();
            return new Tree(op, Arrays.asList(e,expr));
        }
        return e();*/
        /*Tree e = e();
//        pos++;
        if (tokens.get(pos).kind == T.Op) {
            System.outwhiln(tokens.get(pos).kind + "here†®" + tokens.get(pos).value);
            String op = tokens.get(pos).value;
            pos++;
            Tree expr = expr();
            return new Tree(op, Arrays.asList(e, expr));
        }
        return e;*/
    }

    private Tree e() throws ParseException {
        if (tokens.get(pos).kind == T.Literal && (tokens.get(pos).value.equals("true") ||
                tokens.get(pos).value.equals("false"))) {
            Tree answer = new Tree(tokens.get(pos).value);
            pos++;
            return answer;
        }

        if (tokens.get(pos).kind == T.Literal) {
            Tree answer = new Tree(tokens.get(pos).value);
            pos++;
            return answer;
        }

        if (tokens.get(pos).kind == T.Ident) {
            String ident = tokens.get(pos).value;
            if (tokens.get(pos + 1).kind == T.LPAREN) {
                return call();
            }
            pos++;
            return new Tree(ident);
        }

        if (tokens.get(pos).kind == T.LPAREN) {
            pos++;
            Tree t = expr(6);
//            pos++;
            expect(T.RPAREN);
            pos++;
            return t;
        }

        throw new ParseException("Parse error ", tokens.get(pos).pos);
    }

    private Tree io() throws ParseException {
        expect(T.KW);
        if (tokens.get(pos).value.equals("read")) {
            pos++;
            expect(T.LPAREN);
            pos++;
            expect(T.Ident);
            String ident = tokens.get(pos).value;
            pos++;
            expect(T.RPAREN);
            return new Tree("read: " + ident);
        } else if (tokens.get(pos).value.equals("write")) {
            pos++;
            expect(T.LPAREN);
            pos++;
            Tree expr = expr(6);
            expect(T.RPAREN);
            return new Tree("write: ", Collections.singletonList(expr));
        }

        throw new ParseException("Parse error ", tokens.get(pos).pos);
    }

    private Tree whileLoop() throws ParseException {
        expect(T.KW);
        if (!tokens.get(pos).value.equals("while")) throw new ParseException("Parse error ", tokens.get(pos).pos);
        pos++;
        expect(T.LPAREN);
        pos++;
        Tree expr = expr(6);
        expect(T.RPAREN);
        pos++;
        expect(T.LCURV);
        pos++;
        List<Tree> ss = new ArrayList<>();
        ss.add(new Tree("condition", Collections.singletonList(expr)));
        addStatement(ss);
        expect(T.RCURV);
        pos++;
        return new Tree("while", ss);
    }

    private void expect(Token.T t) throws ParseException {
        if (t == T.END && pos >= tokens.size()) return;
        if (tokens.get(pos).kind != t)
            throw new ParseException("Parse error " + t.toString() + " " + tokens.get(pos).kind, tokens.get(pos).pos);
//        if (lex.curToken != t) throw new ParseException("Parse error", lex.getCurPos());
    }

    private Tree p() {
        return null;
    }
}
