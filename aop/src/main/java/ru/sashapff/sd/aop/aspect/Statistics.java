package profile;

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
