package token;

import exception.CalcVisitorException;
import exception.ParseVisitorException;
import visitor.TokenVisitor;

public abstract class Brace implements Token {
    @Override
    public void accept(TokenVisitor visitor) throws ParseVisitorException, CalcVisitorException {
        visitor.visit(this);
    }
}
