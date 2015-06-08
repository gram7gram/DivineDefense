package ua.gram.model.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ScreenHeader extends Actor {

    private final NinePatchDrawable header;

    public ScreenHeader(DDGame game) {
        this(game, "");
    }

    public ScreenHeader(DDGame game, String title) {
        header = new NinePatchDrawable(
                new NinePatch(game.getResources().getSkin().getRegion("header"), 30, 30, 6, 22));
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
