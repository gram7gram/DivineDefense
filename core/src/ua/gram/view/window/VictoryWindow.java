package ua.gram.view.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * TODO Add Stars.
 * TODO Add reward for level
 *
 * NOTE Add social integration?
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends Window {

    private final Sprite victoryBanner;
    private final Texture star;

    public VictoryWindow(final DDGame game) {
        super("", game.getResources().getSkin(), "default");
        Skin skin = game.getResources().getSkin();
        float originX = (DDGame.WORLD_WIDTH - this.getWidth()) / 2f;
        float originY = (DDGame.WORLD_HEIGHT - this.getHeight()) / 2f;
        this.setSize(400, 300);
        this.setPosition(originX, originY);
        this.setVisible(true);
        this.setMovable(false);

        victoryBanner = new Sprite(skin.getRegion("banner-victory"));
        victoryBanner.setSize(500, 250);
        victoryBanner.setPosition(originX - 50, originY + 250);

        star = game.getResources().getSkin().getRegion("star_big").getTexture();

        Button nextLevel = new TextButton("NEXT LEVEL", skin, "pretty-button");
        nextLevel.setSize(200, 80);
        nextLevel.setVisible(true);
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getPlayer().nextLevel();
                Gdx.app.log("INFO", "Switching to level " + game.getPlayer().getLevel());
                setVisible(false);
                DDGame.PAUSE = false;
                game.setScreen(new LevelLoadingScreen(game, game.getPlayer().getLevel()));
            }
        });

        Label reward = new Label("Reward: +2", skin, "default");
        reward.setPosition(55, 95);
        reward.setVisible(true);

        this.add(nextLevel);
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(star, 45, 140);
        batch.draw(star, 150, 140);
        batch.draw(star, 255, 140);
        victoryBanner.draw(batch);
    }
}
