package ua.gram.view.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.view.screen.MainMenuScreen;

/**
 * <pre>
 * TODO Remove Pause window title
 * TODO Enable/Disable sound
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class PauseWindow extends Window {

    public PauseWindow(final DDGame game, String styleName, final GameUIStage stage_ui) {
        super("", game.getSkin(), styleName);

        final byte butHeight = 60;

        this.setPosition(DDGame.WORLD_WIDTH / 4f, DDGame.WORLD_HEIGHT / 3f);
        this.setSize(DDGame.WORLD_WIDTH / 2f, DDGame.WORLD_HEIGHT / 3f);
        this.setVisible(true);
        this.setMovable(false);

        Button continueBut = new TextButton("C", game.getSkin(), "default");
        continueBut.setVisible(true);
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage_ui.toggleWindow(stage_ui.getPauseWindow());
            }
        });

        Button soundBut = new TextButton("S", game.getSkin(), "default");
        soundBut.setVisible(true);
        soundBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        Button menuBut = new TextButton("M", game.getSkin(), "default");
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

        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

}
