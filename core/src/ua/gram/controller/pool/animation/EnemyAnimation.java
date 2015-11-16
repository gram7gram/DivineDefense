package ua.gram.controller.pool.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.model.Animator;
import ua.gram.model.Player;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimation implements AnimationControllerInterface<Enemy> {

    private final GameActor.Types origin;
    private final EnemyAnimationProvider provider;
    public boolean initialized;
    private DirectionPoolInterface idlePool;
    private DirectionPoolInterface walkingPool;
    private DirectionPoolInterface deadPool;
    private DirectionPoolInterface spawnPool;
    private DirectionPoolInterface abilityPool;
    private Enemy enemy;

    public EnemyAnimation(GameActor.Types origin, EnemyAnimationProvider provider) {
        this.origin = origin;
        this.provider = provider;
        initialized = false;
    }

    @Override
    public void init(Enemy enemy) {
        this.enemy = enemy;
        GameActor.Types origin = enemy.getOriginType();
        initialized = true;
        try {
            walkingPool = new EnemyDirectionAnimationPool(Animator.Types.WALKING, this);
        } catch (Exception e) {
            initialized = false;
            Gdx.app.error("EXC", "Error at loading WALKING animation type for " + origin);
        }
        try {
            deadPool = new EnemyDirectionAnimationPool(Animator.Types.DEAD, this);
        } catch (Exception e) {
            initialized = false;
            Gdx.app.error("EXC", "Error at loading DEAD animation type for " + origin);
        }
        try {
            spawnPool = new EnemyDirectionAnimationPool(Animator.Types.SPAWN, this);
        } catch (Exception e) {
            initialized = false;
            Gdx.app.error("EXC", "Error at loading SPAWN animation type for " + origin);
        }
        try {
            idlePool = new EnemyDirectionAnimationPool(Animator.Types.IDLE, this);
        } catch (Exception e) {
            initialized = false;
            Gdx.app.error("EXC", "Error at loading IDLE animation type for " + origin);
        }

        if (origin == GameActor.Types.SUMMONER) {
            try {
                abilityPool = new AbilityUserDirectionAnimationPool(Animator.Types.ABILITY, this);
            } catch (Exception e) {
                initialized = false;
                Gdx.app.error("EXC", "Error at loading ABILITY animation type for " + origin);
            }
        }
        Gdx.app.log("INFO", "Animation for " + origin + " is OK");
    }

    @Override
    public TextureRegion[] setAnimationRegion(Animator.Types type, Path.Types direction, Animator.Types ability) throws NullPointerException {
        if (enemy == null) throw new NullPointerException("Missing enemy");
        String region = enemy.getClass().getSimpleName()
                + "_" + Player.SYSTEM_FRACTION
                + "_" + type
                + "_" + direction
                + (ability != null ? "_" + ability : "");
        TextureRegion texture = provider.getSkin().getRegion(region);

        if (texture == null) throw new NullPointerException("Texture not found: " + region);

        TextureRegion[][] regions = texture.split(
                (int) enemy.getAnimationWidth(),
                (int) enemy.getAnimationHeight());
        return regions[0];
    }

    @Override
    public void free(Animator.Types type, Path.Types direction, Animation animation) {
        this.get(type).get(direction).free(animation);
    }

    @Override
    public Animation obtain(Animator.Types type, Path.Types direction) throws Exception {
        return  this.get(type).get(direction).obtain();
    }

    public DirectionPoolInterface get(Animator.Types type) throws IllegalArgumentException {
        switch (type) {
            case WALKING:
                return walkingPool;
            case DEAD:
                return deadPool;
            case SPAWN:
                return spawnPool;
            case ABILITY:
                return abilityPool;
            case IDLE:
                return idlePool;
            default:
                throw new IllegalArgumentException("Unknown animation origin: " + type);
        }
    }
}
