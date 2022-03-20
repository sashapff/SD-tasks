package app;

public class Calculator {
    public Calculator() {
    }

    private int increment(int a) {
        return a + 1;
    }

    private int decrement(int a) {
        return a - 1;
    }

    public void add(int a, int b) {
        while (a > 0) {
            a = increment(a);
            b = decrement(b);
        }
    }

    public void sub(int a, int b) {
        while (b > 0) {
            a = decrement(a);
            b = decrement(b);
        }
    }

}
