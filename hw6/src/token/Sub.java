package token;

public class Sub extends Operation {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String printName() {
        return "SUB";
    }

    public Long evaluate(Long a, Long b) {
        return a - b;
    }
}
