package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Vector4;
import ua.gram.model.window.WindowGroup;
import ua.gram.view.screen.LevelSelectScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuWindow extends WindowGroup {

    public MainMenuWindow(final DDGame game, Skin skin) {
        super(skin);
        setTitle(new Label("MAIN MENU", skin, "header2altwhite"));
        int butHeight = DDGame.DEFAULT_BUTTON_HEIGHT;
        int butWidth = 300;

        Button continueBut = new TextButton("PLAY", skin, "diablo-red");
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game, game.getPrototype()));
            }
        });
        Button aboutBut = new TextButton("ABOUT", skin, "diablo-yellow");
        aboutBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO Create AboutScreen
//                game.setScreen(new AboutScreen(game));
            }
        });
        Button settingsBut = new TextButton("SETTINGS", skin, "diablo-yellow");
        settingsBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO Create SettingsScreen
//                game.setScreen(new SettingsScreen(game));
            }
        });

        Table table = new Table(skin);

        Table buttons = new Table(skin);
        buttons.add(continueBut).width(butWidth).padBottom(10).height(butHeight).row();
        buttons.add(settingsBut).width(butWidth).padBottom(10).height(butHeight).row();
        buttons.add(aboutBut).width(butWidth).height(butHeight);

        float width = (getWidth() - getLayoutTable().getPadLeft() - getLayoutTable().getPadRight()) / 2;

        table.add(buttons).width(width).center().expand();
        table.add().width(width).center().expand();

        setContentPadding(new Vector4(0));
        setContent(table);
    }
}
