package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.view.screen.LevelSelectScreen;
import ua.gram.view.screen.MarketScreen;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuStage extends AbstractStage {

    public MainMenuStage(final DDGame game) {
        super(game);
        this.setDebugAll(DDGame.DEBUG);
        int width = DDGame.WORLD_WIDTH;
        int height = DDGame.WORLD_HEIGHT;
        byte butHeight = 80;
        short butWidth = 300;
        byte gap = 5;
        Skin skin = game.getResources().getSkin();

        Button quitBut = new Button(skin, "quit-button");
        quitBut.setSize(butHeight, butHeight);
        quitBut.setPosition(width - quitBut.getWidth() - gap, height - quitBut.getHeight() - gap);
        quitBut.setVisible(true);
        quitBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        Button continueBut = new TextButton("PLAY", skin, "diablo-red");
        continueBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game, game.getPrototype()));
            }
        });
        Button marketBut = new TextButton("MARKET", skin, "diablo-yellow");
        marketBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MarketScreen(game, game.getPrototype().market));
            }
        });
        Button aboutBut = new TextButton("ABOUT", skin, "diablo-yellow");
        aboutBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO Create AboutScreen
//                game.setScreen(new AboutScreen(game));
            }
        });
        Button settingsBut = new TextButton("SETTINGS", skin, "diablo-yellow");
        settingsBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO Create SettingsScreen
//                game.setScreen(new SettingsScreen(game));
            }
        });

        Table table = new Table(skin);

        table.setDebug(DDGame.DEBUG);
        table.setSize(DDGame.WORLD_WIDTH / 2f, DDGame.WORLD_HEIGHT);

        table.add(continueBut).width(butWidth).padBottom(10).height(butHeight).row();
        table.add(marketBut).width(butWidth).padBottom(10).height(butHeight).row();
        table.add(settingsBut).width(butWidth).padBottom(10).height(butHeight).row();
        table.add(aboutBut).width(butWidth).height(butHeight).row();

        this.addActor(table);
    }

}
