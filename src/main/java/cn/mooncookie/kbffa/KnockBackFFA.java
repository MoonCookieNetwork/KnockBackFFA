package cn.mooncookie.kbffa;

import cn.mooncookie.kbffa.BaseListener.BlockProtect;
import cn.mooncookie.kbffa.BaseListener.ChatFormat;
import cn.mooncookie.kbffa.BaseListener.NoMobSpawn;
import cn.mooncookie.kbffa.BaseListener.StopWeatherChange;
import cn.mooncookie.kbffa.Game.Listener.*;
import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.ScoreBoard.RefreshScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

//你说得对，我懒得建Repo，下次一定。

public class KnockBackFFA extends JavaPlugin implements Listener {
    private BlockClearListener Clearlistener;
    public static File playerDataFile;
    private static KnockBackFFA instance;

    public static KnockBackFFA getInstance() {
        return instance;
    }

    private MapChangeListener mapChangeListener;
    @Override
    public void onEnable() {
        instance = this;
        Clearlistener = new BlockClearListener();
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        PlayerKillDeathListener playerKillDeathListener = new PlayerKillDeathListener(playerDataFile);
        getServer().getPluginManager().registerEvents(Clearlistener,this);
        getServer().getPluginManager().registerEvents(playerKillDeathListener, this);
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已启用");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        new RefreshScoreBoard(this).runTaskTimer(this, 0, 20);
        getServer().getPluginManager().registerEvents(new BlockClearListener(),this);
        getServer().getPluginManager().registerEvents(new MapChangeListener(this), this);
        getCommand("nextmap").setExecutor(new MapChangeListener(this));
        getCommand("changemap").setExecutor(new MapChangeListener(this));
        getServer().getPluginManager().registerEvents(new ChatFormat(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new ItemsListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(), this);
        getServer().getPluginManager().registerEvents(new BlockProtect(), this);
        getServer().getPluginManager().registerEvents(new NoMobSpawn(), this);
        getServer().getPluginManager().registerEvents(new StopWeatherChange(), this);
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
        mapChangeListener.saveMapData();
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已关闭");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
    }

}

