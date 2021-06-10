package utils;

public class LogUtil {

    public static void log(String statement) {
        System.out.println(statement);
    }

    public static void logError(String error) {
        String errorLog = "ERROR: " + error;
        System.out.println(errorLog);
    }
}
