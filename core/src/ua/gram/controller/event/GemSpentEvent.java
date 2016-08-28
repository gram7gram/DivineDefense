package ua.gram.controller.event;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GemSpentEvent extends Event {

    protected final int amount;

    public GemSpentEvent(int amount) {
        if (amount >= 0) {
            throw new IllegalArgumentException("Amount should be less then zero");
        }
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
