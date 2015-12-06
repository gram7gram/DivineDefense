package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;

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
            Vector2 next = this.getCurrentDirection();
            Vector2 index = this.getCurrentPositionIndex();
            Vector2 position = new Vector2(
                    (Math.round(pos.x) - (Math.round(pos.x) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.x,
                    (Math.round(pos.y) - (Math.round(pos.y) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.y);

            System.out.println("--------");
            System.out.println("Current: " + Path.toStringRound(pos));
            System.out.println("Index: " + Path.toString(index));
            System.out.println("Direction: " + Path.toString(next));
            System.out.println("Result: " + Path.toString(position));
            System.out.println("Check: " +
                    ((int) (index.x + next.x) == (int) position.x
                            && (int) (index.y + next.y) == (int) position.y));

            this.getSpawner().spawnChild(this, "EnemySoldier", position);
            Gdx.app.log("INFO", this + " performs ability");
        } catch (Exception e) {
            Log.exc("Could not execute ability for " + this, e);
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
