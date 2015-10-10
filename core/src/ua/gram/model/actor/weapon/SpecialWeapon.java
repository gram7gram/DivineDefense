package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class SpecialWeapon extends Weapon {

    public SpecialWeapon(Tower tower, Enemy target) {
        super(tower, target);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Batch batch) {

    }
}
