package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.player.Player;

/**
 * Continue playing the levelConfig with additional health.
 *
 * @author gram
 */
public class ContinueListener extends ClickListener {

    private final DDGame game;
    private final int amountHealth;
    private final int amountGems;

    public ContinueListener(DDGame game, int health, int gems) {
        this.game = game;
        this.amountHealth = health == -1 ? Player.DEFAULT_HEALTH : health;
        this.amountGems = gems;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.getPlayer().chargeGems(amountGems);
        game.getPlayer().setHealth(amountHealth);
        game.getSecurity().save();
        DDGame.PAUSE = false;
    }

}
