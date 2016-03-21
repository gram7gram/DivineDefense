package ua.gram.model.window;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.listener.NextLevelClickListener;
import ua.gram.model.Level;
import ua.gram.model.ResetableInterface;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.ui.window.VictoryWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;

/**
 * TODO Add reward for levelConfig
 * NOTE Add social integration?
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends Window implements ResetableInterface {

    private final Sprite victoryBanner;
    private final CustomLabel reward;
    private final DDGame game;
    private final VictoryWindowPrototype prototype;

    public VictoryWindow(final DDGame game, final Level level, WindowPrototype proto) {
        super("", game.getResources().getSkin(), proto.style);

        if (!(proto instanceof VictoryWindowPrototype))
            throw new IllegalArgumentException("Prototype is not instance of VictoryWindowPrototype");

        this.game = game;
        prototype = (VictoryWindowPrototype) proto;

        Skin skin = game.getResources().getSkin();

        setSize(prototype.width, prototype.height);

        float originX = (DDGame.WORLD_WIDTH - getWidth()) / 2f;
        float originY = (DDGame.WORLD_HEIGHT - getHeight()) / 2f;

        setPosition(originX, originY);
        setVisible(true);
        setMovable(false);

        TextureRegion region = skin.getRegion(prototype.titleRegion);
        if (region == null)
            throw new NullPointerException("Missing texture region: " + prototype.titleRegion);

        victoryBanner = new Sprite(region);
        victoryBanner.setSize(region.getRegionWidth(), region.getRegionHeight());
        victoryBanner.setPosition(originX - 50, originY + region.getRegionHeight() / 2f);

        Button nextLevel = new TextButton(prototype.buttonText, skin, prototype.buttonTextStyle);
        nextLevel.setSize(prototype.buttonWidth, prototype.buttonHeight);
        nextLevel.setVisible(true);
        nextLevel.addListener(new NextLevelClickListener(game, level));

        reward = new CustomLabel("", skin, prototype.rewardTextStyle);
        reward.setVisible(true);
        reward.setAlignment(Align.center);

        add(reward).padTop(170).height(40).fillX().row();
        add(nextLevel).fillX().padTop(20).height(80).width(200);

        resetObject();

        Log.info("VictoryWindow is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        victoryBanner.draw(batch);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible)
            resetObject();
    }

    @Override
    public void resetObject() {
        if (reward != null) {
            int gemsReward = prototype.getReward();
            String text;
            if (gemsReward == 0) {
                text = "Unlucky...\nNo reward for now";
            } else {
                text = "Reward: +" + gemsReward + " gems";
                game.getPlayer().addGems(gemsReward);
            }
            reward.updateText(text);
            Log.info("Reward for victory: " + gemsReward + " gems");
        }
    }
}
