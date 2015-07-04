package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemySoldier extends Enemy implements Cloneable {

    public EnemySoldier(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySoldier clone() throws CloneNotSupportedException {
        return (EnemySoldier) super.clone();
    }
}
