package ua.gram.model.prototype.weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FreezeWeaponPrototype extends AOEWeaponPrototype {
    public String region;
    public float delay;
    public int frames;

    @Override
    public float getDuration() {
        return duration == 0 ? delay * frames : duration;
    }
}
