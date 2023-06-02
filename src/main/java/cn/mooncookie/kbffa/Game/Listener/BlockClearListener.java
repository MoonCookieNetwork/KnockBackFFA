package cn.mooncookie.kbffa.Game.Listener;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockClearListener implements Listener {
    private final Map<String, List<Location>> playerBlockLocations = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (!playerBlockLocations.containsKey(player.getName())) {
            playerBlockLocations.put(player.getName(), new ArrayList<>());
        }

        playerBlockLocations.get(player.getName()).add(location);
    }

    public void clearBlocks() {
        for (List<Location> locations : playerBlockLocations.values()) {
            for (Location location : locations) {
                Block block = location.getBlock();
                block.setType(Material.AIR);
            }
        }
    }
}