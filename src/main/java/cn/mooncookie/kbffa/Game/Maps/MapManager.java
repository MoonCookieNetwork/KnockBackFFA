package cn.mooncookie.kbffa.Game.Maps;

import org.bukkit.*;

import static org.bukkit.Bukkit.getServer;

public class MapManager {
    public static String MapName = null;
    public static void MapChanger() {

    }
    public static void mapLoader() {
        getServer().createWorld(new WorldCreator("Long"));
        Bukkit.getWorld("Shield").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Shield").setTime(6000);
        Bukkit.getWorld("Shield").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Shield").setGameRuleValue("doMobSpawning", "false");

        getServer().createWorld(new WorldCreator("Garden"));
        Bukkit.getWorld("Garden").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Garden").setTime(6000);
        Bukkit.getWorld("Garden").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Garden").setGameRuleValue("doMobSpawning", "false");

        getServer().createWorld(new WorldCreator("RedDeath"));
        Bukkit.getWorld("RedDeath").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("RedDeath").setTime(6000);
        Bukkit.getWorld("RedDeath").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("RedDeath").setGameRuleValue("doMobSpawning", "false");

        getServer().createWorld(new WorldCreator("Desert"));
        Bukkit.getWorld("Desert").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Desert").setTime(6000);
        Bukkit.getWorld("Desert").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Desert").setGameRuleValue("doMobSpawning", "false");

        getServer().createWorld(new WorldCreator("Woods"));
        Bukkit.getWorld("Woods").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Woods").setTime(6000);
        Bukkit.getWorld("Woods").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Woods").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Lime").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Lime").setTime(6000);
        Bukkit.getWorld("Lime").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Lime").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Nether").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Nether").setTime(6000);
        Bukkit.getWorld("Nether").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Nether").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Survival").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Survival").setTime(6000);
        Bukkit.getWorld("Survival").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Survival").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Colors").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Colors").setTime(6000);
        Bukkit.getWorld("Colors").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Colors").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Prismarine").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Prismarine").setTime(6000);
        Bukkit.getWorld("Prismarine").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Prismarine").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Beach").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Beach").setTime(6000);
        Bukkit.getWorld("Beach").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Beach").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Clay").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Clay").setTime(6000);
        Bukkit.getWorld("Clay").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Clay").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Sanic").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Sanic").setTime(6000);
        Bukkit.getWorld("Sanic").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Sanic").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Savanna").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Savanna").setTime(6000);
        Bukkit.getWorld("Savanna").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Savanna").setGameRuleValue("doMobSpawning", "false");

        Bukkit.getWorld("Chess").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("Chess").setTime(6000);
        Bukkit.getWorld("Chess").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("Chess").setGameRuleValue("doMobSpawning", "false");

    }

}
