package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.Items;
import cn.mooncookie.kbffa.LPRankProvider;
import cn.mooncookie.kbffa.ScoreBoard.ScoreBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeave implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.getInventory().clear();
        Items.giveItem(player);
        ScoreBoard.updateScoreboard(player);
        String name = player.getDisplayName();
        String JoinFormat = LPRankProvider.getPrefix(e.getPlayer()) + name + LPRankProvider.getSuffix(e.getPlayer()) + "§6进入击退战场！";
        String NonPermissionJoinFormat = "§7[§a+§7] " + LPRankProvider.getPrefix(e.getPlayer()) + name + LPRankProvider.getSuffix(e.getPlayer());
        MapSpawn.teleportSpawn(player);
        if (player.hasPermission("moclobby.joinmessage")) {
            e.setJoinMessage(JoinFormat);
        } else {
            e.setJoinMessage(NonPermissionJoinFormat);
        }
    }
}
