package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.model.prototype.EnemyPrototype;

import java.util.Arrays;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends AbilityUser implements Cloneable {

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public synchronized void ability() {
        try {
            Vector2 pos = this.getCurrentPosition();
            Vector2 next = this.getPath().peekNextDirection();
            Vector2 position = new Vector2(
                    (pos.x - (pos.x % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.x,
                    (pos.y - (pos.y % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.y);
            this.getSpawner().spawnChild(this, "EnemySoldier", position);
            Gdx.app.log("INFO", this + " performs ability");
        } catch (Exception e) {
            Gdx.app.error("EXC", "Could not execute ability for " + this
                    + "\r\nMSG: " + e.getMessage()
                    + "\r\nTRACE: " + Arrays.toString(e.getStackTrace()));
        }
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
