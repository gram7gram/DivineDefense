package ua.gram.model.strategy.tower;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.AOEWeapon;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.group.EnemyGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AOEStrategy implements TowerStrategy {

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {

        if (victims.size() == 1) return victims;

        Weapon weapon = tower.getWeapon();

        if (!(weapon instanceof AOEWeapon))
            throw new IllegalArgumentException("Non-AOE weapon should not have access to this method");

        return getVictimsInRange(victims, (AOEWeapon) weapon);
    }

    protected List<EnemyGroup> getVictimsInRange(List<EnemyGroup> victims, AOEWeapon weapon) {
        EnemyGroup mainVictim = victims.get(0);

        ArrayList<EnemyGroup> victimInRange = new ArrayList<EnemyGroup>(5);

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
