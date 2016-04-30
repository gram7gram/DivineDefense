package ua.gram.controller.state.tower;

import ua.gram.controller.state.tower.level1.Level1State;
import ua.gram.controller.state.tower.level2.Level2State;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStateHolder {

    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Types.TowerLevels currentLevelType;

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

    public Types.TowerLevels getCurrentLevelType() {
        return currentLevelType;
    }

    public void setCurrentLevelType(Types.TowerLevels currentLevelType) {
        this.currentLevelType = currentLevelType;
    }
}
