package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.stage.StageHolder;
import ua.gram.model.Initializer;
import ua.gram.utils.Log;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PauseWindow extends WindowGroup implements Initializer {

    private final Button continueBut;
    private StageHolder stageHolder;

    public PauseWindow(final DDGame game) {
        super(game.getResources().getSkin(), "window-dark-gold");

        Skin skin = game.getResources().getSkin();

        continueBut = new Button(skin, "play-big");
        continueBut.setVisible(true);

        Button menuBut = new Button(skin, "list-big");
        menuBut.setVisible(true);
        menuBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        getContent().add(continueBut)
                .size(DDGame.DEFAULT_BUTTON_HEIGHT)
                .padRight(DDGame.DEFAULT_BUTTON_HEIGHT / 2.5f)
                .right()
                .expand();
        getContent().add(menuBut)
                .size(DDGame.DEFAULT_BUTTON_HEIGHT)
                .padLeft(DDGame.DEFAULT_BUTTON_HEIGHT / 2.5f)
                .left()
                .expand();

        setPosition(DDGame.WORLD_WIDTH / 4f, DDGame.WORLD_HEIGHT / 3f);
        setSize(DDGame.WORLD_WIDTH / 2f, 200);
        setVisible(true);

        setTitle(new Label("PAUSE", skin, "header2altwhite"));

        Log.info(getClass().getSimpleName() + " is OK");
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void init() {
        if (stageHolder == null) throw new NullPointerException("Missing StageHolder");
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageHolder.getUiStage().hidePauseWindow();
                DDGame.resumeGame();
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && stageHolder != null) {
            stageHolder.getUiStage().getTowerControls().hideControls();
        }
    }
}
