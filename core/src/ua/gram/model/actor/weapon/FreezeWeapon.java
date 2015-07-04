package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;

/**
 * Weapon for TowerStun
 *
 * @author Gram <gram7gram@gmail.com>
 */
public final class FreezeWeapon extends Weapon {

    public FreezeWeapon(Tower tower, Enemy target) {
        super(tower, target);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Batch batch) {

    }
}
