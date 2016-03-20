package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.Resources;
import ua.gram.controller.stage.LevelSelectStage;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectScreen extends AbstractScreen {

    private final Sprite background;
    private LevelSelectStage stage;

    public LevelSelectScreen(DDGame game, GamePrototype prototype) {
        super(game, prototype);
        background = new Sprite(game.getResources().getRegisteredTexture(Resources.BACKGROUND_TEXTURE));
        Log.info("LevelSelectScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
        LevelPrototype[] prototypes = getPrototype().levelConfig.levels;
        stage = new LevelSelectStage(game, prototypes);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.getBatch().begin();
        background.draw(stage.getBatch());
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderOtherElements(float delta) {

    }
}
