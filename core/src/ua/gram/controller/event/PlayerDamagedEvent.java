package ua.gram.controller.event;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PlayerDamagedEvent extends DamageEvent {
    public PlayerDamagedEvent(float damage) {
        super(damage);
    }
}
