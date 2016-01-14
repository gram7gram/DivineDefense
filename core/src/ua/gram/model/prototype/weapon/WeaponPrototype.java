package ua.gram.model.prototype.weapon;

import ua.gram.model.prototype.PrototypeInterface;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class WeaponPrototype implements PrototypeInterface {
    public String type;
    protected float duration;

    public abstract float getDuration();
}
