package visitor;

import exception.CalcVisitorException;
import token.Brace;
import token.NumberToken;
import token.Operation;

import java.util.Stack;

public class CalcVisitor extends TokenVisitor {
    private final Stack<Long> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getValue());
    }

    @Override
    public void visit(Brace token) throws CalcVisitorException {
        throw new CalcVisitorException("Unexpected bracket");
    }

    @Override
    public void visit(Operation token) throws CalcVisitorException {
        if (stack.size() < 2) {
            throw new CalcVisitorException("Invalid expression");
        }
        Long a = stack.pop();
        Long b = stack.pop();
        stack.add(token.evaluate(b, a));
    }

    public long getValue() throws CalcVisitorException {
        if (stack.size() != 1) {
            throw new CalcVisitorException("Invalid expression");
        }
        return stack.peek();
    }
}
