package cn.mooncookie.kbffa.BaseListener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockProtect implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        e.setCancelled(true);
    }
}
