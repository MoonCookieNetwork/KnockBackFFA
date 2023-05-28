package cn.mooncookie.kbffa.Game.Maps;

import cn.mooncookie.kbffa.Game.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

/***********************
 *   @Author: Rain
 *   @Date: 2023/5/24
 * **********************
 */
public class MapChangeListener implements CommandExecutor, Listener {
    public static final List<String> worldNames = new ArrayList<>();
    public static int currentMapIndex = 0;
    public static String MapName = null;

    public MapChangeListener(JavaPlugin plugin) {
        Collections.addAll(worldNames, "Shield", "Garden", "RedDeath", "Desert", "Woods", "Lime", "Nether", "Colors", "Prismarine", "Beach", "Clay", "Sanic", "Savanna", "Chess");
        new BukkitRunnable() {
            @Override
            public void run() {
                changeMap();
            }
        }.runTaskTimer(plugin, 0L, 8580);
    }

    private void changeMap() {
        World nextWorld = Bukkit.getWorld(worldNames.get((currentMapIndex + 1) % worldNames.size()));
        currentMapIndex = (currentMapIndex + 1) % worldNames.size();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location newSpawn = nextWorld.getSpawnLocation();
            player.teleport(newSpawn);
            MapName = nextWorld.getName();
            player.sendMessage("地图已切换。");
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World currentWorld = Bukkit.getWorld(worldNames.get(currentMapIndex));
        Location spawnLocation = currentWorld.getSpawnLocation();
        event.setRespawnLocation(spawnLocation);
        player.getInventory().clear();
        Items.giveItem(player);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!sender.hasPermission("KnockBackFFA.Command.NextMap")) {
            sender.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        changeMap();
        return true;
    }
}
