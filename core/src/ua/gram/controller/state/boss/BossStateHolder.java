package ua.gram.controller.state.boss;

import ua.gram.controller.state.boss.level1.Level1State;
import ua.gram.controller.state.boss.level2.Level2State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossStateHolder {
    private Level1State currentLevel1State;
    private Level2State currentLevel2State;

    public Level1State getCurrentLevel1State() {
        return currentLevel1State;
    }

    public void setCurrentLevel1State(Level1State currentLevel1State) {
        this.currentLevel1State = currentLevel1State;
    }

    public Level2State getCurrentLevel2State() {
        return currentLevel2State;
    }

    public void setCurrentLevel2State(Level2State currentLevel2State) {
        this.currentLevel2State = currentLevel2State;
    }
}
