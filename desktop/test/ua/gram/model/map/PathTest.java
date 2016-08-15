package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import ua.gram.test.GameTestCase;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class PathTest extends GameTestCase {

    @DataProvider
    public static Object[][] getTypeProvider() {
        return new Object[][]{
                {1, 0, Path.Direction.RIGHT},
                {-1, 0, Path.Direction.LEFT},
                {0, 1, Path.Direction.UP},
                {0, -1, Path.Direction.DOWN}
        };
    }

    @DataProvider
    public static Object[][] oppositeProvider() {
        return new Object[][]{
                {Path.EAST, Path.WEST},
                {Path.WEST, Path.EAST},
                {Path.NORTH, Path.SOUTH},
                {Path.SOUTH, Path.NORTH},
        };
    }

    @DataProvider
    public static Object[][] isValidDirectionProvider() {
        return new Object[][]{
                {Path.EAST, true},
                {Path.WEST, true},
                {Path.NORTH, true},
                {Path.EAST, true},
                {new Vector2(1, 2), false},
                {Vector2.Zero, false},
        };
    }

    @DataProvider
    public static Object[][] getVectorProvider() {
        return new Object[][]{
                {Path.Direction.LEFT, Path.WEST},
                {Path.Direction.RIGHT, Path.EAST},
                {Path.Direction.DOWN, Path.SOUTH},
                {Path.Direction.UP, Path.NORTH},
        };
    }

    @DataProvider
    public static Object[][] isZeroProvider() {
        return new Object[][]{
                {0, 0, true},
                {1, 0, false},
                {0, 1, false},
                {1.1f, 1, false},
                {1.0000001f, 1, false},
                {0.0f, 0.0f, true},
                {0.01f, 0.0f, true},
                {0.001f, 0.0f, true},
                {0.000001f, 0.0f, true},
        };
    }

    @Test
    @UseDataProvider("getTypeProvider")
    public void getTypeTest(float x, float y, Path.Direction expected) {
        Path.Direction result = Path.getType(x, y);

        assertEquals("Not an expected result", expected, result);
    }

    @Test
    @UseDataProvider("oppositeProvider")
    public void oppositeTest(Vector2 vector, Vector2 expected) {
        Vector2 result = Path.opposite(vector);

        assertEquals("Not an expected result", expected, result);
    }

    @Test
    @UseDataProvider("getVectorProvider")
    public void getVectorTest(Path.Direction direction, Vector2 expected) {
        Vector2 result = Path.getVector(direction);

        assertEquals("Not an expected result", expected, result);
    }

    @Test
    @UseDataProvider("isZeroProvider")
    public void isZeroTest(float x, float y, boolean expected) {
        Vector2 vector = new Vector2(x, y);

        boolean result = Path.isZero(vector);

        assertEquals("Not an expected result", expected, result);
    }

    @Test
    @UseDataProvider("isValidDirectionProvider")
    public void isValidDirectionTest(Vector2 vector, boolean expected) {

        boolean result = Path.isValidDirection(vector);

        assertEquals("Not an expected result", expected, result);
    }

}
