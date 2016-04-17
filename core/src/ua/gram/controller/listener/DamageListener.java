package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.controller.event.DamageEvent;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.PopupLabel;
import ua.gram.model.state.enemy.EnemyStateHolder;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DamageListener implements EventListener {

    private final Enemy enemy;

    public DamageListener(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof DamageEvent)) return false;

        DamageEvent damageEvent = (DamageEvent) event;


        Log.info(enemy + " receives " + damageEvent.getDamage()
                + " dmg, hp: " + enemy.health);

        String text = "-" + (int) damageEvent.getDamage();

        enemy.addAction(
                Actions.run(new PopupLabel(text, enemy.getSkin(),
                        "smallest-popup-red", enemy)));

        EnemyStateManager manager = enemy.getStateManager();
        EnemyStateHolder holder = enemy.getStateHolder();

        if (enemy.health <= 0 && holder.getCurrentLevel1State() != manager.getDeadState()) {
            manager.swapLevel1State(enemy, manager.getDeadState());
        }

        return true;
    }
}
