package exception;

import token.Token;

public class CalcVisitorException extends Exception {
    public CalcVisitorException(String string) {
        super(string);
    }
}
