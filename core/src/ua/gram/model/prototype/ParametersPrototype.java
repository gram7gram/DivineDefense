package ua.gram.model.prototype;

import com.badlogic.gdx.Gdx;

/**
 * TODO Split into Global and Game configuration
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class ParametersPrototype extends Prototype {

    public String[] consoleHello;
    public String[] consoleBye;
    public String id;
    public String configPath;
    public String gameModule;
    public String osName;
    public String deviceId;
    public String contact;
    public String author;
    public String repository;
    public String title;
    public String git;
    public String skin;
    public String logFile;
    public boolean debugging;
    public int maxLevels;
    public int logLevel;
    public ReportPrototype reportConfig;

    public String getLogFilePath() {
        return Gdx.files.getExternalStoragePath() + logFile;
    }
}
