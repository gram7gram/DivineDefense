package ua.gram.model.prototype;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class LaserWeaponPrototype extends WeaponPrototype {
    public Color colorBack;
    public Color colorOver;
    public String startBack;
    public String startOver;
    public String middleBack;
    public String middleOver;
    public String endBack;
    public String endOver;

    @Override
    public float getDuration() {
        return -1;
    }
}
