package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

public class PrintVisitor extends TokenVisitor {
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void visit(NumberToken token) {
        stringBuilder.append(token.printName() + ' ');
    }

    @Override
    public void visit(Brace token) {
        stringBuilder.append(token.printName() + ' ');
    }

    @Override
    public void visit(Operation token) {
        stringBuilder.append(token.printName() + ' ');
    }

    public String getExpression() {
        return stringBuilder.toString();
    }
}
