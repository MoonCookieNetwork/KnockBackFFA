package cn.mooncookie.kbffa.ScoreBoard;

import cn.mooncookie.kbffa.Game.Listener.PlayerKillDeathListener;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LoadingScoreBoard {

    private static String getDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd §8HH:mm");
        return format.format(date);
    }

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = new Scoreboard();
        ScoreboardObjective objective = scoreboard.registerObjective("KBFFA", IScoreboardCriteria.b);
        objective.setDisplayName("§6§l击退战场");
        PacketPlayOutScoreboardObjective removeObjective = new PacketPlayOutScoreboardObjective(objective, 1);
        PacketPlayOutScoreboardObjective createObjective = new PacketPlayOutScoreboardObjective(objective, 0);
        PacketPlayOutScoreboardDisplayObjective displayObjective = new PacketPlayOutScoreboardDisplayObjective(1, objective);
        List<PacketPlayOutScoreboardScore> scores = new ArrayList<>();

        scores.add(getScorePacket(scoreboard, objective, "   ", 8));
        scores.add(getScorePacket(scoreboard, objective, ("§c正在加载你的档案" + PlayerKillDeathListener.kills.get(player)), 7));
        scores.add(getScorePacket(scoreboard, objective, ("§c请稍等片刻..." + PlayerKillDeathListener.deaths.get(player)), 6));
        scores.add(getScorePacket(scoreboard, objective, ("  "), 5));
        scores.add(getScorePacket(scoreboard, objective, ("§c如长时间仍在加载， "), 4));
        scores.add(getScorePacket(scoreboard, objective, ("§c请尝试重新加入服务器。"), 3));
        scores.add(getScorePacket(scoreboard, objective, (" "), 2));
        scores.add(getScorePacket(scoreboard, objective, ("§7#" + LoadingScoreBoard.getDate()), 1));
        scores.add(getScorePacket(scoreboard, objective, ("§emc.mooncookie.top"), 0));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(removeObjective);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(createObjective);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(displayObjective);

        for (PacketPlayOutScoreboardScore packets : scores) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packets);
        }
    }

    private static PacketPlayOutScoreboardScore getScorePacket(Scoreboard scoreboard, ScoreboardObjective objective, String display, int scoreValue) {
        ScoreboardScore score = new ScoreboardScore(scoreboard, objective, display);
        score.setScore(scoreValue);
        return new PacketPlayOutScoreboardScore(score);
    }
}