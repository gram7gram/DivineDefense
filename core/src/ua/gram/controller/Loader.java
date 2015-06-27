package ua.gram.controller;

import java.util.ArrayList;

/**
 * DIRTY HACK #1
 * TODO Serialize everything
 *
 * @author Gram <gram7gram@gmail.com>
 */
public final class Loader {

//    private int waves_for_lvl1 = 3;
//    private int waves_for_lvl2 = 5;
//    private int waves_for_lvl3 = 5;
//    private int waves_for_lvl4 = 5;
//    private int waves_for_lvl5 = 7;
//    private int waves_for_lvl6 = 10;

    public static ArrayList<String[]> getWaves(int lvl, int difficulty) {
        switch (lvl) {
            case 1:
                switch (difficulty) {
                    case 1:
                        return level1_diff1();
                    case 2:
                        return level1_diff2();
                    case 3:
                        return level1_diff3();
                }
            case 2:
                switch (difficulty) {
                    case 1:
                        return level2_diff1();
                    case 2:
                        return level2_diff2();
                    case 3:
                        return level2_diff3();
                }
            case 3:
                switch (difficulty) {
                    case 1:
                        return level3_diff1();
                    case 2:
                        return level3_diff2();
                    case 3:
                        return level3_diff3();
                }
            case 4:
                switch (difficulty) {
                    case 1:
                        return level4_diff1();
                    case 2:
                        return level4_diff2();
                    case 3:
                        return level4_diff3();
                }
            case 5:
                switch (difficulty) {
                    case 1:
                        return level5_diff1();
                    case 2:
                        return level5_diff2();
                    case 3:
                        return level5_diff3();
                }
            case 6:
                switch (difficulty) {
                    case 1:
                        return level6_diff1();
                    case 2:
                        return level6_diff2();
                    case 3:
                        return level6_diff3();
                }

        }
        return null;
    }

    private static ArrayList<String[]> level1_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level1_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level1_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level2_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level2_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level2_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level3_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level3_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level3_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level4_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level4_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level4_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level5_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level5_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level5_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level6_diff1() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level6_diff2() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }

    private static ArrayList<String[]> level6_diff3() {
        ArrayList<String[]> waves = new ArrayList<String[]>();
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemyRunner",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier"
        });
        waves.add(new String[]{
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemyRunner",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySoldier",
                "EnemySoldierArmored",
                "EnemySummoner",
                "EnemySoldierArmored",
                "EnemySummoner"
        });
        return waves;
    }


}
