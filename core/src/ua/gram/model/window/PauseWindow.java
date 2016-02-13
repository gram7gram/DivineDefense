package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.Initializer;
import ua.gram.model.stage.StageHolder;
import ua.gram.model.stage.UIStage;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PauseWindow extends Window implements Initializer {

    private final Button continueBut;
    private StageHolder stageHolder;

    public PauseWindow(final DDGame game) {
        super("", game.getResources().getSkin(), "default");

        final byte butHeight = 60;

        this.setPosition(DDGame.WORLD_WIDTH / 4f, DDGame.WORLD_HEIGHT / 3f);
        this.setSize(DDGame.WORLD_WIDTH / 2f, DDGame.WORLD_HEIGHT / 3f);
        this.setVisible(true);
        this.setMovable(false);

        continueBut = new TextButton("C", game.getResources().getSkin(), "default");
        continueBut.setVisible(true);

        Button soundBut = new TextButton("S", game.getResources().getSkin(), "default");
        soundBut.setVisible(true);
        soundBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO Toggle sounds
            }
        });

        Button menuBut = new TextButton("M", game.getResources().getSkin(), "default");
        menuBut.setVisible(true);
        menuBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        this.add(continueBut)
                .width(butHeight * 1.5f)
                .height(butHeight * 1.5f)
                .padLeft(30)
                .expandX().expandY();
        this.add(soundBut)
                .width(butHeight * 1.5f)
                .height(butHeight * 1.5f)
                .expandX().expandY();
        this.add(menuBut)
                .width(butHeight * 1.5f)
                .height(butHeight * 1.5f)
                .padRight(30)
                .expandX().expandY();

        Log.info(this.getClass().getSimpleName() + " is OK");
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void init() {
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DDGame.PAUSE = false;
                UIStage stage = stageHolder.getUiStage();
                stage.toggleWindow(stage.getPauseWindow());
            }
        });

    }
}
