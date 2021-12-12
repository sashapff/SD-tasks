package token;

import exception.CalcVisitorException;

public class Div extends Operation {
    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public String printName() {
        return "DIV";
    }

    public Long evaluate(Long a, Long b) throws CalcVisitorException {
        if (b == 0) {
            throw new CalcVisitorException("division by 0");
        }
        return a / b;
    }
}
