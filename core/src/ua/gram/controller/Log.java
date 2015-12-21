package ua.gram.controller;

import com.badlogic.gdx.Gdx;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Log {

    public synchronized static void info(String msg) {
        Gdx.app.log("INFO", new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": " + msg);
    }

    public synchronized static void warn(String msg) {
        Gdx.app.log("WARN", msg);
    }

    public synchronized static void exc(String msg, Exception e) {
        msg += "\r\nCLASS:\t" + e.getClass().getSimpleName();
        msg += "\r\nMSG:\t" + e.getMessage();
        msg += "\r\nTRACE:\t" + Arrays.toString(e.getStackTrace());
        Gdx.app.error("EXC", new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": " + msg);
    }
}
