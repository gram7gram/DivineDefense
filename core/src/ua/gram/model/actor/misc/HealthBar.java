package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class HealthBar extends Actor {

    private final Enemy enemy;
    private final NinePatchDrawable loadingBar30;
    private final NinePatchDrawable loadingBar60;
    private final NinePatchDrawable loadingBar100;
    private final float maxHealth;
    private final float maxWidth;
    private NinePatchDrawable loadingBar;
    private float currentHealth;

    public HealthBar(Skin skin, Enemy enemy) {
        this.enemy = enemy;
        maxHealth = enemy.health;
        loadingBar100 = new NinePatchDrawable(
                new NinePatch(skin.getRegion("health-bar-100"), 0, 0, 0, 0));
        loadingBar60 = new NinePatchDrawable(
                new NinePatch(skin.getRegion("health-bar-50"), 0, 0, 0, 0));
        loadingBar30 = new NinePatchDrawable(
                new NinePatch(skin.getRegion("health-bar-30"), 0, 0, 0, 0));
        this.setDebug(DDGame.DEBUG);
        this.setTouchable(Touchable.disabled);
        this.setSize(DDGame.TILE_HEIGHT - 10, 3);
        this.setPosition(enemy.getX() + 5, enemy.getY() + enemy.getHeight() + 2);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        maxWidth = this.getWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            this.setPosition(enemy.getX() + 5, enemy.getY() + enemy.getHeight() + 2);
            if (loadingBar == null) {
                loadingBar = loadingBar100;
            } else if (enemy.health / enemy.defaultHealth <= .85f && loadingBar == loadingBar100) {
                loadingBar = loadingBar60;
            } else if (enemy.health / enemy.defaultHealth <= .3f && loadingBar == loadingBar60) {
                loadingBar = loadingBar30;
            }
            currentHealth = enemy.health;
            this.setVisible(currentHealth != maxHealth);
        }
    }

    /**
     * HealthBar is displayed only if the EnemyState's health is not 100%.
     * Color varies on health coeficient.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (this.isVisible() && currentHealth > 0) {
            loadingBar.draw(batch, this.getX(), this.getY(), (currentHealth / maxHealth) * maxWidth, this.getHeight());
        }
    }

    public Enemy getParentEnemy() {
        return enemy;
    }

}
