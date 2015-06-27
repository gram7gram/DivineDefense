package ua.gram.model.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Level;
import ua.gram.view.screen.LevelLoadingScreen;
import ua.gram.view.screen.MainMenuScreen;

/**
 * TODO Add Stars.
 * TODO Add reward for level
 * <p/>
 * NOTE Add social integration?
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends Window {

    private final Sprite victoryBanner;
    private final Sprite star;

    public VictoryWindow(final DDGame game, final Level level) {
        super("", game.getResources().getSkin(), "default");
        Skin skin = game.getResources().getSkin();
        this.setSize(400, 375);
        float originX = (DDGame.WORLD_WIDTH - this.getWidth()) / 2f;
        float originY = (DDGame.WORLD_HEIGHT - this.getHeight()) / 2f;
        this.setPosition(originX, originY);
        this.setVisible(true);
        this.setMovable(false);

        victoryBanner = new Sprite(skin.getRegion("banner-victory"));
        victoryBanner.setSize(500, 250);
        victoryBanner.setPosition(originX - 50, originY + 225);

        star = new Sprite(skin.getRegion("star_big"));

        Button nextLevel = new TextButton("NEXT LEVEL", skin, "pretty-button");
        nextLevel.setSize(250, 80);
        nextLevel.setPosition((DDGame.WORLD_WIDTH - nextLevel.getWidth()) / 2f, this.getY());
        nextLevel.setVisible(true);
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level.isLast()) {
                    game.getPlayer().nextLevel();
                    Gdx.app.log("INFO", "Switching to level " + game.getPlayer().getLevel());
                    setVisible(false);
                    DDGame.PAUSE = false;
                    game.setScreen(new LevelLoadingScreen(game, game.getPlayer().getLevel()));
                } else {
                    game.setScreen(new MainMenuScreen(game));
//                    game.setScreen(new FinalScreen(game));
                }
            }
        });

        Label reward = new Label("Reward: +2 gems", skin, "archery64black");
        reward.setPosition(originX + 55, originY + 95);
        reward.setVisible(true);

        this.add(reward).padTop(170).height(40).fillX().row();
        this.add(nextLevel).fillX().padTop(20).height(80).width(200);
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(star, this.getX() + 45, this.getY() + 190, 100, 100);
        batch.draw(star, this.getX() + 150, this.getY() + 190, 100, 100);
        batch.draw(star, this.getX() + 255, this.getY() + 190, 100, 100);
        victoryBanner.draw(batch);
    }
}
