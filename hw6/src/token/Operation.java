package token;

import exception.CalcVisitorException;
import visitor.TokenVisitor;

public abstract class Operation implements Token {
    abstract public int getPriority();

    @Override
    public void accept(TokenVisitor visitor) throws CalcVisitorException {
        visitor.visit(this);
    }

    abstract public Long evaluate(Long a, Long b) throws CalcVisitorException;
}
