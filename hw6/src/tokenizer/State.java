package tokenizer;

import exception.TokenizerException;
import token.Token;

import java.util.List;

public interface State {
    State consume(final char c, final List<Token> tokens);
}
