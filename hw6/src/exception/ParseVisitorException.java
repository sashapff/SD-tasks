package exception;

import token.Token;

public class ParseVisitorException extends Exception {
    public ParseVisitorException(Token token) {
        super("Unexpected token: " + token);
    }
}
