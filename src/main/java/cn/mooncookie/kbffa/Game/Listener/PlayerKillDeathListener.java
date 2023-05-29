package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.KnockBackFFA;
import cn.mooncookie.kbffa.LPRankProvider;
import org.bukkit.Bukkit;
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
        int killCount = playerData.getInt(playerUUID + ".kills", 0);
        int deathCount = playerData.getInt(playerUUID + ".deaths", 0);
        int pointsCount = playerData.getInt(playerUUID + ".points", 0);
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
            GenShinImpact.giveEnderPearl(killer);
            savePlayerData(killer);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null) {
            Player victim = event.getEntity();
            Player killer = event.getEntity().getKiller();

            victim.setHealth(20);
            victim.setFoodLevel(20);
            victim.getInventory().clear();
            Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> GenShinImpact.giveItems(victim), 1);
            victim.removePotionEffect(PotionEffectType.SPEED);

            int deathCount = deaths.getOrDefault(victim, 0) + 1;
            deaths.put(victim, deathCount);
            savePlayerData(victim);
            Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> victim.spigot().respawn(), 1);
            event.setDeathMessage(LPRankProvider.getPrefixColor(victim) + victim.getDisplayName() + " 被击杀， 击杀者： " + LPRankProvider.getPrefixColor(killer) + killer.getDisplayName());
        }
    }

    private void savePlayerData(Player player) {
        UUID playerUUID = player.getUniqueId();
        playerData.set(playerUUID + ".kills", kills.getOrDefault(player, 0));
        playerData.set(playerUUID + ".deaths", deaths.getOrDefault(player, 0));
        playerData.set(playerUUID + ".points", points.getOrDefault(player, 0));
        try {
            playerData.save(playerDataFile);
        } catch (Exception e) {
            System.out.println(playerUUID + " " + player.getName() + " data saving failed.");
        }
    }
}
