package ru.sashapff.sd.aop.aspect;

public class Statistics {
    private Statistics() {
    }

    private static final Statistics INSTANCE = new Statistics();

    public static Statistics getInstance() {
        return INSTANCE;
    }

    public void print() {

    }
}
