package token;

public class Mul extends Operation {
    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public String printName() {
        return "MUL";
    }

    public Long evaluate(Long a, Long b) {
        return a * b;
    }
}
