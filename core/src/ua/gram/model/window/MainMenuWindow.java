package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.prototype.ui.window.MainMenuWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.view.screen.CreditsScreen;
import ua.gram.view.screen.LevelSelectScreen;
import ua.gram.view.screen.SettingsScreen;

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

        Button playButton = new TextButton("PLAY", skin, "diablo-red");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        Button settingsButton = new TextButton("SETTINGS", skin, "diablo-yellow");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        Button creditsButton = new TextButton("CREDITS", skin, "diablo-yellow");
        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreditsScreen(game));
            }
        });

        float width = 300;
        float height = 80;

        buttons.add(playButton)
                .width(width)
                .height(height)
                .padBottom(10).row();

        buttons.add(settingsButton)
                .width(width)
                .height(height)
                .padBottom(10).row();

        buttons.add(creditsButton)
                .width(width)
                .height(height)
                .padBottom(10);

        float columnWidth = (getWidth() - getLayoutTable().getPadLeft() - getLayoutTable().getPadRight()) / 2;

        table.add(buttons).width(columnWidth).center().expand();
        table.add().width(columnWidth).center().expand();

        setContent(table);
    }
}
