package ua.gram.model.actor.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Level;
import ua.gram.model.Wave;
import ua.gram.view.screen.ErrorScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CounterButton extends Actor {

    private final DDGame game;
    private final Animation animation;
    private final Level level;
    private TextureRegion currentFrame;
    private float stateTime = 0;
    private float counter = 0;

    public CounterButton(final DDGame game, final Level level, Vector2 position) {
        this.game = game;
        this.level = level;
        int size = 40;
        animation = new Animation(1,
                game.getResources().getSkin().getRegion("button-countdown")
                        .split(size, size)[0]);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INFO", "Countdown interrupted");
//                if (level.getWave().getCountdown() > 0) {
//                    int reward = (int) (animation.getKeyFrameIndex(Gdx.graphics.getDeltaTime()) / animation.getFrameDuration());
//                    if (reward <= 0 && reward >= Wave.countdown)
//                        throw new IllegalArgumentException("Out of bounds reward: " + reward + " coins");
//                    game.getPlayer().addCoins(reward);
//                    //TODO Display profit Label
//                }
                Gdx.app.log("INFO", "Player will recieves some coins as reward");
                try {
                    level.getWave().nextWave();
                    setVisible(false);
                } catch (IndexOutOfBoundsException e) {
                    game.setScreen(new ErrorScreen(game, "Unappropriate wave ["
                            + level.getWave().getCurrentWave()
                            + "] in level " + level.currentLevel, e));
                }
            }
        });
        this.setSize(size, size);
        this.setPosition(
                position.x * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - this.getWidth()) / 2f,
                position.y * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - this.getHeight()) / 2f
        );
        this.setVisible(false);
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Counter button is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (!level.getWave().isStarted
                    && !level.isCleared) {
                counter += delta;
                if (!this.isVisible()) {
                    this.start(Wave.currentWave <= 1 ? 1 : 1 / 2f);
                } else if (animation.isAnimationFinished(counter)) {
                    counter = 0;
                    Gdx.app.log("INFO", "Countdown finished");
                    try {
                        level.getWave().nextWave();
                        this.setVisible(false);
                    } catch (IndexOutOfBoundsException e) {
                        game.setScreen(new ErrorScreen(game, "Unappropriate wave number in level " + level.currentLevel, e));
                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || currentFrame == null) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = animation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY());
    }

    public void start(float duration) {
        animation.setFrameDuration(duration);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.setVisible(true);
        Gdx.app.log("INFO", "Countdown started");
    }
}
