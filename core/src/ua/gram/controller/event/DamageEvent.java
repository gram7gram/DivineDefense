package ua.gram.controller.event;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DamageEvent extends Event {

    private final float damage;

    public DamageEvent(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
