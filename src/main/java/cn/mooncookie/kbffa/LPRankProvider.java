package cn.mooncookie.kbffa;

import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LPRankProvider {
    public static String getPrefix(Player p) {
        String prefix = LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData().getPrefix();
        return prefix == null ? "" : ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public static String getPrefixColor(Player p) {
        String PrefixColor = LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData().getPrefix().substring(0, 2);
        return ChatColor.translateAlternateColorCodes('&', PrefixColor);
    }

    public static String getSuffix(Player p) {
        String suffix = LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData().getSuffix();
        return suffix == null ? "" : ChatColor.translateAlternateColorCodes('&', suffix);
    }
}
