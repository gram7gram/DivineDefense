package ua.gram.controller.event;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GemEarnedEvent extends Event {

    protected final int amount;

    public GemEarnedEvent(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be greater then zero");
        }
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
