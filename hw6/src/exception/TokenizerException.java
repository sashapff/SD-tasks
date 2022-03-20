package exception;

public class TokenizerException extends Exception {
    public TokenizerException(char c) {
        super("Unexpected symbol: " + c);
    }
}
