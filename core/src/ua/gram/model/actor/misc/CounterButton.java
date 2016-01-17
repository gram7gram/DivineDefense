package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.listener.WaveCounterClickListener;
import ua.gram.model.Level;
import ua.gram.model.actor.AnimatedActor;
import ua.gram.model.group.GameUIGroup;
import ua.gram.model.prototype.CounterButtonPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CounterButton extends AnimatedActor<CounterButtonPrototype> {

    private final Level level;
    private float counter = 0;

    public CounterButton(final DDGame game, Level level, CounterButtonPrototype prototype) {
        super(game.getResources().getSkin(), prototype);
        this.level = level;

        this.setVisible(false);
        this.setPosition(
                prototype.tilePosition.x * DDGame.TILE_HEIGHT + 10,
                prototype.tilePosition.y * DDGame.TILE_HEIGHT + 10
        );

        this.addListener(new WaveCounterClickListener(game, this));

        Log.info("Counter button is OK");
    }

    @Override
    protected Animation createAnimation(CounterButtonPrototype prototype) {
        this.setSize(prototype.width, prototype.height);

        TextureRegion region = skin.getRegion(prototype.region);
        if (region == null)
            throw new NullPointerException("Missing region in skin: " + prototype.region);

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
        this.setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            if (!level.isActiveWave() && !level.isFinished()) {
                if (!this.isVisible()) {
                    reset();
                    start(getCounterFrameDuration());
                }
                if (animation.isAnimationFinished(counter)) {
                    Log.info("Countdown finished");
                    showNotification();
                    level.nextWave();
                    this.setVisible(false);
                } else {
                    counter += delta;
                }
            }
        }
    }

    private void showNotification() {
        int index = level.getCurrentWaveIndex();
        if (index > 0) {
            String text = "WAVE " + index;

            if (this.getParent() instanceof GameUIGroup)
                ((GameUIGroup) this.getParent()).showNotification(text);
            else
                Log.warn("Could not display notification \"" + text
                        + "\". Parent is not of the GameUIGroup");
        } else {
            Log.warn("Passed " + index + " wave index to notification. Ignored");
        }
    }

    private float getCounterFrameDuration() {
        int index = level.getCurrentWaveIndex();
        return index == 1 || index < 0 ? 1 : 1 / 2f;
    }

    public void start(float duration) {
        animation.setFrameDuration(duration);
        this.setVisible(true);
        Log.info("Countdown started");
    }

    @Override
    public void reset() {
        super.reset();
        counter = 0;
        this.setVisible(false);
        Log.info("Counter button was reset");
    }

    public Level getLevel() {
        return level;
    }
}
