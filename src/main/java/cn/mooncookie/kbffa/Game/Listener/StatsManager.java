package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class StatsManager implements Listener {
    public static final HashMap<Player, Integer> kills = new HashMap<>();
    public static final HashMap<Player, Integer> deaths = new HashMap<>();
    public static final HashMap<Player, Integer> points = new HashMap<>();
    private final File playerDataFile;
    YamlConfiguration playerData;

    public StatsManager(File playerDataFile) {
        this.playerDataFile = playerDataFile;
        this.playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int killCount = playerData.getInt(playerUUID + ".Kills", 0);
        int deathCount = playerData.getInt(playerUUID + ".Deaths", 0);
        int pointsCount = playerData.getInt(playerUUID + ".Points", 0);
        kills.put(player, killCount);
        deaths.put(player, deathCount);
        points.put(player, pointsCount);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        savePlayerData(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();
        if (killer != null) {
            int killCount = kills.getOrDefault(killer, 0) + 1;
            kills.put(killer, killCount);
            int pointsCount = points.getOrDefault(killer, 0) + 5;
            points.put(killer, pointsCount);
            ScoreBoard.updateScoreboard(killer);
        } // 击杀者
        if (event.getEntity() != null) {
            int deathCount = deaths.getOrDefault(victim, 0) + 1;
            deaths.put(victim, deathCount);
            if (killer != null) {
                int pointsCount = points.getOrDefault(victim, 0);
                if (!(pointsCount <= 0)) {
                    points.put(victim, pointsCount - 5);
                }
            }
            ScoreBoard.updateScoreboard(victim);
        } // 被击杀者
    }

    public void savePlayerData(Player player) {
        UUID playerUUID = player.getUniqueId();
        playerData.set(playerUUID + ".Kills", kills.getOrDefault(player, 0));
        playerData.set(playerUUID + ".Deaths", deaths.getOrDefault(player, 0));
        playerData.set(playerUUID + ".Points", points.getOrDefault(player, 0));
        try {
            playerData.save(playerDataFile);
        } catch (Exception e) {
            System.out.println(playerUUID + " " + player.getName() + " data saving failed.");
        }
    }

}
