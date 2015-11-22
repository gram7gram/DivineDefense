package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author gram
 */
public class RestartListener extends ClickListener {

    private final int lvl;
    private final DDGame game;

    public RestartListener(DDGame game, int lvl) {
        this.lvl = lvl;
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("INFO", "Level " + lvl + " will now restart");
        game.getPlayer().restoreHealth();
        game.getPlayer().decreaseHealth();
        LevelPrototype prototype = game.getPrototype().levels[lvl - 1];
        game.setScreen(new LevelLoadingScreen(game, prototype));
    }

}
