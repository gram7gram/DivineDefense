package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.group.LevelTile;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectStage extends Stage {

    public LevelSelectStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);

        Button back = new Button(game.getResources().getSkin(), "back-button");
        back.setSize(80, 80);
        back.setPosition(DDGame.WORLD_WIDTH - back.getWidth() - 5, DDGame.WORLD_HEIGHT - back.getHeight() - 5);
        back.setVisible(true);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        LevelTile tile1 = new LevelTile(game, 1, 2, false);
        LevelTile tile2 = new LevelTile(game, 2, 3, false);
        LevelTile tile3 = new LevelTile(game, 3, 0, true);

        tile1.setVisible(true);
        tile2.setVisible(true);
        tile3.setVisible(true);

        this.addActor(tile1);
        this.addActor(tile2);
        this.addActor(tile3);
        this.addActor(back);
    }

}
