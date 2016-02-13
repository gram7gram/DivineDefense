package ua.gram.model.prototype.tower;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import ua.gram.model.prototype.GameActorPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPrototype extends GameActorPrototype {

    public float buildDelay;
    public WeaponPrototype weapon;
    public TowerPropertyPrototype[] properties;

    public TowerPropertyPrototype getFirstLevelProperty() {
        return getProperty(1);
    }

    public TowerPropertyPrototype getProperty(int lvl) {
        Optional<TowerPropertyPrototype> optional = find(prototype -> prototype.towerLevel == lvl);
        return optional.get();
    }

    private Optional<TowerPropertyPrototype> find(Predicate<TowerPropertyPrototype> predicate) {
        Optional<TowerPropertyPrototype> optional = Arrays.asList(properties)
                .stream().filter(predicate).findFirst();
        if (!optional.isPresent())
            throw new NullPointerException("Missing first tower property");
        return optional;
    }
}
