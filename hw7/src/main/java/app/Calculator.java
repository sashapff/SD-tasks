package app;

public class Calculator {
    public Calculator() {
    }

    private int increment(int a) {
        int res = 1;
        for (int i = 0; i < a; i++) {
            res += 1;
        }
        return res;
    }

    private int decrement(int a) {
        return a - 1;
    }

    public void add(int a, int b) {
        while (b > 0) {
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
