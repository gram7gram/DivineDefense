DIVINE DEFENSE

[![YOUTUBE: DIVINE DEFENSE progress](https://img.youtube.com/vi/JLvl0AFW3kc/0.jpg)](https://www.youtube.com/watch?v=JLvl0AFW3kc)

![GAME ICON](https://github.com/gram7gram/DivineDefense/blob/master/logo_small.png?raw=true)

#Concept

**Short name**: DD

**Age rating**: 5+

**Genre**: real-time strategy, tower defense

**Description**

Angels and Demons, Rage and Kindness, Good and Evil - there are two eternal fractions, battling over and over in Divine Defense. 

Choose your faction and join the divine battle for world domination! Each has unique weaponry and stats. 

Defend from invasion of the opposite faction, gain game progress, gold and upgrades!

**Platforms**

Primary platform is Android

PC/Mac are secondary

iOS and Web are backup options

**Author**

Dmitriy Bondarchuk (Gram) <gram7gram@gmail.com>

Development page: https://github.com/gram7gram/DivineDefense

**Art** 

Евгений Ф. <yeugenyf@gmail.com>

Dmitriy Bondarchuk (Gram) <gram7gram@gmail.com>

#Setting

**Style**: cartoon

**Art style**: third-person

**Dimension**: 2.5D (pseudo 3D)

![First level. Angel’s POV](https://github.com/gram7gram/DivineDefense/blob/master/art/v1.0/level1_2.jpg?raw=true)

__First level. Angel’s POV__

![Near boss’s chambers. Angel’s POV](https://github.com/gram7gram/DivineDefense/blob/master/art/v1.0/level2.jpg?raw=true)

__Near boss’s chambers. Angel’s POV__

As the game offers two kind of characters, the game has two settings: one for Angels point of view, the other is for Demon POV.  

Let's assume that the player has chosen the Angel's POV. 

Angel's POV is about going down to the Hell. Illustrations for the level has transitions from Heaven to Hell: clouds, mountains, plains, caves, dungeons and boss chambers as a last level.

The game starts from the Heaven gates. 

The last cordon has been breached and dire destiny awaits the Heaven. The player has to, first of all, defend the gates and, if successful, will be allowed to proceed to the next location. With each successful defend, player will move to the Hell and boss's chambers.

Stronger enemies will appear as the Player wins battles. To deal with them Player is provided with the armory. Player has to choose and plan carefully about what to purchase and when to upgrade his weapons. 

For the sake of player, it is possible to purchase different attack strategies for the weapon.

It may happen that the Player faces defeat. Yet worry not, o brave soul! The Player was granted precious gems which will help him during such times! From the beginning, there are several gems in Player’s disposal. Manage them wisely. 

## Mechanics

**Mode**: single-player

**Players’ control**

Control is limited. Mouse or touch is used

**Player’s stats**

Coins, gems, health

**Player’s growth**

Player can improve weapons stats

**Enemies**

The opposite in-game faction

**Levels**

Levels differ in graphics. Level is split in waves

**Defeat condition**

Player’s health equals to zero

**Victory condition**

Level is cleared when all waves are cleared. Game is cleared when all levels are cleared.

**Savings**

Each level the Player has achieved is available for start head-on. The gems are stored too

**Average game time**: 5 hours

## Gameplay

The Player is provided with the battlefield (later level, map) and the predefined start-point. In Player’s disposal are a several hundred coins, dozen precious gems and dozen health. The level is split in dozen of waves, which Player has to fend off to be able to proceed next.

To be able to interact with game world, Player has the ability to use weapons on the map. There is a predefined weaponry available. Tower is a 
materialization of weapon in the Divine Defense.

To build a tower, Player has to spend coins. 

Player cannot build a tower if there are not enough coins for one: either defeat enemies with current arsenal (refers to “Enemy defeat reward”), or wait for the next wave (refers to “Next wave bonus”). 

Each undefeated enemy will damage the Player: decrease Player’s health.

To defeat stronger enemies, Player can upgrade Towers. Player can upgrade Tower if there are enough coins for one.

If Player decides the Tower is not efficient for the current wave, it can be sold and removed from the map. In such case, the Player receives refund for the tower, minus fee.

By clearing all waves in level, the Player can proceed to the next level. Later, the Player can access newly achieved level at will.

If the Player is not successful - Player’s health drops to zero - he will be provided with 2 opportunities: restart the level or continue the wave with “blessing”. Blessings are in several options: continue with full health restored or continue with several restored health.

The game is considered finished when the Player clears last level.



__end of concept__



# Concurrent state-machine (CSM)

Finit, concurrent, stack state-machines are up to you to decide which is the best way.

I have choosen the concurrent one.

```java
package ua.gram.model.state;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface StateInterface<T extends Actor> {

    /**
     * Execute before actual management:
     * load nessessary recources, objects etc.
     */
    void preManage(T actor);

    /**
     * Implement state logic
     */
    void manage(T actor, float delta);

    /**
     * Clean-un and reset state
     */
    void postManage(T actor);
}
```

Basicaly, I have an Actor, which can have several states simultaneously.
As a came across gdx-AI ([gdx-ai]) I was pleased over the fact that we though in the same direction on implementing the state-machine.

Yet, whether my implementation is good is, again, UP TO YOU TO DECIDE.

Each state has:
 
 * smth that should be done before it becames the object's state (*preManage*, example: updating animation)
 
 * smth that shoud be done continiously (*manage*, example: move an Actor)
 
 * smth to do if state is disabled (*postManage*, example: cleanup, unset variables).

So, to manage StateInterface, StateManager is essential:

```java
package ua.gram.model.state;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class StateManager<A extends Actor> {

    protected A actor;

    public abstract void init(A actor);

    public abstract void update(A actor, float delta);

    /**
     * Swap states. Executes StateInterface::postManage on {@param before}
     * and StateInterface::preManage on {@param after}.
     *
     * @param actor the actor which will be managed
     * @param before current state; nullable
     * @param after new state; nullable
     * @param level integer represetation of the state level, aka 1-4
     */
    public abstract void swap(A actor, StateInterface before, StateInterface after, int level);

    /**
     * Save actor-specific state
     * @param actor
     * @param newState
     */
    public abstract void persist(A actor, StateInterface newState, int level) throws NullPointerException;

    /**
     * Reset all states for the Actor
     *
     * @param actor
     */
    public abstract void reset(A actor);

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }

}
```

By dividing states into the class hierarchy it is possible to reduce code-redundancy and improve scalability of the feature. 

Here are 4 levels of states:

1. **Activity level**: active/inactive, dead, finish, spawn

2. **Behaviour level**: idle, walk

3. **Ability level**: ability

4. **Affected level**: stun

And an example of the State:

```java
package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);
        
        enemy.getAnimationProvider().get(enemy).free(enemy);

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();

        enemy.clearActions();
        
        spawner.free(enemy);
        
        group.clear();
        group.remove();
        
        Gdx.app.log("INFO", enemy + " is dead");
    }

    @Override
    public void postManage(Enemy enemy) {
        game.getPlayer().addCoins(enemy.reward);
        float value = new Random().nextFloat();

        //10% chance to get a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Gdx.app.log("INFO", "Player got 1 gem");
        }
    }
}
```

States diagramm:

![states image](https://github.com/gram7gram/DivineDefense/blob/master/misc/states.png?raw=true)

References:

* State patterns: [online book]

* Gdx-AI library: [gdx-ai]

[gdx-ai]: https://github.com/libgdx/gdx-ai

[online book]: http://gameprogrammingpatterns.com/state.html
