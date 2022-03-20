package profiler;

import org.aspectj.lang.Signature;

import java.util.HashMap;
import java.util.Stack;

public class Statistics {
    private static final Statistics INSTANCE = new Statistics();

    private Statistics() {
    }

    public static Statistics getInstance() {
        return INSTANCE;
    }

    private final HashMap<String, Long> methodTime = new HashMap<>();
    private final HashMap<String, Integer> methodCalls = new HashMap<>();
    private final Stack<Long> stack = new Stack<>();

    public void add(Signature signature) {
        String name = signatureToString(signature);

        if (!methodCalls.containsKey(name)) {
            methodCalls.put(name, 0);
        }
        methodCalls.put(name, methodCalls.get(name) + 1);

        stack.add(System.currentTimeMillis());
    }

    public void remove(Signature signature) {
        String name = signatureToString(signature);
        Long last = stack.pop();

        long previous = 0;
        if (methodTime.containsKey(name)) {
            previous = methodTime.get(name);
        }

        methodTime.put(name, previous + System.currentTimeMillis() - last);
    }

    public void print() {
        for (String name : methodCalls.keySet()) {
            System.out.println("Method " + name);
            System.out.println("\tCalled: " + methodCalls.get(name) + " times");
            System.out.println("\tSummary process time: " + methodTime.get(name) + " millis");
            System.out.println("\tAverage process time: " + methodTime.get(name) / methodCalls.get(name) + " millis");
        }
    }

    private String signatureToString(Signature signature) {
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

}
