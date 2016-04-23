package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

import ua.gram.DDGame;
import ua.gram.controller.listener.ToggleTowerControlsListener;
import ua.gram.model.Initializer;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.ActorGroup;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.Layer;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.level.Level;
import ua.gram.utils.Log;

/**
 * Contains major game objects, like towers and enemies.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class BattleStage extends AbstractStage implements Initializer {

    private final Level level;
    private final ToggleTowerControlsListener controlsListener;
    private final ArrayList<Layer> indexes;
    private final ArrayList<int[]> towerPositions;
    private final int mapHeight;

    public BattleStage(DDGame game, Level level) {
        super(game);
        this.level = level;
        towerPositions = new ArrayList<int[]>();
        indexes = new ArrayList<Layer>();
        mapHeight = level.getMap().getFirstLayer().getHeight();
        for (int i = 0; i < mapHeight; i++) {
            Layer layer = new Layer();
            indexes.add(layer);
            addActor(layer);
        }
        controlsListener = new ToggleTowerControlsListener();
        Log.info(indexes.size() + " indexes are OK");
        Log.info("BattleStage is OK");
    }

    @Override
    public void init() {
        level.setBattleStage(this);
        level.createSpawner();
        if (stageHolder == null)
            throw new NullPointerException("Missing StageHolder for listener");
        controlsListener.setStageHolder(stageHolder);
        addListener(controlsListener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebugAll(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            level.update(delta);
        }
    }

    /**
     * Handles storing of indexes for Towers and Enemies on the map,
     * so that they can be rendered in appropriate order.
     *
     * @param newGroup group, which is added on the stage
     */
    public void updateZIndexes(ActorGroup newGroup) {
        int index = getActorIndex(newGroup);
        Group group = indexes.get(index);

        if (group != null) {
            group.addActor(newGroup);
            Log.info(newGroup.getClass().getSimpleName() + " added to " + index + " index");
        } else {
            Log.warn("Stage failed to get Group at " + index + " index");
        }

        int count = countLayers();
        Log.info("Stage now has " + count + (count > 1 ? " layers" : " layer") + " with children");
    }

    /**
     * Swaps Enemies between Groups on Stage, that represent Z-indexes.
     *
     * @param enemy enemy and it's parent group to swap
     */
    public void updateActorIndex(ActorGroup enemy) {
        if (enemy.getLayer() != null) {
            int index = getActorIndex(enemy);
            if (index != enemy.getLayer().getZIndex()) {
                try {
                    Group indexGroup = indexes.get(index);
                    if (indexGroup != null) {
                        enemy.remove();
                        indexGroup.addActor(enemy);
                    } else {
                        Log.crit("Could not swap " + enemy.getRootActor() + " between indexes");
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.exc("Stage failed to update indexes", e);
                }
            }
        }
    }

    private int getActorIndex(Actor actor) {
        int index = (mapHeight - Math.round(actor.getY() / DDGame.TILE_HEIGHT) - 1);
        return index > 0 ? index : 0;
    }

    public int countLayers() {
        int count = 0;
        for (Group group : indexes) {
            if (group.hasChildren()) ++count;
        }
        return count;
    }

    /**
     * If there is at least one instance of EnemyGroup in
     * corresponding Group, true is returned.
     *
     * @return true - at least one EnemyState
     */
    public boolean hasEnemiesOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    return true;
                }
            }
        }
        Log.info("No enemies on map!");
        return false;
    }

    /**
     * If there is at least one instance of TowerGroup in corresponding Group, true is returned.
     *
     * @return true - at least one TowerState
     */
    public boolean hasTowersOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    return true;
                }
            }
        }
        Log.info("No towers on map!");
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
     * Get Enemies directly from stage and corresponding groups.
     *
     * @return enemies on map
     */
    public ArrayList<EnemyGroup> getEnemyGroupsOnMap() {
        ArrayList<EnemyGroup> enemies = new ArrayList<EnemyGroup>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    enemies.add((EnemyGroup) actor);
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
    public ArrayList<TowerGroup> getTowersOnMap() {
        ArrayList<TowerGroup> towers = new ArrayList<>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    towers.add((TowerGroup) actor);
                }
            }
        }
        return towers;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isPositionEmpty(float x, float y) {
        for (int[] position : towerPositions) {
            if (position[0] == (int) (x / DDGame.TILE_HEIGHT)
                    && position[1] == (int) (y / DDGame.TILE_HEIGHT)) {
                return false;
            }
        }
        return true;
    }

    public ToggleTowerControlsListener getControlsListener() {
        return controlsListener;
    }

    public void addTowerPosition(Tower tower) {
        towerPositions.add(new int[]{
                (int) (tower.getX() / DDGame.TILE_HEIGHT),
                (int) (tower.getY() / DDGame.TILE_HEIGHT)
        });
    }

    public void removeTowerPosition(Tower tower) {
        towerPositions.remove(new int[]{
                (int) (tower.getX() / DDGame.TILE_HEIGHT),
                (int) (tower.getY() / DDGame.TILE_HEIGHT)
        });
    }

    public Layer putOnLayer(Actor actor, int index) {
        if (indexes.size() <= index) index = indexes.size() - 1;
        actor.remove();
        Layer layer = indexes.get(index);
        layer.addActor(actor);
        return layer;
    }

}