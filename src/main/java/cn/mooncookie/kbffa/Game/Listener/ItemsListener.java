package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.Items;
import cn.mooncookie.kbffa.KnockBackFFA;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ItemsListener implements Listener {
    private final int featherCd = 10;
    private final int jumpPadCd = 10;
    private final int bowCd = 10;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR))
            return;
        ItemStack i = e.getItem();
        if (e.getItem() == null)
            return;
        if (i.getType() == Material.FEATHER) {
            long lastFeatherTime = 0;
            for (MetadataValue meta : player.getMetadata("lastFeatherTime")) {
                if (meta.getOwningPlugin().equals(KnockBackFFA.getInstance())) {
                    lastFeatherTime = meta.asLong();
                    break;
                }
            }
            long currentTime = System.currentTimeMillis();
            if (lastFeatherTime == 0 || currentTime - lastFeatherTime >= featherCd * 1000L) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.SPEED, 5 * 20, 4, false, true), false);
                player.setMetadata("lastFeatherTime",
                        new FixedMetadataValue(KnockBackFFA.getInstance(), System.currentTimeMillis()));
            } else {
                player.sendMessage("§c请在" + (featherCd - (currentTime - lastFeatherTime) / 1000L) + "秒后再使用！");
                e.setCancelled(true);
            }
        }
        if (i.getType() == Material.BOW) {
            long lastBowTime = 0;
            for (MetadataValue meta : player.getMetadata("lastBowTime")) {
                if (meta.getOwningPlugin().equals(KnockBackFFA.getInstance())) {
                    lastBowTime = meta.asLong();
                    break;
                }
            }
            if (player.getInventory().contains(Material.ARROW) && (lastBowTime == 0 || System.currentTimeMillis() - lastBowTime >= bowCd * 1000L)) {
                player.setMetadata("lastBowTime",
                        new FixedMetadataValue(KnockBackFFA.getInstance(), System.currentTimeMillis()));
               final ItemStack bowItem = i.clone();
                new BukkitRunnable() {
                    int playedTicks = 0;
                    @Override
                    public void run() {
                        playedTicks += 20;
                        if (playedTicks >= bowCd * 20) {
                            Inventory inventory = player.getInventory();
                            int bowSlot = inventory.first(Material.BOW);
                            if (bowSlot >= 0) {
                                ItemStack bowArrow = bowItem.clone();;
                                ItemMeta itemMeta = bowArrow.getItemMeta() ;
                               itemMeta.setDisplayName(bowItem.getItemMeta().getDisplayName());
                                bowArrow.setItemMeta(itemMeta);
                                inventory.setItem(bowSlot , bowArrow);
                            }
                            cancel();
                            return;
                        }
                        int bowSlot = player.getInventory().first(Material.BOW);
                        if (bowSlot >= 0) {
                            e.setCancelled(true);
                            ItemStack bowArrow = bowItem.clone();
                            ItemMeta itemMeta = bowArrow.getItemMeta();
                            itemMeta.setDisplayName("§c" + Integer.toString((bowCd * 20 - playedTicks) / 20));
                            bowArrow.setItemMeta(itemMeta);
                            player.getInventory().setItem(bowSlot , bowArrow);
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(KnockBackFFA.getInstance(), 20L, 20L);
            }
            }
        }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.getBlock().getType() == Material.GOLD_PLATE) {
            long lastJumpPadTime = 0;
            for (MetadataValue meta : player.getMetadata("lastJumpPadTime")) {
                if (meta.getOwningPlugin().equals(KnockBackFFA.getInstance())) {
                    lastJumpPadTime = meta.asLong();
                    break;
                }
            }
            long currentTime = System.currentTimeMillis();
            if (lastJumpPadTime == 0 || currentTime - lastJumpPadTime >= jumpPadCd * 1000L) {
                player.setMetadata("lastJumpPadTime", new FixedMetadataValue(KnockBackFFA.getInstance(), System.currentTimeMillis()));
                Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> Items.giveJumpPad(player), 1);
                Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getBlock().setType(Material.AIR), 5 * 20);
            } else {
                player.sendMessage("§c请在" + (jumpPadCd - (currentTime - lastJumpPadTime) / 1000L) + "秒后再使用！");
                e.setCancelled(true);
            }
            return;
        }
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getPlayer().getInventory().addItem(new ItemStack(e.getPlayer().getItemInHand().getType(), 1, (short) 0, e.getPlayer().getItemInHand().getData().getData())), 1);

        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getBlock().setType(Material.REDSTONE_BLOCK), 3 * 20);
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getBlock().setType(Material.WOOL), 4 * 20);
        Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getBlock().setType(Material.AIR), 5 * 20);
    }

    @EventHandler
    public void onJumpPad(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        if (location.getBlock().getType() == Material.GOLD_PLATE) {
            player.setVelocity(location.getDirection().multiply(1.5).setY(1.5));
            player.playSound(location, Sound.PISTON_EXTEND, 1, 1);
            player.damage(0.01);
        }

    }
}