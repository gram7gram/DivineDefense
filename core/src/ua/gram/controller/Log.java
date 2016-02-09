package ua.gram.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * NOTE I don't like static things, yet deal with it...
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Log {

    static final String INFO = "INFO";
    static final String WARN = "WARN";
    static final String CRIT = "CRIT";
    static final String EXC = "EXC";
    private static final BlockingQueue<Message> messages = new ArrayBlockingQueue<>(100);
    static FileHandle logFile;

    public static void init(String path) {
        if (Gdx.files == null) throw new NullPointerException("Gdx is not initialized yet");
        if (Gdx.files.isExternalStorageAvailable()) {
            logFile = Gdx.files.external(Gdx.files.getExternalStoragePath() + path);
            System.out.println("Log file will write to external: " + logFile.path());
        } else if (Gdx.files.isLocalStorageAvailable()) {
            logFile = Gdx.files.local(path);
            System.out.println("Log file will write to local: " + logFile.path());
        }
    }

    public synchronized static void info(String msg) {
        messages.offer(new Message(INFO, msg));
//        write(INFO, msg);
    }

    public synchronized static void warn(String msg) {
        messages.offer(new Message(WARN, msg));
//        write(WARN, msg);
    }

    public synchronized static void crit(String msg) {
        messages.offer(new Message(CRIT, msg));
//        write(CRIT, msg);
    }

    public synchronized static void exc(String msg, Exception e) {
        msg = buildExceptionMessage(msg, e);
        messages.offer(new Message(EXC, msg));
//        write(EXC, msg);
    }

    private static String getPrefix(boolean value) {
        return value ? "+" : "-";
    }


    public static String buildExceptionMessage(String msg, Exception e) {
        msg += "\r\nCLASS:\t" + e.getClass().getSimpleName();
        msg += "\nMSG:\t" + e.getMessage();
        msg += "\nTRACE:\t" + Arrays.toString(e.getStackTrace()) + "\r\n";
        return msg;
    }

    public static boolean logToFile(String level, String msg) {
        if (logFile == null) return false;
        logFile.writeString(getTime() + " "
                + (level != null ? level + "\t" : "")
                + msg + "\n", true);
        return true;
    }

    private static String getTime() {
        return new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.ENGLISH)
                .format(new java.util.Date());
    }

    public static void update() {
        for (Message message : messages) {
            write(message.getLevel(), message.getMessage());
        }
        messages.clear();
    }

    public synchronized static void write(String level, String msg) {
        boolean result = logToFile(level, msg);

        String prefix = getPrefix(result) + level;

        switch (level) {
            case CRIT:
            case EXC:
                if (Gdx.app != null) Gdx.app.error(prefix, msg);
                else System.err.println(prefix + ": " + msg);
                break;
            case INFO:
            case WARN:
            default:
                if (Gdx.app != null) Gdx.app.log(prefix, msg);
                else System.out.println(prefix + ": " + msg);
                break;
        }
    }

    public static void dispose() {
        update();
    }
}
