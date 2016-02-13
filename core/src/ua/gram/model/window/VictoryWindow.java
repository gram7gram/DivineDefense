package ua.gram.model.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;
import ua.gram.controller.listener.NextLevelClickListener;
import ua.gram.model.Level;

/**
 * TODO Add reward for levelConfig
 * NOTE Add social integration?
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends Window {

    private final Sprite victoryBanner;

    public VictoryWindow(final DDGame game, final Level level) {
        super("", game.getResources().getSkin(), "default");
        Skin skin = game.getResources().getSkin();
        this.setSize(400, 375);
        float originX = (DDGame.WORLD_WIDTH - this.getWidth()) / 2f;
        float originY = (DDGame.WORLD_HEIGHT - this.getHeight()) / 2f;
        this.setPosition(originX, originY);
        this.setVisible(true);
        this.setMovable(false);

        victoryBanner = new Sprite(skin.getRegion("ui/static/banner-victory"));
        victoryBanner.setSize(500, 250);
        victoryBanner.setPosition(originX - 50, originY + 225);

        Button nextLevel = new TextButton("NEXT LEVEL", skin, "pretty-button");
        nextLevel.setSize(250, 80);
        nextLevel.setPosition((DDGame.WORLD_WIDTH - nextLevel.getWidth()) / 2f, this.getY());
        nextLevel.setVisible(true);
        nextLevel.addListener(new NextLevelClickListener(game, level));

        Label reward = new Label("Reward: +2 gems", skin, "header2black");
        reward.setPosition(originX + 55, originY + 95);
        reward.setVisible(true);

        this.add(reward).padTop(170).height(40).fillX().row();
        this.add(nextLevel).fillX().padTop(20).height(80).width(200);
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        victoryBanner.draw(batch);
    }
}
