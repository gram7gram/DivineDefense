package ua.gram.model.actor.enemy;

import com.badlogic.gdx.math.Vector2;

import ua.gram.DDGame;
import ua.gram.controller.voter.TiledMapVoter;
import ua.gram.model.enums.Voter;
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

    private Vector2 getMinionSpawnPosition(Vector2 pos, Path.Direction nextType) {
        Vector2 next = Path.getVector(nextType);

        if (next == null || next.x > 1 || next.x < -1 || next.y > 1 || next.y < -1)
            throw new IllegalArgumentException("Next vector (" + nextType + ") is not a valid direction");

        minionSpawnPosition.set(
                pos.x + next.x,
                pos.y + next.y);

        return minionSpawnPosition;
    }

    @Override
    public synchronized boolean ability() {
        if (path.stepsBeforeFinishLength() <= getPrototype().abilityDelay + 1) {
            isInterrupted = true;
            abilityDurationCount = -1;
            Log.warn(this + " is too close to Base to perform ability");
            return false;
        }

        Vector2 pos = getDirectionHolder().getCurrentPositionIndex();
        Path.Direction next = getDirectionHolder().getCurrentDirectionType();
        Vector2 index = getMinionSpawnPosition(pos, next);

        int x = (int) index.x;
        int y = (int) index.y;

        TiledMapVoter voter = getSpawner().getLevel().getMap().getVoter();

        if (!voter.isWalkable(x, y)) {
            throw new IllegalArgumentException("Cannot spawn minion at ["
                    + x + ":" + y + "]: cell is not walkable");
        }

        if (!voter.isBase(x, y, Voter.Policy.AFFIRMATIVE)) {
            spawner.spawn(getPrototype().minion, index, this);

            Log.info(this + " performs ability");
        } else {
            Log.warn(this + " tried to perform ability at the Base");
        }

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
