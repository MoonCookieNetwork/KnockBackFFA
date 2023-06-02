package cn.mooncookie.kbffa.Game.Maps;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.Bukkit;
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

import java.io.File;
import java.io.IOException;

/***********************
 *   @Author: Rain
 *   @Date: 2023/5/24
 * **********************
 */
public class MapChangeListener implements Listener, CommandExecutor {

    public static int countdown = 0;
    public static GenShinImpact currentMap;
    private final JavaPlugin plugin;

    public MapChangeListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskLater(plugin, this::init, 20);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void init() {
        for (GenShinImpact map : GenShinImpact.values()) {
            World world = Bukkit.getWorld(map.getName());
            if (world == null) {
                continue;
            }
            map.setWorld(world);
            map.initSpawnLocation();
        }
        loadCurrentMap();
        startCountdown();
    }

    private void loadCurrentMap() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        String name = yaml.getString("current_map", GenShinImpact.SHIELD.name());
        currentMap = GenShinImpact.valueOf(name.toUpperCase());
    }

    private void saveCurrentMap() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set("current_map", currentMap.getName());
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCountdown() {
        countdown = currentMap.getDuration();
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            countdown--;
            if (countdown <= 0) {
                changeMap();
            }
        }, 0L, 20L);
    }

    private void changeMap() {
        currentMap = currentMap.getNext();
        saveCurrentMap();
        for (Player player : Bukkit.getOnlinePlayers()) {
            currentMap.teleport(player);
            ScoreBoard.updateScoreboard(player);
            player.sendMessage("§a地图已切换至" + MapChangeListener.currentMap.getDisplayName() + "。");
        }
        countdown = currentMap.getDuration();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        currentMap.teleport(player);
        GenShinImpact.giveItems(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        currentMap.teleport(player);
        GenShinImpact.giveItems(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§8更换地图")) {
            return;
        }
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        GenShinImpact map = GenShinImpact.getByDisplayName(event.getCurrentItem().getItemMeta().getDisplayName());
        if (map != null && map != currentMap) {
            currentMap = map;
            saveCurrentMap();
            for (Player p : Bukkit.getOnlinePlayers()) {
                currentMap.teleport(p);
                p.sendMessage("§a地图已切换至" + MapChangeListener.currentMap.getDisplayName() + "。");
                ScoreBoard.updateScoreboard(p);
                countdown = currentMap.getDuration();
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("KBFFA.Command.ChangeMap")) {
            sender.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(getMapsInventory());
        return true;
    }

    private Inventory getMapsInventory() {
        Inventory gui = Bukkit.createInventory(null, 27, "§8更换地图");
        for (GenShinImpact map : GenShinImpact.values()) {
            ItemStack worldButton = new ItemStack(Material.PAPER);
            ItemMeta meta = worldButton.getItemMeta();
            meta.addEnchant(Enchantment.KNOCKBACK, 114514, true);
            meta.setDisplayName(map.getDisplayName());
            worldButton.setItemMeta(meta);
            gui.addItem(worldButton);
        }
        return gui;
    }

}