package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.KnockBackFFA;
import cn.mooncookie.kbffa.LPRankProvider;
import cn.mooncookie.kbffa.ScoreBoard.LoadingScoreBoard;
import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PlayerJoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 1, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, -100, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, -100, false));
        LoadingScoreBoard.updateScoreboard(player);
        player.setGameMode(GameMode.ADVENTURE);

        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> {
            if (player.getLocation().getWorld().getName().equals("world")) {
                MapChangeListener.currentMap.teleport(player);
            }
            addPlayer(player);
        }, 60);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String name = player.getDisplayName();
        String NonPermissionQuitFormat = "§7[§c-§7] " + LPRankProvider.getPrefix(e.getPlayer()) + name + LPRankProvider.getSuffix(e.getPlayer());
        e.setQuitMessage(NonPermissionQuitFormat);
        player.removeMetadata("lastBowTime", KnockBackFFA.getInstance());
        player.removeMetadata("lastJumpPadTime", KnockBackFFA.getInstance());
    }

    private void addPlayer(Player player) {
        String name = player.getDisplayName();
        String JoinFormat = LPRankProvider.getPrefix(player) + name + LPRankProvider.getSuffix(player) + "§6进入击退战场！";
        String NonPermissionJoinFormat = "§7[§a+§7] " + LPRankProvider.getPrefix(player) + name + LPRankProvider.getSuffix(player);

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> GenShinImpact.giveItems(player), 1);
        player.getActivePotionEffects().clear();
        ScoreBoard.updateScoreboard(player);

        if (!isVanished(player)) {
            if (player.hasPermission("moclobby.joinmessage")) {
                Bukkit.broadcastMessage(JoinFormat);
            } else {
                Bukkit.broadcastMessage(NonPermissionJoinFormat);
            }
        }
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
