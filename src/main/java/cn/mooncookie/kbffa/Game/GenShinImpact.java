package cn.mooncookie.kbffa.Game;

import cn.mooncookie.kbffa.Game.Maps.MapChangeListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/***********************
 *   @Author: Rain
 *   @Date: 2023/5/29
 * **********************
 */
public enum GenShinImpact {

    SHIELD("Shield", "§1蒙德", 300),
    GARDEN("Garden", "§2须弥城", 300),
    RED_DEATH("RedDeath", "§6层岩巨渊", 300),
    DESERT("Desert", "§e列柱沙原", 300),
    WOODS("Woods", "§a道成林", 300),
    LIME("Lime", "§d鸣神岛", 300),
    NETHER("Nether", "§d清赖岛", 300),
    COLORS("Colors", "§f阿陀河谷", 300),
    PRISMARINE("Prismarine", "§9神无冢", 300),
    BEACH("Beach", "§b鹤观", 300),
    CLAY("Clay", "§3荒石苍漠", 300),
    SANIC("Sanic", "§8神秘绿洲", 300),
    SAVANNA("Savanna", "§7草原", 300),
    CHESS("Chess", "§6皇宫殿堂", 300);


    private final String name;
    private final String displayName;
    private final int duration;
    private World world;
    private Location spawnLocation;

    GenShinImpact(String name, String displayName, int duration) {
        this.name = name;
        this.displayName = displayName;
        this.duration = duration;
    }


    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDuration() {
        return duration;
    }

    public GenShinImpact getNext() {
        GenShinImpact[] maps = GenShinImpact.values();
        int index = (this.ordinal() + 1) % maps.length;
        if (maps[index] == SHIELD) {
            index = (index + 1) % maps.length;
        }
        return maps[index];
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void initSpawnLocation() {
        spawnLocation = world.getSpawnLocation();
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void teleport(Player player) {
        player.teleport(spawnLocation);
    }

    public static void giveItems(Player player) {
        ItemStack KBStick = new ItemStack(Material.STICK, 1);
        ItemMeta KBStickItemMeta = KBStick.getItemMeta();
        KBStickItemMeta.setDisplayName("§6击退棒");
        KBStickItemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        KBStickItemMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        KBStickItemMeta.spigot().setUnbreakable(true);
        KBStick.setItemMeta(KBStickItemMeta);
        player.getInventory().setItem(0, KBStick);

        ItemStack Blocks = new ItemStack(Material.SANDSTONE, 64);
        player.getInventory().setItem(1, Blocks);

        ItemStack BowItem = new ItemStack(Material.BOW, 1);
        ItemMeta BowItemMeta = BowItem.getItemMeta();
        BowItemMeta.setDisplayName("§d§lSakura");
        BowItemMeta.spigot().setUnbreakable(true);
        BowItem.setItemMeta(BowItemMeta);
        player.getInventory().setItem(2, BowItem);

        giveJumpPad(player);

        ItemStack SpeedItem = new ItemStack(Material.FEATHER, 1);
        ItemMeta SpeedItemMeta = SpeedItem.getItemMeta();
        SpeedItemMeta.setDisplayName("§b§l加速");
        SpeedItemMeta.spigot().setUnbreakable(true);
        SpeedItem.setItemMeta(SpeedItemMeta);
        player.getInventory().setItem(7, SpeedItem);

        giveEnderPearl(player);

        ItemStack Arrows = new ItemStack(Material.ARROW, 64);
        player.getInventory().setItem(17, Arrows);
        if (player.hasPermission("kbffa.admin")|| player.getName().equals("ShiroAiy")|| player.getName().equals("Araykal")) {
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
            player.getInventory().setItem(3, flameStick);
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
    public static GenShinImpact getByDisplayName(String displayName) {
        for (GenShinImpact map : values()) {
            if (map.getDisplayName().equals(displayName)) {
                return map;
            }
        }
        return null;
    }
}

