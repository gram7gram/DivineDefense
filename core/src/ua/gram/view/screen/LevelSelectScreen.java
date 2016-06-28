package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.controller.stage.LevelSelectStage;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectScreen extends AbstractScreen {

    private LevelSelectStage stage;

    public LevelSelectScreen(DDGame game) {
        super(game);
        Log.info("LevelSelectScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        LevelPrototype[] prototypes = game.getPrototype().levelConfig.levels;
        for(LevelPrototype proto : prototypes) {
            String previewName = proto.preview.name 
                + "@" + proto.preview.size 
                + "." + proto.preview.format;
            game.getResources().loadTexture(previewName);
        }

        game.getResources().getManager().finishLoading();

        stage = new LevelSelectStage(game, prototypes);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderAlways(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderNoPause(float delta) {

    }

    @Override
    public void hide() {
        super.hide();
        LevelPrototype[] prototypes = game.getPrototype().levelConfig.levels;
        for(LevelPrototype proto : prototypes) {
            
            String previewName = proto.preview.name 
                + "@" + proto.preview.size 
                + "." + proto.preview.format;
            game.getResources().getManager().unload(previewName);
        }
    }
}
