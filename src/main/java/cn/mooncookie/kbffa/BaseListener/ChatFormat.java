package cn.mooncookie.kbffa.BaseListener;

import cn.mooncookie.kbffa.Game.Listener.PlayerKillDeathListener;
import cn.mooncookie.kbffa.LPRankProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String name = p.getDisplayName();
        String level = "§a[" + PlayerKillDeathListener.points.get(p) + "§a] ";
        String prefix = LPRankProvider.getPrefix(p);
        String suffix = LPRankProvider.getSuffix(p);
        String msg;
        if (e.getPlayer().hasPermission("MOCChatColor.VIP")) {
            msg = "§f： " + e.getMessage().replaceAll("%", "%%");
        } else {
            msg = "§7： " + e.getMessage().replaceAll("%", "%%");
        }

        e.setFormat(level + prefix + name + suffix + msg);
    }
}
