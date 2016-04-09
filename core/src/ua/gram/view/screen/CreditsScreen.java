package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.gram.DDGame;
import ua.gram.controller.stage.CreditsStage;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CreditsScreen extends AbstractScreen {

    private final CreditsStage stage;
    private final Sprite background;

    public CreditsScreen(DDGame game) {
        super(game);
        stage = new CreditsStage(game);
        WindowPrototype prototype = stage.getPrototype();

        Texture back = game.getResources().getRegisteredTexture(prototype.background);

        background = new Sprite(back);
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);

        Log.info("CreditsScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 165 / 255f, 40 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (background.getTexture() != null) {
            stage.getBatch().begin();
            background.draw(stage.getBatch());
            stage.getBatch().end();
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderOtherElements(float delta) {

    }
}
