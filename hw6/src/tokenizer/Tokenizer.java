package tokenizer;

import exception.TokenizerException;
import token.Token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String expression;
    private State state = new Start();

    public Tokenizer(String string) {
        expression = string;
    }

    public List<Token> tokenize() throws TokenizerException {
        List<Token> tokens = new ArrayList<>();
        for (int position = 0; position < expression.length(); position++) {
            state = state.consume(expression.charAt(position), tokens);
        }

        if (state instanceof Number) {
            state.consume(' ', tokens);
        }

        if (state instanceof Error) {
            throw ((Error) state).getException();
        }

        return tokens;
    }

}
