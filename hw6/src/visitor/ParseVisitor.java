package visitor;

import exception.ParseVisitorException;
import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParseVisitor extends TokenVisitor {
    private List<Token> tokens = new ArrayList<>();
    private Stack<Token> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) throws ParseVisitorException {
        if (token instanceof Open) {
            stack.add(token);
        } else {
            while (!stack.isEmpty() && !(stack.peek() instanceof Open)) {
                tokens.add(stack.peek());
                stack.pop();
            }
            if (stack.isEmpty()) {
                throw new ParseVisitorException(new Close());
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.empty() && stack.peek() instanceof Operation
                && ((Operation) stack.peek()).getPriority() >= token.getPriority()) {
            tokens.add(stack.peek());
            stack.pop();
        }
        stack.add(token);
    }

    public List<Token> getTokens() throws ParseVisitorException {
        while (!stack.empty()) {
            if (stack.peek() instanceof Operation) {
                tokens.add(stack.peek());
                stack.pop();
            } else {
                throw new ParseVisitorException(stack.peek());
            }
        }

        return tokens;
    }
}
