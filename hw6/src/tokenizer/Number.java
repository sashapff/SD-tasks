package tokenizer;

import token.NumberToken;
import token.Token;

import java.util.List;

import static java.lang.Character.isDigit;

public class Number implements State {
    private final StringBuilder stringValue = new StringBuilder();

    public Number(char c) {
        stringValue.append(c);
    }

    @Override
    public State consume(char c, List<Token> tokens) {
        if (isDigit(c)) {
            stringValue.append(c);
            return this;
        } else {
            long value = Long.parseLong(stringValue.toString());
            tokens.add(new NumberToken(value));
            State newState = new Start();
            newState.consume(c, tokens);
            return newState;
        }
    }
}
