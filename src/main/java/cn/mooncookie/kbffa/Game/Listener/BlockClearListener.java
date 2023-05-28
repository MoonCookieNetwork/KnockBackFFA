package cn.mooncookie.kbffa.Game.Listener;

/***********************
 *   @Author: Rain
 *   @Date: 2023/5/29
 * **********************
 */
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
    private Map<String, List<Block>> playerBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!playerBlocks.containsKey(player.getName())) {
            playerBlocks.put(player.getName(), new ArrayList<>());
        }

        playerBlocks.get(player.getName()).add(block);
    }

    public void clearBlocks() {
        for (List<Block> blocks : playerBlocks.values()) {
            for (Block block : blocks) {
                block.setType(Material.AIR);
            }
        }
    }
}