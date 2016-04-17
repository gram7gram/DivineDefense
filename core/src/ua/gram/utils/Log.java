package ua.gram.utils;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Log {

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String CRIT = "CRIT";
    public static final String EXC = "EXC";

    /**
     * Normal log
     */
    public synchronized static void info(String msg) {
        if (Gdx.app != null) {
            Gdx.app.log(INFO, msg);
        } else {
            System.out.println(INFO + ": " + msg);
        }
    }

    public synchronized static void warn(String msg) {
        if (Gdx.app != null) {
            Gdx.app.log(WARN, msg);
        } else {
            System.out.println(WARN + ": " + msg);
        }
    }

    /** Log critical errors. Should not happen */
    public synchronized static void crit(String msg) {
        if (Gdx.app != null) {
            Gdx.app.error(CRIT, msg);
        } else {
            System.err.println(CRIT + ": " + msg);
        }
    }

    /** Log exceptions */
    public synchronized static void exc(String msg, Exception e) {
        String message = buildExceptionMessage(msg, e);
        if (Gdx.app != null) {
            Gdx.app.error("\n" + EXC, message);
        } else {
            System.err.println("\n" + EXC + ": " + message);
        }
    }

    public static String buildExceptionMessage(String msg, Exception e) {
        msg += "\r\nCLASS: " + e.getClass().getSimpleName();
        msg += "\nMSG: " + e.getMessage();
        msg += "\nTRACE: " + Arrays.toString(e.getStackTrace());
        msg += "\nCAUSE: " + e.getCause();
        msg += "\r\n";
        return msg;
    }

    private String getTime() {
        return new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.ENGLISH)
                .format(new java.util.Date());
    }
}
