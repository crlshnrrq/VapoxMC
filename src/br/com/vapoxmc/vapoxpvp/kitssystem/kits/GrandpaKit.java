package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;

public final class GrandpaKit extends Kit {

	public static final Stack ROD = new Stack(Material.STICK).ench(Enchantment.KNOCKBACK, 2).display("§7» §fVara §7«");

	public GrandpaKit() {
		super("Grandpa", "Arremesse seus inimigos para longe.", new Stack(Material.STICK), true, ROD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.addItem(ROD);
	}
}