package ua.gram.controller;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;

import java.util.Arrays;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Log {

    public synchronized static void info(String msg) {
        Gdx.app.log("INFO", msg);
    }

    public synchronized static void warn(String msg) {
        Gdx.app.log("WARN", msg);
    }

    public synchronized static void exc(String msg, Exception e) {
        if (DDGame.DEBUG) {
            msg += "\r\nMSG: " + e.getMessage();
            msg += "\r\nTRACE: " + Arrays.toString(e.getStackTrace());
        }
        Gdx.app.error("EXC", msg);
    }
}
