package token;

public class Add extends Operation {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String printName() {
        return "ADD";
    }

    public Long evaluate(Long a, Long b) {
        return a + b;
    }
}
