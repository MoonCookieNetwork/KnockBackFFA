package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.LPRankProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        Player killer = ((Player) e.getEntity()).getKiller();

        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            e.setDamage(0);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            player.closeInventory();
            PlayerDeathEvent deathEvent = new PlayerDeathEvent(player, null, 0, null);
            Bukkit.getPluginManager().callEvent(deathEvent);
            player.teleport(player.getWorld().getSpawnLocation());
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (player == killer) {
                e.setCancelled(true);
            }
            e.setDamage(0);
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null && !player.equals(killer)) {
            Bukkit.broadcastMessage(LPRankProvider.getPrefix(player) + player.getDisplayName() + " §f被击杀， 击杀者： " + LPRankProvider.getPrefix(killer) + killer.getDisplayName() + "§7。");
        }
    }

    //FastDie
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        if (location.getBlockY() <= 0.0) {
            player.closeInventory();
            PlayerDeathEvent deathEvent = new PlayerDeathEvent(player, null, 0, null);
            Bukkit.getPluginManager().callEvent(deathEvent);
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}