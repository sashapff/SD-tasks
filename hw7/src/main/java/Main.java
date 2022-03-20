import profiler.Statistics;

public class Main {
    public static void main(String[] args) {
        System.setProperty("profiledPackage", args[0]);
        Statistics statistics = Statistics.getInstance();
        Calculator calculator = new Calculator();
        calculator.add(1000000000, 1000000000);
        calculator.sub(999999999, 999999999);
        calculator.add(10, 11);

        statistics.print();
    }
}
