package ru.spbau.solikov.lparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(args[0]);
        LexicalAnalyzer lexicalAnalyzer;
        try {
            lexicalAnalyzer = new LexicalAnalyzer(fileInputStream);
            try {
                lexicalAnalyzer.nextToken();
                while (lexicalAnalyzer.curToken.kind != Token.T.END) {
                    System.out.print(lexicalAnalyzer.curToken.kind + "(");
                    if (!lexicalAnalyzer.curToken.value.equals("")) {
                        System.out.print(lexicalAnalyzer.curToken.value + ',');

                    }
                    System.out.print(lexicalAnalyzer.curToken.line + "," +
                            lexicalAnalyzer.curToken.pos + "," +
                            (lexicalAnalyzer.curToken.length - 1 + lexicalAnalyzer.curToken.pos) + ")" + "; ");
                    lexicalAnalyzer.nextToken();
                }
            } catch (ParseException e){
                System.out.print("Error on position " + e.pos + " - " + e.message);
            }
        } catch (ParseException e) {
            System.out.print("Error on position " + e.pos + " - " + e.message);
        }
    }
}
