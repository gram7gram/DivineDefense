package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemyRunner extends Enemy implements Cloneable {

    public EnemyRunner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemyRunner clone() throws CloneNotSupportedException {
        return (EnemyRunner) super.clone();
    }
}
