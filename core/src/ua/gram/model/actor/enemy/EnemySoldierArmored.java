package ua.gram.model.actor.enemy;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySoldierArmored extends EnemySoldier {

    public EnemySoldierArmored(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySoldierArmored clone() throws CloneNotSupportedException {
        return (EnemySoldierArmored) super.clone();
    }

}
