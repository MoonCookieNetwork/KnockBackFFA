package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.KnockBackFFA;
import cn.mooncookie.kbffa.LPRankProvider;
import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static cn.mooncookie.kbffa.Game.Maps.MapChangeListener.*;


public class PlayerJoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        ScoreBoard.updateScoreboard(player);

        World currentWorld = Bukkit.getWorld(String.valueOf(countdown));
        Location spawnLocation = currentWorld.getSpawnLocation();
        player.teleport(spawnLocation);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> GenShinImpact.giveItems(player), 1);
        player.getActivePotionEffects().clear();

        String name = player.getDisplayName();
        String JoinFormat = LPRankProvider.getPrefix(e.getPlayer()) + name + LPRankProvider.getSuffix(e.getPlayer()) + "§6进入击退战场！";
        String NonPermissionJoinFormat = "§7[§a+§7] " + LPRankProvider.getPrefix(e.getPlayer()) + name + LPRankProvider.getSuffix(e.getPlayer());
        if (player.hasPermission("moclobby.joinmessage")) {
            e.setJoinMessage(JoinFormat);
        } else {
            e.setJoinMessage(NonPermissionJoinFormat);
        }
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
}
