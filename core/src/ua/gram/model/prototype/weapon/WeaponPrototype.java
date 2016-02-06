package ua.gram.model.prototype.weapon;

import ua.gram.model.prototype.Prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class WeaponPrototype extends Prototype {
    public String type;
    protected float duration;

    public abstract float getDuration();
}
