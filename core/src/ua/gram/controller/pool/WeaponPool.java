package ua.gram.controller.pool;

import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.factory.WeaponFactory;
import ua.gram.controller.weapon.WeaponProvider;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WeaponPool extends Pool<Weapon> {

    private final WeaponPrototype prototype;
    private final DDGame game;
    private final WeaponProvider builder;

    public WeaponPool(DDGame game, WeaponProvider builder, WeaponPrototype prototype) {
        super(5, DDGame.MAX_ENTITIES);
        this.prototype = prototype;
        this.builder = builder;
        this.game = game;
    }

    public WeaponPrototype getPrototype() {
        return prototype;
    }

    @Override
    protected Weapon newObject() {
        return WeaponFactory.create(game, builder, prototype);
    }
}
