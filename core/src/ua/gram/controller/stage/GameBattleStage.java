package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.controller.listener.ToggleTowerControlsListener;
import ua.gram.model.Level;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.view.group.EnemyGroup;
import ua.gram.view.group.TowerGroup;

import java.util.ArrayList;

/**
 * Contains major game objects, like towers and enemies.
 * TODO For each tile row - own Z-index
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class GameBattleStage extends Stage {

    private final Level level;
    private volatile ArrayList<Group> indexes;

    public GameBattleStage(DDGame game, Level level) {
        super(game.getViewport(), game.getBatch());
        this.level = level;
        indexes = new ArrayList<Group>();
        for (int i = 0; i < DDGame.MAP_HEIGHT; i++) {
            Group group = new Group();
            indexes.add(group);
            this.addActor(group);
        }
        this.setDebugAll(DDGame.DEBUG);
        Gdx.app.log("INFO", indexes.size() + " indexes are OK");
        Gdx.app.log("INFO", "BattleStage is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (level.getStage() == null) {
                level.setStage(this);
                level.createSpawner();
                this.addListener(new ToggleTowerControlsListener(this));
            }
            level.update(delta);
        }
    }

    /**
     * Handles storing of indexes for Towers and Enemies on the map,
     * so that they can be rendered in appropriate order.
     * TODO Remove newGroup from previous group is Z-index is changed. Put into other, according to Z-index.
     *
     * @param newGroup group, which is added on the stage
     */
    public void updateZIndexes(Group newGroup) {
        for (Actor actor : newGroup.getChildren()) {
            if (actor instanceof Enemy || actor instanceof Tower) {
                int index = (int) (DDGame.MAP_HEIGHT - (actor.getY()) / DDGame.TILEHEIGHT) - 1;
                indexes.get(index).addActor(newGroup);
                Gdx.app.log("INFO", actor.getClass().getSimpleName() + " added to " + index + " index");
                break;
            }
        }
        int count = 0;
        for (Group group : indexes) {
            if (group.hasChildren()) ++count;
        }
        Gdx.app.log("INFO", "Stage now has " + count + (count > 1 ? " layers" : " layer"));
//        synchronized (System.err) {
//            System.err.println("INDEXES:");
//            for (int i = 0; i < indexes.size(); i++) {
//                System.err.print("  " + i + (i > 9 ? " :  " : "  :  "));
//                for (Actor actor : indexes.get(i).getChildren()) {
//                    System.err.print(actor.getClass().getSimpleName() + "; ");
//                }
//                System.err.println();
//            }
//            System.err.println("ACTORS ON STAGE: " + this.getActors().size);
//            for (int i = 0; i < this.getActors().size; i++) {
//                Array<Actor> actors = this.getActors();
//                System.err.print("  " + i + (i > 9 ? " :  " : "  :  " + actors.get(i).getClass().getSimpleName()) + "\r\n");
//                if (actors.get(i) instanceof Group) {
//                    for (Actor actor2 : ((Group) actors.get(i)).getChildren()) {
//                        if (actor2 instanceof TowerGroup || actor2 instanceof EnemyGroup) {
//                            for (Actor actor3 : ((Group) actor2).getChildren()) {
//                                System.err.print("      -" + actor3.getClass().getSimpleName() + " Z:" + actor3.getZIndex() + "\r\n");
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * If there is at least one instance of EnemyGroup in corresponding Group, true is returned.
     *
     * @return true - at least one Enemy
     */

    public boolean hasEnemiesOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    return true;
                }
            }
        }
        Gdx.app.log("INFO", "No enemies on map!");
        return false;
    }

    /**
     * If there is at least one instance of TowerGroup in corresponding Group, true is returned.
     *
     * @return true - at least one Tower
     */
    public boolean hasTowersOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    return true;
                }
            }
        }
        Gdx.app.log("INFO", "No towers on map!");
        return false;
    }

    /**
     * Get Enemies directly from stage and corresponding groups.
     *
     * @return enemies on map
     */
    public ArrayList<Enemy> getEnemiesOnMap() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    for (Actor actor2 : ((EnemyGroup) actor).getChildren()) {
                        if (actor2 instanceof Enemy) {
                            enemies.add((Enemy) actor2);
                        }
                    }
                }
            }
        }
        return enemies;
    }

    /**
     * Get Towers directly from stage and corresponding groups.
     *
     * @return towers on map
     */
    public ArrayList<Tower> getTowersOnMap() {
        ArrayList<Tower> towers = new ArrayList<Tower>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    for (Actor actor2 : ((TowerGroup) actor).getChildren()) {
                        if (actor2 instanceof Tower) {
                            towers.add((Tower) actor2);
                        }
                    }
                }
            }
        }
        return towers;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isPositionEmpty(float x, float y) {
//        for (Actor actor : this.getActors()) {
//            if (actor instanceof Group) {
//                for (Actor actor1 : ((Group) actor).getChildren()) {
//                    if (actor1 instanceof TowerGroup) {
//                        for (Actor actor2 : ((TowerGroup) actor1).getChildren()) {
//                            if (actor2 instanceof Tower) {
//                                return (Float.compare(actor2.getX(), x) != 0
//                                        && Float.compare(actor2.getY(), y) != 0);
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return true;
    }


    public ArrayList<Group> getIndexes() {
        return indexes;
    }

    public void updateActorIndex(Enemy enemy) {
        int index = (int) (DDGame.MAP_HEIGHT - (enemy.getY()) / DDGame.TILEHEIGHT) - 1;
        if (index != enemy.getParent().getZIndex()) {
            enemy.getParent().remove();
            indexes.get(index).addActor(enemy.getParent());
        }
    }
}