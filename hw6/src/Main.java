import exception.CalcVisitorException;
import exception.ParseVisitorException;
import exception.TokenizerException;
import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParseVisitor;
import visitor.PrintVisitor;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        try {
            Tokenizer tokenizer = new Tokenizer(new Scanner(System.in).nextLine());
            List<Token> tokens = tokenizer.tokenize();

            ParseVisitor parseVisitor = new ParseVisitor();
            parseVisitor.process(tokens);
            tokens = parseVisitor.getTokens();

            PrintVisitor printVisitor = new PrintVisitor();
            printVisitor.process(tokens);
            String expression = printVisitor.getExpression();


            CalcVisitor calcVisitor = new CalcVisitor();
            calcVisitor.process(tokens);
            long value = calcVisitor.getValue();

            System.out.println(expression);
            System.out.println(value);
        } catch (TokenizerException | ParseVisitorException | CalcVisitorException e) {
            System.out.println(e.getMessage());
        }
    }
}
