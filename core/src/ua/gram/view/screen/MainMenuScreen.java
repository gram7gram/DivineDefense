package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.gram.DDGame;
import ua.gram.controller.stage.MainMenuStage;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuScreen extends AbstractScreen {

    private final MainMenuStage uiStage;
    private final Sprite background;

    public MainMenuScreen(DDGame game) {
        super(game);
        uiStage = new MainMenuStage(game);
        WindowPrototype prototype = uiStage.getPrototype();

        Texture back = game.getResources().getRegisteredTexture(prototype.background);
        background = new Sprite(back);
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);

        Log.info("MainMenuScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(60 / 255f, 165 / 255f, 40 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (background.getTexture() != null) {
            uiStage.getBatch().begin();
            background.draw(uiStage.getBatch());
            uiStage.getBatch().end();
        }
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void renderOtherElements(float delta) {
    }

}
