package cn.mooncookie.kbffa.Game.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamegeListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        e.setDamage(0);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void toDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getDamage() <= 0) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (e.getFinalDamage() < player.getHealth() && !e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            return;
        }
        e.setCancelled(true);
        e.setDamage(0);
        player.closeInventory();
        PlayerDeathEvent deathEvent = new PlayerDeathEvent(player, null, 0, null);
        Bukkit.getPluginManager().callEvent(deathEvent);
    }
}
