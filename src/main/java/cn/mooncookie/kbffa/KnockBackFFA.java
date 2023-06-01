package cn.mooncookie.kbffa;

import cn.mooncookie.kbffa.BaseListener.BlockProtect;
import cn.mooncookie.kbffa.BaseListener.ChatFormat;
import cn.mooncookie.kbffa.BaseListener.NoMobSpawn;
import cn.mooncookie.kbffa.BaseListener.StopWeatherChange;
import cn.mooncookie.kbffa.Game.Listener.*;
import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.ScoreBoard.RefreshScoreBoard;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

//你说得对，我懒得建Repo，下次一定。

public class KnockBackFFA extends JavaPlugin implements Listener {
    public static File playerDataFile;
    private static KnockBackFFA instance;
    private BlockClearListener Clearlistener;
    private MapChangeListener mapChangeListener;

    public static KnockBackFFA getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = getServer().getPluginManager();
        Clearlistener = new BlockClearListener();
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        PlayerKillDeathListener playerKillDeathListener = new PlayerKillDeathListener(playerDataFile);
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已启用");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        new RefreshScoreBoard(this).runTaskTimer(this, 0, 20);

        //Command
        getCommand("changemap").setExecutor(new MapChangeListener(this));

        //Listener
        pluginManager.registerEvents(Clearlistener, this);
        pluginManager.registerEvents(playerKillDeathListener, this);
        pluginManager.registerEvents(new BlockClearListener(), this);
        pluginManager.registerEvents(new MapChangeListener(this), this);
        pluginManager.registerEvents(new ChatFormat(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new ItemsListener(), this);
        pluginManager.registerEvents(new PlayerJoinLeave(), this);
        pluginManager.registerEvents(new BlockProtect(), this);
        pluginManager.registerEvents(new NoMobSpawn(), this);
        pluginManager.registerEvents(new StopWeatherChange(), this);
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        new YamlConfiguration();
        if (!playerDataFile.exists()) {
            saveResource("playerdata.yml", false);
        }
        YamlConfiguration.loadConfiguration(playerDataFile);
    }

    @Override
    public void onDisable() {
        Clearlistener.clearBlocks();
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已关闭");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
    }
}

