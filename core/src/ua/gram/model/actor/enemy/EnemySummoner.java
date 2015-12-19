package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemySummoner extends AbilityUser implements Cloneable {

    public EnemySummoner(DDGame game, EnemyPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public synchronized void ability() {
        Vector2 pos = this.getCurrentPosition();
        Vector2 next = this.getCurrentDirection();
        Vector2 position = new Vector2(
                (Math.round(pos.x) - (Math.round(pos.x) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.x,
                (Math.round(pos.y) - (Math.round(pos.y) % DDGame.TILE_HEIGHT)) / DDGame.TILE_HEIGHT + next.y);

//            Vector2 index = this.getCurrentPositionIndex();
//            System.out.println("--------");
//            System.out.println("Current: " + Path.toStringRound(pos));
//            System.out.println("Index: " + Path.toString(index));
//            System.out.println("Direction: " + Path.toString(next));
//            System.out.println("Result: " + Path.toString(position));
//            System.out.println("Check: " +
//                    ((int) (index.x + next.x) == (int) position.x
//                            && (int) (index.y + next.y) == (int) position.y));

        Map map = getSpawner().getLevel().getMap();

        if (!checkSpawnPosition(map, position)) {
            throw new GdxRuntimeException("Cannot spawn child. Requested cell "
                    + Path.toString(position) + " does not contain nessesary property");
        }

        try {
            this.getSpawner().spawnChild(this, "EnemySoldier", position);
            Gdx.app.log("INFO", this + " performs ability");
        } catch (Exception e) {
            Log.exc("Could not execute ability for " + this, e);
        }
    }

    private boolean checkSpawnPosition(Map map, Vector2 pos) {
        TiledMapTileLayer layer = map.getActiveLayer();
        TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x, (int) pos.y);
        if (cell != null) {
            MapProperties prop = cell.getTile().getProperties();
            return prop.containsKey(map.getPrototype().walkableProperty);
        } else {
            return false;
        }
    }

    @Override
    public void update(float delta) {
        this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    @Override
    public EnemySummoner clone() throws CloneNotSupportedException {
        return (EnemySummoner) super.clone();
    }
}
