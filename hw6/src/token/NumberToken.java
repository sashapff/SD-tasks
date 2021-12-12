package token;

import visitor.TokenVisitor;

public class NumberToken implements Token {
    private long value;

    public NumberToken(long value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String printName() {
        return "NUMBER(" + value + ")";
    }

    public long getValue() {
        return value;
    }
}
