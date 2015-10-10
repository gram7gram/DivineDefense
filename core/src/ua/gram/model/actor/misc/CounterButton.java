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
import ua.gram.model.group.GameUIGroup;
import ua.gram.view.screen.ErrorScreen;

/**
 * TODO Blink this
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class CounterButton extends Actor {

    private final DDGame game;
    private final Level level;
    private Animation animation;
    private TextureRegion currentFrame;
    private float stateTime = 0;
    private float counter = 0;

    public CounterButton(final DDGame game, final Level level, Vector2 position) {
        final CounterButton counterButton = this;
        this.game = game;
        this.level = level;
        this.setSize(40, 40);
        this.setPosition(
                position.x * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - this.getWidth()) / 2f,
                position.y * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - this.getHeight()) / 2f
        );
        this.setVisible(false);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INFO", "Countdown interrupted");
                try {
                    if (!animation.isAnimationFinished(counter)) {
                        int reward = (int) (stateTime / animation.getFrameDuration());
                        if (reward > 0) {
                            Gdx.app.log("INFO", "Player receives " + (reward *= 10) + " coins as reward");
                            game.getPlayer().addCoins(reward);
                            new PopupLabel("+" + reward, game.getResources().getSkin(), "smallpopupwhite", counterButton);
                        }
                    }
                    level.nextWave();
                    counterButton.setVisible(false);
                    stateTime = 0;
                } catch (Exception e) {
                    counterButton.setVisible(false);
                    game.setScreen(new ErrorScreen(game, "Inappropriate wave "
                            + level.getCurrentWave()
                            + " in level " + level.currentLevel, e));
                }
            }
        });
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Counter button is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (!level.isActiveWave() && !level.isFinished()) {
                if (!this.isVisible()) {
                    animation = reset();
                    start(level.getCurrentWave() <= 1 ? 1 : 1 / 2f);
                }
                if (animation.isAnimationFinished(counter)) {
                    Gdx.app.log("INFO", "Countdown finished");
                    ((GameUIGroup) this.getParent()).showNotification("WAVE " + (level.getCurrentWave() + 1));
                    level.nextWave();
                    this.setVisible(false);
                } else {
                    counter += delta;
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

    private Animation reset() {
        int size = 40;
        Animation animation = new Animation(1, game.getResources().getSkin()
                .getRegion("button-countdown")
                .split(size, size)[0]
        );
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.setSize(size, size);
        counter = 0;
        stateTime = 0;
        Gdx.app.log("INFO", "Counter animation was reset");
        return animation;
    }
}
