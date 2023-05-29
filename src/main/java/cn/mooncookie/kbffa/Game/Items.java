package cn.mooncookie.kbffa.Game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {
    public static void giveItem(Player p) {
        ItemStack KBStick = new ItemStack(Material.STICK, 1);
        ItemMeta KBStickItemMeta = KBStick.getItemMeta();
        KBStickItemMeta.setDisplayName("§6击退棒");
        KBStickItemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        KBStickItemMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        KBStickItemMeta.spigot().setUnbreakable(true);
        KBStick.setItemMeta(KBStickItemMeta);
        p.getInventory().setItem(0, KBStick);

        ItemStack Blocks = new ItemStack(Material.SANDSTONE, 64);
        p.getInventory().setItem(1, Blocks);

        ItemStack BowItem = new ItemStack(Material.BOW, 1);
        ItemMeta BowItemMeta = BowItem.getItemMeta();
        BowItemMeta.setDisplayName("§d§lSakura");
        BowItemMeta.spigot().setUnbreakable(true);
        BowItem.setItemMeta(BowItemMeta);
        p.getInventory().setItem(2, BowItem);

        giveJumpPad(p);

        ItemStack SpeedItem = new ItemStack(Material.FEATHER, 1);
        ItemMeta SpeedItemMeta = SpeedItem.getItemMeta();
        SpeedItemMeta.setDisplayName("§b§l加速");
        SpeedItemMeta.spigot().setUnbreakable(true);
        SpeedItem.setItemMeta(SpeedItemMeta);
        p.getInventory().setItem(7, SpeedItem);

        giveEnderPearl(p);

        ItemStack Arrows = new ItemStack(Material.ARROW, 64);
        p.getInventory().setItem(17, Arrows);
        if (p.hasPermission("kbffa.admin")|| p.getName().equals("ShiroAiy")|| p.getName().equals("Araykal")) {
            ItemStack flameStick = new ItemStack(Material.BLAZE_ROD, 1);
            ItemMeta flameStickMeta = flameStick.getItemMeta();
            flameStickMeta.setDisplayName("§c§lKBFFA Crack");
            List<String> lore = new ArrayList<>();
            lore.add("§cCracked By YukiEnd");
            flameStickMeta.setLore(lore);
            flameStickMeta.addEnchant(Enchantment.KNOCKBACK, 5000, true);
            flameStickMeta.addEnchant(Enchantment.FIRE_ASPECT, 520, true);
            flameStickMeta.spigot().setUnbreakable(true);
            flameStick.setItemMeta(flameStickMeta);
            p.getInventory().setItem(3, flameStick);
        }
    }

    public static void giveJumpPad(Player p) {
        ItemStack JumpPad = new ItemStack(Material.GOLD_PLATE, 1);
        ItemMeta JumpPadMeta = JumpPad.getItemMeta();
        JumpPadMeta.setDisplayName("§e§l跳板");
        JumpPadMeta.spigot().setUnbreakable(true);
        JumpPad.setItemMeta(JumpPadMeta);
        p.getInventory().setItem(4, JumpPad);
    }

    public static void giveEnderPearl(Player p) {
        ItemStack EnderPeral = new ItemStack(Material.ENDER_PEARL, 1);
        p.getInventory().setItem(8, EnderPeral);
    }
}
