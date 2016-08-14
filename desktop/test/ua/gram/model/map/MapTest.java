package ua.gram.model.map;

import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.gram.model.prototype.level.MapPrototype;
import ua.gram.test.GameTestCase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class MapTest extends GameTestCase {

    @DataProvider
    public static Object[][] validMapProvider() {
        return new Object[][]{
                {"data/levels/maps/level1@60.tmx", new Vector2(1, 0)},
                {"data/levels/maps/level2@60.tmx", new Vector2(0, 1)},
                {"data/levels/maps/level3@60.tmx", new Vector2(0, 5)},
                {"data/levels/maps/level4@60.tmx", new Vector2(1, 0)},
                {"data/levels/maps/level5@60.tmx", new Vector2(0, 6)},
                {"data/levels/maps/level6@60.tmx", new Vector2(1, 9)},
                {"data/levels/maps/level7@60.tmx", new Vector2(1, 9)},
                {"data/levels/maps/level8@60.tmx", new Vector2(0, 1)},
                {"data/levels/maps/level9@60.tmx", new Vector2(0, 4)},
                {"data/levels/maps/level10@60.tmx", new Vector2(12, 8)}
        };
    }

    @Test
    @UseDataProvider("validMapProvider")
    public void normalizePath(String mapFile, Vector2 spawn) {

        TmxMapLoader loader = new TmxMapLoader(new AbsoluteFileHandleResolver());
        TiledMap tiledMap = loader.load(root + mapFile);

        assertNotNull(tiledMap);

        MapPrototype prototype = new MapPrototype();
        prototype.baseProperty = "base";
        prototype.spawnProperty = "spawn";
        prototype.blockedProperty = "blocked";
        prototype.walkableProperty = "walkable";
        prototype.name = mapFile;

        Map map = new Map(tiledMap, prototype);
        map.init();

        WalkablePath path = map.normalizePath(Vector2.Zero, spawn);

        assertTrue(path.size() > 10);

        for (Vector2 direction : path.getDirections()) {
            assertTrue("X coordinate is not a direction",
                    direction.x == -1 || direction.x == 1 || direction.x == 0);
            assertTrue("Y coordinate is not a direction",
                    direction.y == -1 || direction.y == 1 || direction.y == 0);
        }
    }

}
