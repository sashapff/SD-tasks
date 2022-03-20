package tokenizer;

import exception.TokenizerException;
import token.Token;

import java.util.List;

public class Error implements State {
    private final TokenizerException exception;

    public Error(TokenizerException exception) {
        this.exception = exception;
    }

    @Override
    public State consume(char c, List<Token> tokens) {
        return this;
    }

    public TokenizerException getException() {
        return this.exception;
    }
}
