package tokenizer;

import exception.TokenizerException;
import token.*;

import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class Start implements State {
    @Override
    public State consume(char c, List<Token> tokens) {
        if (isWhitespace(c)) {
        } else if (c == '+') {
            tokens.add(new Add());
        } else if (c == '-') {
            tokens.add(new Sub());
        } else if (c == '*') {
            tokens.add(new Mul());
        } else if (c == '/') {
            tokens.add(new Div());
        } else if (c == '(') {
            tokens.add(new Open());
        } else if (c == ')') {
            tokens.add(new Close());
        } else if (isDigit(c)) {
            return new Number(c);
        } else {
            return new Error(new TokenizerException(c));
        }
        return this;
    }
}
