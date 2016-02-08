package ua.gram.controller;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Message {
    private final String level;
    private final String message;

    public Message(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
