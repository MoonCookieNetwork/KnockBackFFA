package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import cn.mooncookie.kbffa.KnockBackFFA;
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
            e.setDamage(0);
            return;
        }
        e.setCancelled(true);
    }

    //FastDie
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> {
            if (player.getLocation().getWorld().getName().equals("world")) {
/*                player.kickPlayer("§f在加载你的击退战场数据时发生了一个问题， 请重新加入后再试！");*/
                MapChangeListener.currentMap.teleport(player);
                player.sendMessage("§c检测到你是第一次进入击退战场,已将你传送到当前地图!");
            }
        }, 10);
        if (location.getBlockY() <= 0.0) {
            player.closeInventory();
            PlayerDeathEvent deathEvent = new PlayerDeathEvent(player, null, 0, null);
            Bukkit.getPluginManager().callEvent(deathEvent);
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}