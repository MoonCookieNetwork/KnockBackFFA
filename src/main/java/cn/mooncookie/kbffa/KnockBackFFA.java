package cn.mooncookie.kbffa;


import cn.mooncookie.kbffa.Game.Maps.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KnockBackFFA extends JavaPlugin implements org.bukkit.event.Listener {
    private static KnockBackFFA instance;
    private final Map<UUID, Boolean> CanTrade = new HashMap<>();
    private final Map<UUID, Boolean> CanEnderChest = new HashMap<>();
    private File dataFolder;
    private YamlConfiguration config;

    public static KnockBackFFA getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已启用");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        MapManager.mapLoader();
        MapManager.mapLoader();
        dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        config = new YamlConfiguration();
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
        getLogger().info(ChatColor.GREEN + "插件已关闭");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————M0onCo0kie————————");
    }
}

