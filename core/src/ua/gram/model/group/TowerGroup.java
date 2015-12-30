package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;
import ua.gram.model.actor.misc.ProgressBar;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends ActorGroup<Tower> {

    private final Actor towerBack;
    private final Weapon weapon;
    private final DDGame game;

    public TowerGroup(DDGame game, Tower tower) {
        super(tower);
        this.game = game;
        towerBack = new Actor();
        this.addActor(towerBack);
        this.addActor(tower);//NOTE Tower should have a parent, before getting a weapon
        this.weapon = tower.getWeapon();
        weapon.setVisible(false);
        this.addActor(weapon);
        this.addActor(new ProgressBar(game.getResources().getSkin(), tower));
        tower.setZIndex(1);
        Gdx.app.log("INFO", "Group for " + tower + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && DDGame.DEBUG) {
            game.getInfo().draw(batch, this.getParent().getZIndex() + "",
                    root.getX() - 8,
                    root.getY() + root.getHeight());
        }
    }

    public Actor getTowerBack() {
        return towerBack;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
