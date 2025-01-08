package Utils;

public class Timer {

    private static long startTime;
    private static long endTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void stop() {
        endTime = System.nanoTime();
    }

    public static long getElapsedTime() {
        return endTime - startTime;
    }
}
