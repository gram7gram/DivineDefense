package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.utils.Log;
import ua.gram.view.screen.ErrorScreen;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author gram
 */
public class RestartClickListener extends ClickListener {

    private final int lvl;
    private final DDGame game;

    public RestartClickListener(DDGame game, int lvl) {
        this.lvl = lvl;
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Log.info("Level " + lvl + " will now restart");
        game.getPlayer().restoreHealth();
        game.getPlayer().decreaseHealth(1);
        game.getSecurity().save();
        try {
            LevelPrototype prototype = game.getLevelPrototype(lvl);
            game.setScreen(new LevelLoadingScreen(game, prototype));
        } catch (IndexOutOfBoundsException e) {
            game.setScreen(new ErrorScreen(game, "Could not load level configuration", e));
        }
    }
}