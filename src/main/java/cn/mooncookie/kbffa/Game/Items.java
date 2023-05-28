package cn.mooncookie.kbffa.Game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        BowItemMeta.spigot().setUnbreakable(true);
        BowItem.setItemMeta(BowItemMeta);
        p.getInventory().setItem(2, BowItem);

        ItemStack JumpPad = new ItemStack(Material.GOLD_PLATE, 1);
        ItemMeta JumpPadMeta = JumpPad.getItemMeta();
        JumpPadMeta.setDisplayName("§6击退棒");
        JumpPadMeta.spigot().setUnbreakable(true);
        JumpPad.setItemMeta(BowItemMeta);
        p.getInventory().setItem(4, JumpPad);

        ItemStack SpeedItem = new ItemStack(Material.FEATHER, 1);
        ItemMeta SpeedItemMeta = SpeedItem.getItemMeta();
        SpeedItemMeta.setDisplayName("§b加速");
        JumpPadMeta.spigot().setUnbreakable(true);
        JumpPad.setItemMeta(BowItemMeta);
        p.getInventory().setItem(7, JumpPad);

        ItemStack EnderPeral = new ItemStack(Material.ENDER_PEARL, 8);
        p.getInventory().setItem(7, EnderPeral);
    }
}
