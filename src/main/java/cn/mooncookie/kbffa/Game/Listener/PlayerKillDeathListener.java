package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.KnockBackFFA;
import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PlayerKillDeathListener implements Listener {
    public static final HashMap<Player, Integer> kills = new HashMap<>();
    public static final HashMap<Player, Integer> deaths = new HashMap<>();
    public static final HashMap<Player, Integer> points = new HashMap<>();
    private final File playerDataFile;
    YamlConfiguration playerData;

    public PlayerKillDeathListener(File playerDataFile) {
        this.playerDataFile = playerDataFile;
        this.playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int killCount = playerData.getInt(playerUUID + ".Kills", 0);
        int deathCount = playerData.getInt(playerUUID + ".Deaths", 0);
        int pointsCount = playerData.getInt(playerUUID + ".Points", 0);
        kills.put(player, killCount);
        deaths.put(player, deathCount);
        points.put(player, pointsCount);
        savePlayerData(player);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            int killCount = kills.getOrDefault(killer, 0) + 1;
            kills.put(killer, killCount);
            int pointsCount = points.getOrDefault(killer, 0) + 5;
            points.put(killer, pointsCount);

            ScoreBoard.updateScoreboard(killer);
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
            killer.getInventory().addItem(GenShinImpact.EnderPearl());
            savePlayerData(killer);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null) {
            Player victim = event.getEntity();
            Player killer = event.getEntity().getKiller();

            victim.setGameMode(GameMode.SURVIVAL);
            victim.setHealth(20);
            victim.setFoodLevel(20);
            victim.getInventory().clear();
            Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> GenShinImpact.giveItems(victim), 1);
            Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> victim.spigot().respawn(), 1);
            victim.removePotionEffect(PotionEffectType.SPEED);

            ScoreBoard.updateScoreboard(victim);
            int deathCount = deaths.getOrDefault(victim, 0) + 1;
            deaths.put(victim, deathCount);
            int pointsCount = points.getOrDefault(killer, 0) - 5;
            points.put(killer, pointsCount);
            savePlayerData(victim);
        }
    }

    private void savePlayerData(Player player) {
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
