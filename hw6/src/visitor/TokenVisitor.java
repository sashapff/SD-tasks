package visitor;

import exception.CalcVisitorException;
import exception.ParseVisitorException;
import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.List;

public abstract class TokenVisitor {
    abstract public void visit(NumberToken token);

    abstract public void visit(Brace token) throws ParseVisitorException, CalcVisitorException;

    abstract public void visit(Operation token) throws CalcVisitorException;

    public void process(List<Token> tokens) throws ParseVisitorException, CalcVisitorException {
        for (Token token : tokens) {
            token.accept(this);
        }
    }
}
