package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.window.AbstractWindow;
import ua.gram.view.screen.LevelSelectScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuWindow extends AbstractWindow {

    public MainMenuWindow(final DDGame game, Skin skin) {
        super("Main Menu", skin);
        int butHeight = 80;
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

        table.setSize(getWidth() / 2f, getHeight());

        table.add(continueBut).width(butWidth).padBottom(10).height(butHeight).row();
        table.add(settingsBut).width(butWidth).padBottom(10).height(butHeight).row();
        table.add(aboutBut).width(butWidth).height(butHeight).row();

        addActor(table);
    }
}