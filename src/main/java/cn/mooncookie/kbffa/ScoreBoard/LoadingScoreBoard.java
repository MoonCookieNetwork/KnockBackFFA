package cn.mooncookie.kbffa.ScoreBoard;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class LoadingScoreBoard {

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = new Scoreboard();
        ScoreboardObjective objective = scoreboard.registerObjective("KBFFA", IScoreboardCriteria.b);
        objective.setDisplayName("§6§l击退战场");
        PacketPlayOutScoreboardObjective removeObjective = new PacketPlayOutScoreboardObjective(objective, 1);
        PacketPlayOutScoreboardObjective createObjective = new PacketPlayOutScoreboardObjective(objective, 0);
        PacketPlayOutScoreboardDisplayObjective displayObjective = new PacketPlayOutScoreboardDisplayObjective(1, objective);
        List<PacketPlayOutScoreboardScore> scores = new ArrayList<>();

        scores.add(getScorePacket(scoreboard, objective, ("§7" + ScoreBoard.getDate()), 8));
        scores.add(getScorePacket(scoreboard, objective, "   ", 7));
        scores.add(getScorePacket(scoreboard, objective, ("§c正在加载你的档案"), 6));
        scores.add(getScorePacket(scoreboard, objective, ("§c请稍等片刻..."), 5));
        scores.add(getScorePacket(scoreboard, objective, ("  "), 4));
        scores.add(getScorePacket(scoreboard, objective, ("§c如长时间仍在加载， "), 3));
        scores.add(getScorePacket(scoreboard, objective, ("§c请尝试重新加入服务器。"), 2));
        scores.add(getScorePacket(scoreboard, objective, (" "), 1));
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