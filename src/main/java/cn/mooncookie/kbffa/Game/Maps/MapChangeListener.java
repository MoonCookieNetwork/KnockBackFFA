package cn.mooncookie.kbffa.Game.Maps;

import cn.mooncookie.kbffa.Game.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***********************
 *   @Author: Rain
 *   @Date: 2023/5/24
 * **********************
 */

public class MapChangeListener implements CommandExecutor, Listener {

    private final JavaPlugin plugin;
    public static final List<String> worldNames = new ArrayList<>();
    public static int currentMapIndex = 0;
    public static String mapName;
    public static int countdown = 114514;

    public MapChangeListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Collections.addAll(worldNames, "Shield", "Garden", "RedDeath", "Desert", "Woods", "Lime", "Nether", "Colors", "Prismarine", "Beach", "Clay", "Sanic", "Savanna", "Chess");

        if (!new File(plugin.getDataFolder(), "mapdata.yml").exists()) {
            saveMapData();
        }
        loadMapData();

        World defaultWorld = Bukkit.getWorld(worldNames.get(currentMapIndex));
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location newSpawn = defaultWorld.getSpawnLocation();
            player.teleport(newSpawn);
            mapName = defaultWorld.getName();
            player.sendMessage("地图已切换到: " + mapName);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                changeMap();
            }
        }.runTaskTimer(plugin, 0L, 10L);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void loadMapData() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "mapdata.yml"));
        if (config.contains("currentMapIndex")) {
            currentMapIndex = config.getInt("currentMapIndex");
        }
        if (config.contains("mapName")) {
            mapName = config.getString("mapName");
        }
        if (config.contains("countdown")) {
            countdown = config.getInt("countdown");
        }
    }

    public void saveMapData() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("currentMapIndex", currentMapIndex);
        config.set("mapName", mapName);
        config.set("countdown", countdown);
        try {
            config.save(new File(plugin.getDataFolder(), "mapdata.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextMap() {
        currentMapIndex = (currentMapIndex + 1) % worldNames.size();
        World nextWorld = Bukkit.getWorld(worldNames.get(currentMapIndex));
        if (nextWorld.getName().equals("Shield")) {
            currentMapIndex = (currentMapIndex + 1) % worldNames.size();
            nextWorld = Bukkit.getWorld(worldNames.get(currentMapIndex));
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location newSpawn = nextWorld.getSpawnLocation();
            player.teleport(newSpawn);
            mapName = nextWorld.getName();
            player.sendMessage("地图已切换到: " + mapName);
        }
        saveMapData();
    }

    private void changeMap() {
        countdown--;
        if (countdown == 0) {
            nextMap();
            countdown = 114514;
        }
        saveMapData();
    }

    private void openChangeMapGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, "更换地图");
        for (String worldName : worldNames) {
            ItemStack worldButton = new ItemStack(Material.PAPER);
            ItemMeta meta = worldButton.getItemMeta();
            meta.addEnchant(Enchantment.KNOCKBACK,10,true);
            meta.setDisplayName(worldName);
            worldButton.setItemMeta(meta);
            gui.addItem(worldButton);
        }

        ItemStack currentMapButton = new ItemStack(Material.MAP);
        ItemMeta meta = currentMapButton.getItemMeta();
        meta.setDisplayName("当前地图：" + mapName);
        currentMapButton.setItemMeta(meta);
        gui.setItem(18, currentMapButton);
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("更换地图")) {
            return;
        }
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        World nextWorld = Bukkit.getWorld(event.getCurrentItem().getItemMeta().getDisplayName());
        if (nextWorld != null && !nextWorld.getName().equals(mapName)) {
            mapName = nextWorld.getName();
            for (Player p : Bukkit.getOnlinePlayers()) {
                Location newSpawn = nextWorld.getSpawnLocation();
                p.teleport(newSpawn);
                p.sendMessage("地图已切换到： " + mapName);
            }
            saveMapData();
        }
        ItemStack currentMapButton = new ItemStack(Material.MAP);
        ItemMeta meta = currentMapButton.getItemMeta();
        meta.setDisplayName("当前地图：" + mapName);
        currentMapButton.setItemMeta(meta);
        event.getInventory().setItem(18, currentMapButton);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World currentWorld = Bukkit.getWorld(mapName);
        Location spawnLocation = currentWorld.getSpawnLocation();
        player.teleport(spawnLocation);
        player.getInventory().clear();
        Items.giveItem(player);
        saveMapData();
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
       Player player = event.getPlayer();
       World currentWorld = Bukkit.getWorld(mapName);
       Location spawnLocation = currentWorld.getSpawnLocation();
       event.setRespawnLocation(spawnLocation);
       player.getInventory().clear();
       Items.giveItem(player);
       saveMapData();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String s , String[] strings) {
        if (!sender.hasPermission("KnockBackFFA.Command.NextMap")) {
            sender.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("nextmap")) {
            nextMap();
        }
        else if (cmd.getName().equalsIgnoreCase("changemap")) {
            openChangeMapGUI(player);
        }
        return true;
    }
}