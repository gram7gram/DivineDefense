package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ua.gram.DDGame;
import ua.gram.controller.listener.WaveCounterClickListener;
import ua.gram.model.Initializer;
import ua.gram.model.actor.AnimatedActor;
import ua.gram.model.level.Level;
import ua.gram.model.prototype.CounterButtonPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CounterButton extends AnimatedActor<CounterButtonPrototype> implements Initializer {

    private final DDGame game;
    private final Level level;
    private float counter = 0;

    public CounterButton(final DDGame game, Level level, CounterButtonPrototype prototype) {
        super(game.getResources().getSkin(), prototype);
        this.game = game;
        this.level = level;

        setVisible(false);
        setPosition(
                prototype.tilePosition.x * DDGame.TILE_HEIGHT + 10,
                prototype.tilePosition.y * DDGame.TILE_HEIGHT + 10
        );

        Log.info("Counter button is OK");
    }

    @Override
    public void init() {
        addListener(new WaveCounterClickListener(game, this));
    }

    @Override
    protected Animation createAnimation(CounterButtonPrototype prototype) {
        this.setSize(prototype.width, prototype.height);

        TextureRegion region = skin.getRegion(prototype.region);

        TextureRegion[][] regions = region.split(prototype.width, prototype.height);

        if (regions.length == 0 || regions[0].length == 0)
            throw new NullPointerException("Could not create animation");

        Animation animation = new Animation(prototype.frameDuration, regions[0]);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        return animation;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            if (!level.isActiveWave() && !level.isFinished()) {
                if (!isVisible()) {
                    reset();
                    start(getCounterFrameDuration());
                }
                if (animation.isAnimationFinished(counter)) {
                    Log.info("Countdown finished");
                    reset();
                    level.nextWave();
                } else {
                    counter += delta;
                }
            }
        }
    }

    private float getCounterFrameDuration() {
        int index = level.getCurrentWaveIndex();
        return index == 1 || index < 0 ? 1 : 1 / 2f;
    }

    public void start(float duration) {
        animation.setFrameDuration(duration);
        setVisible(true);
        Log.info("Countdown started");
    }

    @Override
    public void reset() {
        super.reset();
        counter = 0;
        setVisible(false);
        Log.info("Counter button was reset");
    }

    public float getCounter() {
        return counter;
    }

    public Level getLevel() {
        return level;
    }

}
