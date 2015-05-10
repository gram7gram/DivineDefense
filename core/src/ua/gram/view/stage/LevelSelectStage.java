package ua.gram.view.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * TODO Add stars
 *
 * @author Gram
 */
public class LevelSelectStage extends Stage {

    protected DDGame game;

    public LevelSelectStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;

        Button level1But = new TextButton("1", game.getSkin(), "default");
        level1But.setVisible(true);
        level1But.setSize(150, 150);
        level1But.setPosition(60, 165);
        level1But.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectLevel(1);
            }
        });

        this.addActor(level1But);
    }

    private void selectLevel(int lvl) {
        game.getPlayer().setLevel(lvl);
        Gdx.app.log("INFO", "Level " + game.getPlayer().getLevel() + " is selected");
        game.setScreen(new LevelLoadingScreen(game, lvl));
    }
}
