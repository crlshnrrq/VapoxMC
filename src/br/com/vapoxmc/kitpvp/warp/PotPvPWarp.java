package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class PotPvPWarp extends Warp implements Listener {

	public static final Stack SWORD = new Stack(Material.DIAMOND_SWORD).ench(Enchantment.DAMAGE_ALL, 1)
			.ench(Enchantment.FIRE_ASPECT, 1).display("§aEspada §7(§f§lPotPvP§7)")
			.lore("§7Clique com o §6esquerdo §7para atacar um oponente.");

	public PotPvPWarp() {
		super("PotPvP", new Stack(Material.POTION, 1, 16421),
				new Location(Bukkit.getWorlds().get(0), 10000, 107, 10000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setHelmet(new Stack(Material.DIAMOND_HELMET));
		inv.setChestplate(new Stack(Material.DIAMOND_CHESTPLATE));
		inv.setLeggings(new Stack(Material.DIAMOND_LEGGINGS));
		inv.setBoots(new Stack(Material.DIAMOND_BOOTS));

		inv.setItem(0, SWORD);
		inv.setItem(1, new Stack(Material.POTION, 1, 8258).display("§7» §fPoção"));
		inv.setItem(2, new Stack(Material.POTION, 1, 8259).display("§7» §fPoção"));

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.POTION, 1, 16421).display("§7» §fPoção"));

		VapoxPvP.removeProtection(player);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof PotPvPWarp
				&& event.getItemDrop().getItemStack().isSimilar(SWORD.toItemStack()))
			event.setCancelled(true);
	}
}