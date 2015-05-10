package ua.gram.view.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.controller.listener.ToggleTowerControlsListener;
import ua.gram.model.Level;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.view.stage.group.DummyGroup;
import ua.gram.view.stage.group.EnemyGroup;
import ua.gram.view.stage.group.TowerGroup;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Contains major game objects, like towers and enemies.
 * TODO For each tile row - own Z-index
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class GameBattleStage extends Stage {

    private final Level level;
    private final Group enemiesOnMap;
    private final Group towersOnMap;
    private LinkedList<ArrayList<Actor>> indexes;

    public GameBattleStage(DDGame game, Level level) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.level = level;
        enemiesOnMap = new Group();
        towersOnMap = new Group();
        Group dummiesOnMap = new DummyGroup(this);
        this.addActor(dummiesOnMap);
        this.addActor(enemiesOnMap);
        this.addActor(towersOnMap);
        indexes = new LinkedList<ArrayList<Actor>>();
        for (int i = 0; i < DDGame.MAP_HEIGHT; i++) {
            indexes.add(new ArrayList<Actor>(3));
        }
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
//            if (!indexes.isEmpty() && indexes.size() != 1) updateZIndexes();
            level.update(delta);
        }
    }

    /**
     * Handles storing of indexes for Towers and Enemies on the map,
     * so that they can be rendered in appropriate order.
     *
     * @param newActor actor, who is added on the stage
     */
    public void updateZIndexes(Actor newActor) {
        int index = (int) (DDGame.MAP_HEIGHT - (newActor.getY()) / DDGame.TILEHEIGHT) - 1;
        indexes.get(index).add(newActor);
        Gdx.app.log("INFO", newActor.getClass().getSimpleName() + " added to " + index + " index");
        int count = 0;
        for (ArrayList<Actor> list : indexes) {
            if (!list.isEmpty()) ++count;
        }
        Gdx.app.log("INFO", "Stage now has " + count + (count > 1 ? " layers" : " layer"));
    }

    /**
     * If there is at least one instance of EnemyGroup in corresponding Group, true is returned.
     *
     * @return true - at least one Enemy
     */

    public boolean hasEnemiesOnMap() {
        if (!enemiesOnMap.hasChildren()) Gdx.app.log("INFO", "No enemies on map!");
        return enemiesOnMap.hasChildren();
    }

    /**
     * If there is at least one instance of TowerGroup in corresponding Group, true is returned.
     *
     * @return true - at least one Tower
     */
    public boolean hasTowersOnMap() {
        if (!towersOnMap.hasChildren()) Gdx.app.log("INFO", "No towers on map!");
        return towersOnMap.hasChildren();
    }

    /**
     * Get Enemies directly from stage and corresponding groups.
     *
     * @return enemies on map
     */
    public ArrayList<Enemy> getEnemiesOnMap() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (Actor actor : enemiesOnMap.getChildren()) {
            if (actor instanceof EnemyGroup) {
                for (Actor actor2 : ((EnemyGroup) actor).getChildren()) {
                    if (actor2 instanceof Enemy) {
                        enemies.add((Enemy) actor2);
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
        for (Actor actor : towersOnMap.getChildren()) {
            if (actor instanceof TowerGroup) {
                for (Actor actor2 : ((TowerGroup) actor).getChildren()) {
                    if (actor2 instanceof Tower) {
                        towers.add((Tower) actor2);
                    }
                }
            }
        }
        return towers;
    }

    public Level getLevel() {
        return level;
    }

    public Group getEnemies() {
        return enemiesOnMap;
    }

    public Group getTowers() {
        return towersOnMap;
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

    /**
     * Return Z-index of the Actor in the indexes.
     * -1 is returned if there is no actor match. Should not happen!
     *
     * @param actor actor's index to be searched for.
     * @return index of the actor in the indexes.
     */
    public int getIndexByActor(Actor actor) {
        for (ArrayList<Actor> actors : indexes) {
            if (!actors.isEmpty()) {
                for (Actor actor1 : actors) {
                    if (actor == actor1) {
                        return indexes.indexOf(actors);
                    }
                }
            }
        }
        return -1;
    }
}