package ua.gram.model.actor.boss;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateHolder;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.Initializer;
import ua.gram.model.Resetable;
import ua.gram.model.enums.Types;
import ua.gram.model.level.FinalLevel;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Boss extends Actor implements Initializer, Resetable {

    protected final DDGame game;
    protected final BossStateManager stateManager;
    protected final BossStateHolder stateHolder;
    protected final BossPrototype prototype;
    protected final Counters counters;
    protected Skeleton skeleton;
    protected FinalLevel level;
    private boolean isDefeated;

    public Boss(DDGame game, BossPrototype prototype, BossStateManager manager) {
        this.stateManager = manager;
        this.game = game;
        this.prototype = prototype;

        stateHolder = new BossStateHolder();

        counters = new Counters();

        resetObject();

        Log.info(getClass().getSimpleName() + " is OK");
    }

    @Override
    public void init() {
        stateManager.swapLevel1State(this, stateManager.getActiveState());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG);
        if (skeleton != null) {
            try {
                stateManager.update(this, delta);
            } catch (GdxRuntimeException e) {
                Log.exc("Could not update boss state", e);
            }
            AnimationState state = level.getBossAnimationManager().getSkeletonState();
            state.update(delta);
            state.apply(skeleton);
            skeleton.updateWorldTransform();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (skeleton != null) {
            level.getBossAnimationManager()
                    .getSkeletonRenderer().draw(batch, skeleton);
            if (DDGame.DEBUG) {
                game.getInfo().draw(batch, getX() + ":" + getY(),
                        getX() + getWidth(), getY());
                game.getInfo().draw(batch, stateHolder.getCurrentLevel1State() + "",
                        getX() + getWidth(), getY() + 12);
                game.getInfo().draw(batch, stateHolder.getCurrentLevel2State() + "",
                        getX() + getWidth(), getY() + 24);
                game.getInfo().draw(batch, skeleton.getX() + ":" + skeleton.getY(),
                        getX() + getWidth(), getY() + 36);
                game.getInfo().draw(batch, skeleton.getData().getWidth() + ":" + skeleton.getData().getHeight(),
                        getX() + getWidth(), getY() + 48);
            }
        }
    }

    public BossStateManager getStateManager() {
        return stateManager;
    }

    public BossStateHolder getStateHolder() {
        return stateHolder;
    }

    public BossPrototype getPrototype() {
        return prototype;
    }

    public FinalLevel getLevel() {
        return level;
    }

    public void setLevel(FinalLevel level) {
        this.level = level;

        if (skeleton == null)
            createSkeleton();
    }

    private void createSkeleton() {
        if (level == null)
            throw new NullPointerException("Could not create skeleton without level");

        skeleton = level.getBossAnimationManager().getSkeleton();
        float scale = level.getBossAnimationManager().getScale();

        skeleton.setPosition(prototype.position.x + 95, prototype.position.y + 25);
        setBounds(prototype.position.x, prototype.position.y,
                skeleton.getData().getWidth() * scale,
                skeleton.getData().getHeight() * scale);
    }

    public Counters getCounters() {
        return counters;
    }

    @Override
    public void resetObject() {
        counters.resetObject();
        isDefeated = false;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public void setDefeated(boolean defeated) {
        this.isDefeated = defeated;
    }

    public void updateAnimation(Types.BossState state, boolean loop) {
        if (state == null) {
            Log.warn("Could not update " + this + " animation of NULL type");
            return;
        }

        level.getBossAnimationManager()
                .getSkeletonState()
                .setAnimation(0, state.name(), loop);
    }
}
