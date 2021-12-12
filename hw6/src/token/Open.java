package token;

import visitor.TokenVisitor;

public class Open extends Brace {
    @Override
    public String printName() {
        return "OPEN";
    }
}
