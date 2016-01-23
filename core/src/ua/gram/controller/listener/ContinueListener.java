package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.stage.GameUIStage;

/**
 * Continue playing the levelConfig with additional health.
 *
 * @author gram
 */
public class ContinueListener extends ClickListener {

    private final DDGame game;
    private final int amountHealth;
    private final int amountGems;
    private final Window window;
    private final GameUIStage stage_ui;

    public ContinueListener(DDGame game, int health, int gems, Window window, GameUIStage stage_ui) {
        this.game = game;
        this.amountHealth = health;
        this.amountGems = gems;
        this.window = window;
        this.stage_ui = stage_ui;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.getPlayer().chargeGems(amountGems);
        game.getPlayer().setHealth(amountHealth);
        game.getSecurity().save();
        DDGame.PAUSE = false;
        stage_ui.toggleWindow(window);
    }

}
