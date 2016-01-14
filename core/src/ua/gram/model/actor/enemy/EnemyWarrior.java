package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemyWarrior extends Enemy implements Cloneable {

    public EnemyWarrior(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemyWarrior clone() throws CloneNotSupportedException {
        return (EnemyWarrior) super.clone();
    }

}
