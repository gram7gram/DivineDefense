package ua.gram.model.actor.enemy;

import com.badlogic.gdx.math.Vector2;

import ua.gram.DDGame;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.AbilityUserPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends AbilityUser implements Cloneable {

    private final Vector2 minionSpawnPosition;

    public EnemySummoner(DDGame game, AbilityUserPrototype prototype) {
        super(game, prototype);
        minionSpawnPosition = Vector2.Zero;
    }

    private Vector2 getMinionSpawnPosition(Vector2 pos, Vector2 next) {
        minionSpawnPosition.set(
                (Math.round(pos.x) - (Math.round(pos.x) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.x,
                (Math.round(pos.y) - (Math.round(pos.y) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.y);
        return minionSpawnPosition;
    }

    @Override
    public synchronized boolean ability() {
        Vector2 pos = getCurrentPosition();
        Vector2 next = getCurrentDirection();
        Vector2 position = getMinionSpawnPosition(pos, next);

        Map map = getSpawner().getLevel().getMap();

        if (!map.getVoter().isWalkable((int) position.x, (int) position.y)) {
            throw new IllegalArgumentException("Cannot spawn minion at "
                    + Path.toString(position) + ": cell is not walkable");
        }

        spawner.spawn(getPrototype().minion, position, this);

        Log.info(this + " performs ability");

        return true;
    }

    @Override
    public void update(float delta) {
        setOrigin(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }

    @Override
    public void reset() {
        super.reset();
        minionSpawnPosition.set(0, 0);
    }

    @Override
    public AbilityUserPrototype getPrototype() {
        return (AbilityUserPrototype) prototype;
    }
}
