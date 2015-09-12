package ua.gram.model.actor.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.MarketItemPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketItem extends Group {

    private TextureRegion preview;
    byte gap = 10;

    public MarketItem(final DDGame game, MarketItemPrototype prototype, float x, float y) {
        Skin skin = game.getResources().getSkin();
        int width = 360;
        int height = 150;
        this.setWidth(width);
        this.setHeight(height);
//        this.setPosition(x * (width + gap), y * (height + gap));
        this.setVisible(true);
        this.setDebug(DDGame.DEBUG);
        this.preview = skin.getRegion(prototype.icon);

        TextButton buy = new TextButton("Purchase", skin, "blue-button");
        buy.setSize(120, 40);
        buy.setVisible(true);
        buy.setTouchable(Touchable.enabled);
        buy.setPosition(
                this.getX() + this.getWidth() - buy.getWidth() - gap,
                this.getY() + gap + 5
        );
        CustomLabel description = new CustomLabel(prototype.description, skin);
        description.setVisible(true);
        description.setSize(200, 60);
        description.setWrap(true);
        description.setPosition(
                this.getX() + gap + preview.getRegionWidth() + gap,
                this.getY() + gap + buy.getHeight() + gap);
        description.setBounds(description.getX(), description.getY(), 200, 60);

        CustomLabel title = new CustomLabel(prototype.name, skin, "header2black");
        title.setVisible(true);
        title.setPosition(
                this.getX() + gap + preview.getRegionWidth() + gap,
                this.getY() + gap + buy.getHeight() + gap + 60 + gap);

        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        this.addActor(buy);
        this.addActor(title);
        this.addActor(description);
        Gdx.app.log("INFO", "MarketItem is OK at [" + x + ":" + y + "]");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(preview,
                this.getX() + gap, this.getY() + gap,
                preview.getRegionWidth(), preview.getRegionHeight());
    }

}
