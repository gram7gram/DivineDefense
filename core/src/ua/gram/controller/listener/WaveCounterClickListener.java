package ua.gram.controller.listener;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.Level;
import ua.gram.model.actor.misc.CounterButton;
import ua.gram.model.actor.misc.PopupLabel;
import ua.gram.view.screen.ErrorScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WaveCounterClickListener extends ClickListener {

    private final DDGame game;
    private final CounterButton button;

    public WaveCounterClickListener(DDGame game, CounterButton button) {
        this.game = game;
        this.button = button;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Log.info("Countdown interrupted");
        Level level = button.getLevel();
        Animation animation = button.getAnimation();
        try {
            if (!animation.isAnimationFinished(button.getStateTime())) {
                rewardPlayer();
            } else {
                Log.info("No reward for player");
            }
            level.nextWave();
            button.reset();
        } catch (Exception e) {
            game.setScreen(new ErrorScreen(game, "Inappropriate wave "
                    + level.getCurrentWaveIndex()
                    + " in level " + level.getCurrentLevel(), e));
        }
    }

    private void rewardPlayer() {
        Animation animation = button.getAnimation();
        float value = 10 * (1 - (animation.getAnimationDuration() / button.getCounter()));
        int reward = Math.abs((int) value);

        if (reward > 0) {
            Log.info("Player receives " + reward + " coins as reward");
            game.getPlayer().addCoins(reward);
            new PopupLabel("+" + reward, game.getResources().getSkin(), "smallpopupwhite", button);
        }
    }
}
