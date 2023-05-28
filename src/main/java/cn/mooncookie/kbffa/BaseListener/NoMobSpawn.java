package cn.mooncookie.kbffa.BaseListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NoMobSpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }
}
