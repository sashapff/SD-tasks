package token;

import exception.CalcVisitorException;
import exception.ParseVisitorException;
import visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor) throws ParseVisitorException, CalcVisitorException;
    String printName();
}
