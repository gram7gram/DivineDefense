package ua.gram.controller.animation.boss;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.gram.DDGame;
import ua.gram.desktop.HeadlessDesktop;
import ua.gram.model.player.Player;
import ua.gram.model.prototype.boss.BossPrototype;

import static org.junit.Assert.assertNotNull;


@RunWith(DataProviderRunner.class)
public class BossAnimationManagerTest extends HeadlessDesktop {

    @DataProvider
    public static Object[][] validSkeletonNameProvider() {
        return new Object[][]{
                {"LORD"}
        };
    }

    @Test
    @UseDataProvider("validSkeletonNameProvider")
    public void getSkeleton(String name) {
        BossPrototype prototype = new BossPrototype();
        prototype.name = name;

        game.getResources().loadAtlas("data/spine/bosses/"
                + Player.SYSTEM_FACTION + "/" + name + "/skeleton", true);

        BossAnimationManager manager = new BossAnimationManager(game.getResources(), prototype);

        assertNotNull(manager.getSkeleton());
    }

    @Before
    public void setUp() {
        game.init();
        Player.SYSTEM_FACTION = DDGame.FACTION_1;
        Player.PLAYER_FACTION = DDGame.FACTION_2;
    }

    @After
    public void tearDown() {
        game.dispose();
    }
}
