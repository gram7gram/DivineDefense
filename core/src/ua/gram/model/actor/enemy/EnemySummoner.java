package ua.gram.model.actor.enemy;

import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends AbilityUser implements Cloneable {

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);

    }

    @Override
    public void ability() {
//        try {
//            Vector2 pos = this.getPosition();
//            Vector2 next = this.getPath().peekNextDirection();
//            enemy.getSpawner().spawn("EnemySoldier", new Vector2(pos.x + next.x, pos.y + next.y));
//            Gdx.app.log("INFO", this + " performs ability");
//        } catch (Exception e) {
//            Gdx.app.error("EXC", "Could not execute ability for " + this
//                    + "\r\nMSG: " + e.getMessage()
//                    + "\r\nTRACE: " + Arrays.toString(e.getStackTrace()));
//        }
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }
}
