package ua.gram.view.stage.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * <pre>
 * TODO Add Stars.
 *
 * NOTE Add social integration?
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends Window {

    public VictoryWindow(final DDGame game) {
        super("", game.getSkin(), "menu");
        this.setPosition(DDGame.WORLD_WIDTH / 4f, DDGame.WORLD_HEIGHT / 3f);
        this.setSize(DDGame.WORLD_WIDTH / 2f, DDGame.WORLD_HEIGHT / 3f);
        this.setVisible(true);
        this.setMovable(false);

        Button nextLevel = new TextButton("NEXT LEVEL", game.getSkin(), "default");
        nextLevel.setSize(100, 40);
        nextLevel.setVisible(true);
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getPlayer().nextLevel();
                Gdx.app.log("INFO", "Switching to level " + game.getPlayer().getLevel());
                setVisible(false);
                DDGame.PAUSE = false;
                game.setScreen(new LevelLoadingScreen(game, game.getPlayer().getLevel()));
            }
        });

        this.add(nextLevel);
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

}
