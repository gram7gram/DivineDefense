package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.level.Level;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.utils.Log;
import ua.gram.view.screen.ErrorScreen;
import ua.gram.view.screen.LevelLoadingScreen;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class NextLevelClickListener extends ClickListener {

    private final DDGame game;
    private final Level level;

    public NextLevelClickListener(DDGame game, Level level) {
        this.game = game;
        this.level = level;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        DDGame.PAUSE = false;
        if (!level.isLast()) {
            game.getPlayer().incrementLastUnlockedLevel();
            int lvl = game.getPlayer().nextLevel();
            Log.info("Switching to level " + lvl);
            try {
                LevelPrototype prototype = game.getLevelPrototype(lvl);
                game.setScreen(new LevelLoadingScreen(game, prototype));
            } catch (IndexOutOfBoundsException e) {
                game.setScreen(new ErrorScreen(game, "Could not load level configuration", e));
            }
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
