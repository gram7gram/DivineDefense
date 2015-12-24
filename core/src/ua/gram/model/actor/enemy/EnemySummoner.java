package ua.gram.model.actor.enemy;

import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends AbilityUser implements Cloneable {

    private Vector2 previousBadPosition;

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
        previousBadPosition = Vector2.Zero;
    }

    @Override
    public synchronized boolean ability() {
        Vector2 pos = this.getCurrentPosition();
        Vector2 next = this.getCurrentDirection();
        Vector2 position = new Vector2(
                (Math.round(pos.x) - (Math.round(pos.x) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.x,
                (Math.round(pos.y) - (Math.round(pos.y) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.y);

        Map map = getSpawner().getLevel().getMap();

        if (Path.compare(previousBadPosition, position) || !map.checkSpawnPosition(position)) {
            previousBadPosition = Path.clone(position);
            Log.crit("Cannot spawn child. Requested cell "
                    + Path.toString(position) + " does not contain nessesary property");
            return false;
        } else {
            previousBadPosition = Vector2.Zero;
        }

        try {
            spawner.spawnChild(this, "EnemySoldier", position);
            Log.info(this + " performs ability");
        } catch (Exception e) {
            Log.exc("Could not execute ability for " + this, e);
            return false;
        }

        return true;
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }

    @Override
    public void reset() {
        super.reset();
        previousBadPosition = Vector2.Zero;
    }
}
