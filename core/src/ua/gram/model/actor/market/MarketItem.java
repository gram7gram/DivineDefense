package ua.gram.model.actor.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import ua.gram.DDGame;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.market.MarketItemPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketItem extends Table {

    byte gap = 10;
    private NinePatchDrawable background;
    private TextureRegion preview;

    public MarketItem(final DDGame game, MarketItemPrototype prototype) {
        Skin skin = game.getResources().getSkin();
        int width = 400;
        int height = 150;
        this.background = new NinePatchDrawable(
                new NinePatch(skin.getRegion(prototype.background),
                        2, 2, 2, 2));
        this.preview = skin.getRegion(prototype.icon);

        TextButton buy = new TextButton(prototype.button, skin, prototype.buttonStyle);
        buy.setVisible(true);
        buy.setTouchable(Touchable.enabled);

        CustomLabel description = new CustomLabel(prototype.description, skin, prototype.descriptionStyle);
        description.setVisible(true);
        description.setWrap(true);

        CustomLabel title = new CustomLabel(prototype.name, skin, prototype.nameStyle);
        title.setVisible(true);
        buy.getLabelCell().padLeft(45);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        this.setSize(width, height);
        this.setVisible(true);
        this.add().width(130 + gap * 2);
        this.add(title).height(30)
                .left().top()
                .padBottom(10)
                .padTop(10)
                .minWidth(this.getWidth() - 130 - gap * 3)
                .expandX();
        this.row();
        this.add().width(130 + gap * 2);
        this.add(description)
                .minWidth(this.getWidth() - 130 - gap * 3)
                .left()
                .padBottom(10)
                .expand();
        this.row();
        this.add().width(130 + gap * 2);
        this.add(buy).height(40)
                .right().bottom()
                .bottom()
                .padBottom(10)
                .padRight(10)
                .width(120).expandX();
        this.row();

        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Market's item [" + prototype.name + "] is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch,
                this.getX(), this.getY(),
                this.getWidth(), this.getHeight());
        batch.draw(preview,
                this.getX() + gap, this.getY() + gap,
                130, 130);
        super.draw(batch, parentAlpha);
    }

}
