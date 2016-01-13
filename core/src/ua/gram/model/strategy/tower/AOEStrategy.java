package ua.gram.model.strategy.tower;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.AOEWeapon;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AOEStrategy implements TowerStrategy {

    private final EnemyHealthComparator healthComparator;

    public AOEStrategy(TowerStrategyManager manager) {
        this.healthComparator = manager.getHealthComparator();
        healthComparator.setType(EnemyHealthComparator.MAX);
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {
        Weapon weapon = tower.getWeapon();

        if (!(weapon instanceof AOEWeapon))
            throw new IllegalArgumentException("Non-AOE weapon should not have access to this method");

        Collections.sort(victims, healthComparator);

        return getVictimsInRange(victims, (AOEWeapon) weapon);
    }

    protected List<EnemyGroup> getVictimsInRange(List<EnemyGroup> victims, AOEWeapon weapon) {
        EnemyGroup mainVictim = victims.get(0);

        ArrayList<EnemyGroup> victimInRange = new ArrayList<>(5);

        for (EnemyGroup victim : victims) {
            if (victim == mainVictim) continue;
            Vector2 victimPosition = victim.getRootActor().getCurrentPosition();
            Vector2 mainVictimPosition = mainVictim.getRootActor().getCurrentPosition();
            float distance = victimPosition.dst(mainVictimPosition);
            if (distance <= weapon.getAOERange()) {
                victimInRange.add(victim);
            }
        }

        return victimInRange;
    }
}
