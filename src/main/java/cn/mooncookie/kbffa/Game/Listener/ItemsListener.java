package cn.mooncookie.kbffa.Game.Listener;

import cn.mooncookie.kbffa.Game.GenShinImpact;
import cn.mooncookie.kbffa.KnockBackFFA;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
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

public class ItemsListener implements Listener {
    private final int featherCd = 10;
    private final int jumpPadCd = 10;
    private final int bowCd = 10;

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack i = e.getItem();
        if (e.getItem() == null)
            return;
        if (i.getType() == Material.BLAZE_ROD && e.getAction() == Action.LEFT_CLICK_AIR || i.getType() == Material.BLAZE_ROD && e.getAction() == Action.LEFT_CLICK_BLOCK) {
            player.setVelocity(player.getLocation().getDirection().multiply(10.5).setY(10.5));
            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
            player.damage(0);
            return;
        }
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR))
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

                final ItemStack FeatherItem = player.getItemInHand().clone();
                new BukkitRunnable() {
                    int playedTicks = 0;

                    @Override
                    public void run() {
                        playedTicks += 20;
                        if (playedTicks >= bowCd * 20) {
                            Inventory inventory = player.getInventory();
                            int Feather = inventory.first(Material.FEATHER);
                            if (Feather >= 0) {
                                ItemStack Featheris = FeatherItem.clone();
                                ItemMeta itemMeta = Featheris.getItemMeta();
                                itemMeta.setDisplayName(FeatherItem.getItemMeta().getDisplayName());
                                itemMeta.removeEnchant(Enchantment.KNOCKBACK);
                                Featheris.setItemMeta(itemMeta);
                                inventory.setItem(Feather, Featheris);
                            }
                            cancel();
                            return;
                        }
                        int Feather = player.getInventory().first(Material.FEATHER);
                        if (Feather >= 0) {
                            ItemStack Featheris = FeatherItem.clone();
                            ItemMeta itemMeta = Featheris.getItemMeta();
                            itemMeta.setDisplayName("§b神子技能CD§a(§d" + (featherCd * 20 - playedTicks) / 20 + "§a)");
                            itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                            Featheris.setItemMeta(itemMeta);
                            player.getInventory().setItem(Feather, Featheris);
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(KnockBackFFA.getInstance(), 20L, 20L);
            } else {
                e.setCancelled(true);
                player.sendMessage("§c请等待冷却倒计时结束！");
            }
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
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
            final ItemStack bowItem = player.getItemInHand().clone();
            new BukkitRunnable() {
                int playedTicks = 0;

                @Override
                public void run() {
                    playedTicks += 20;
                    if (playedTicks >= bowCd * 20) {
                        Inventory inventory = player.getInventory();
                        int bowSlot = inventory.first(Material.BOW);
                        if (bowSlot >= 0) {
                            ItemStack bowArrow = bowItem.clone();
                            ItemMeta itemMeta = bowArrow.getItemMeta();
                            itemMeta.setDisplayName(bowItem.getItemMeta().getDisplayName());
                            itemMeta.removeEnchant(Enchantment.KNOCKBACK);
                            bowArrow.setItemMeta(itemMeta);
                            inventory.setItem(bowSlot, bowArrow);
                        }
                        cancel();
                        return;
                    }
                    int bowSlot = player.getInventory().first(Material.BOW);
                    if (bowSlot >= 0) {
                        ItemStack bowArrow = bowItem.clone();
                        ItemMeta itemMeta = bowArrow.getItemMeta();
                        itemMeta.setDisplayName("§d雷电将军技能CD§a(§e" + (bowCd * 20 - playedTicks) / 20 + "§a)");
                        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        bowArrow.setItemMeta(itemMeta);
                        player.getInventory().setItem(bowSlot, bowArrow);
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(KnockBackFFA.getInstance(), 20L, 20L);
        } else {
            e.setCancelled(true);
            player.sendMessage("§c请等待冷却倒计时结束！");
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
                Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> GenShinImpact.giveJumpPad(player), 1);
                Bukkit.getScheduler().runTaskLater(KnockBackFFA.getInstance(), () -> e.getBlock().setType(Material.AIR), 5 * 20);
                final ItemStack JumpPadItem = player.getItemInHand().clone();
                new BukkitRunnable() {
                    int playedTicks = 0;

                    @Override
                    public void run() {
                        playedTicks += 20;
                        if (playedTicks >= bowCd * 20) {
                            Inventory inventory = player.getInventory();
                            int JumpPad = inventory.first(Material.GOLD_PLATE);
                            if (JumpPad >= 0) {
                                ItemStack JumpPadis = JumpPadItem.clone();
                                ItemMeta itemMeta = JumpPadis.getItemMeta();
                                itemMeta.setDisplayName(JumpPadItem.getItemMeta().getDisplayName());
                                itemMeta.removeEnchant(Enchantment.KNOCKBACK);
                                JumpPadis.setItemMeta(itemMeta);
                                inventory.setItem(JumpPad, JumpPadis);
                            }
                            cancel();
                            return;
                        }
                        int JumpPad = player.getInventory().first(Material.GOLD_PLATE);
                        if (JumpPad >= 0) {
                            ItemStack JumpPadis = JumpPadItem.clone();
                            ItemMeta itemMeta = JumpPadis.getItemMeta();
                            itemMeta.setDisplayName("§6旅行者技能CD§a(§c" + (featherCd * 20 - playedTicks) / 20 + "§a)");
                            itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                            JumpPadis.setItemMeta(itemMeta);
                            player.getInventory().setItem(JumpPad, JumpPadis);
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(KnockBackFFA.getInstance(), 20L, 20L);
            } else {
                e.setCancelled(true);
                player.sendMessage("§c请等待冷却倒计时结束！");
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
            player.setVelocity(location.getDirection().multiply(2).setY(2));
            player.playSound(location, Sound.PISTON_EXTEND, 1, 1);
            player.damage(0);
        }
    }
}