package Utils;

public class Timer {

    private static long _startTime;
    private static long _endTime;

    public static void start() {
        _startTime = System.nanoTime();
    }

    public static void stop() {
        _endTime = System.nanoTime();
    }

    public static long getElapsedTime() {
        return _endTime - _startTime;
    }
}
