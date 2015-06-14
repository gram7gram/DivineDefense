package ua.gram.model.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ScreenHeader extends Group {

    private final NinePatchDrawable header;

    public ScreenHeader(DDGame game) {
        this(game, "");
    }

    public ScreenHeader(DDGame game, String title) {
        header = new NinePatchDrawable(
                new NinePatch(game.getResources().getSkin().getRegion("header"), 30, 30, 6, 22));
        Label header = new Label(title, game.getResources().getSkin(), "archery64white");
        header.setPosition((DDGame.WORLD_WIDTH - header.getWidth()) / 2f, DDGame.WORLD_HEIGHT - header.getHeight() - 10);
        this.addActor(header);
        this.setSize(DDGame.WORLD_WIDTH, 80);
        this.setPosition(0, DDGame.WORLD_HEIGHT - 80);
        this.setDebug(DDGame.DEBUG);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        header.draw(batch, 0, DDGame.WORLD_HEIGHT - 80, DDGame.WORLD_WIDTH, 80);
    }
}
