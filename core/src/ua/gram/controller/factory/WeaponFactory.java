package ua.gram.controller.factory;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.builder.WeaponBuilder;
import ua.gram.model.actor.weapon.BombWeapon;
import ua.gram.model.actor.weapon.FreezeWeapon;
import ua.gram.model.actor.weapon.LaserWeapon;
import ua.gram.model.actor.weapon.LightningWeapon;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.prototype.weapon.BombWeaponPrototype;
import ua.gram.model.prototype.weapon.FreezeWeaponPrototype;
import ua.gram.model.prototype.weapon.LaserWeaponPrototype;
import ua.gram.model.prototype.weapon.LightningWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WeaponFactory {

    public static Weapon create(DDGame game, WeaponBuilder builder, WeaponPrototype proto) {
        switch (proto.type.toUpperCase()) {
            case "LASER":
                if (!(proto instanceof LaserWeaponPrototype))
                    throw new GdxRuntimeException("Weapon prototype is not instance of LaserWeaponPrototype");
                LaserWeaponPrototype prototype = (LaserWeaponPrototype) proto;
                return new LaserWeapon(builder, game.getResources(), prototype);
            case "BOMB":
                if (!(proto instanceof BombWeaponPrototype))
                    throw new GdxRuntimeException("Weapon prototype is not instance of BombWeaponPrototype");
                BombWeaponPrototype bprototype = (BombWeaponPrototype) proto;
                return new BombWeapon(builder, game.getResources(), bprototype);

            case "LIGHTNING":
                if (!(proto instanceof LightningWeaponPrototype))
                    throw new GdxRuntimeException("Weapon prototype is not instance of LightningWeaponPrototype");
                LightningWeaponPrototype lprototype = (LightningWeaponPrototype) proto;
                return new LightningWeapon(builder, game.getResources(), lprototype);

            case "FREEZE":
                if (!(proto instanceof FreezeWeaponPrototype))
                    throw new GdxRuntimeException("Weapon prototype is not instance of FreezeWeaponPrototype");
                FreezeWeaponPrototype fprototype = (FreezeWeaponPrototype) proto;
                return new FreezeWeapon(builder, game.getResources(), fprototype);

            default:
                throw new IllegalArgumentException("Cannot create weapon " + proto.type);

        }
    }
}
