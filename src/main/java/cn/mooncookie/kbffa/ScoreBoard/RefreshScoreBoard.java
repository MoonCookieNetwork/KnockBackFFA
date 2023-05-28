package cn.mooncookie.kbffa.ScoreBoard;

import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.KnockBackFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class RefreshScoreBoard extends BukkitRunnable {

    KnockBackFFA plugin;

    public RefreshScoreBoard(KnockBackFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoard.updateScoreboard(player);
        }
    }
}