package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Vector4;
import ua.gram.controller.factory.ScreenFactory;
import ua.gram.model.prototype.ui.ButtonPrototype;
import ua.gram.model.prototype.ui.ScreenButtonPrototype;
import ua.gram.model.prototype.ui.window.MainMenuWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuWindow extends WindowGroup {

    public MainMenuWindow(final DDGame game, Skin skin, WindowPrototype proto) {
        super(skin);

        if (!(proto instanceof MainMenuWindowPrototype))
            throw new IllegalArgumentException("Prototype is not instance of MainMenuWindowPrototype");

        MainMenuWindowPrototype prototype = (MainMenuWindowPrototype) proto;

        setTitle(new Label(prototype.headerText, skin, prototype.headerTextStyle));

        Table table = new Table(skin);

        Table buttons = new Table(skin);

        int length = prototype.actions.length;
        int index = 0;
        for (ButtonPrototype buttonProto : prototype.orderActions()) {

            if (!(buttonProto instanceof ScreenButtonPrototype))
                throw new IllegalArgumentException("Button is not instance of ScreenButtonPrototype");

            final ScreenButtonPrototype buttonPrototype = (ScreenButtonPrototype) buttonProto;

            Button button = new TextButton(buttonPrototype.title, skin, buttonPrototype.style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(ScreenFactory.create(game, buttonPrototype.target));
                }
            });

            if (++index < length) {
                buttons.add(button)
                        .width(buttonPrototype.width)
                        .height(buttonPrototype.height)
                        .padBottom(10).row();
            } else {
                buttons.add(button)
                        .width(buttonPrototype.width)
                        .height(buttonPrototype.height);
            }
        }

        float width = (getWidth() - getLayoutTable().getPadLeft() - getLayoutTable().getPadRight()) / 2;

        table.add(buttons).width(width).center().expand();
        table.add().width(width).center().expand();

        setContentPadding(new Vector4(0));
        setContent(table);
    }
}
