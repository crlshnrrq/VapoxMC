package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Archer extends Kit {

	public Archer() {
		super("Archer", "Utilize seu arco e flecha para atingir oponentes!", new Stack(Material.BOW),
				new Stack(Material.BOW).ench(Enchantment.ARROW_INFINITE, 1).display("§aArco §7(§f§lArcher§7)")
						.lore("§7Clique com o §6direito §7para acertar seus oponentes."),
				new Stack(Material.ARROW).display("§aFlecha").lore("§7Utilize a flecha para acertar seus oponentes!"));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(
				new Stack(Material.BOW).ench(Enchantment.ARROW_INFINITE, 1).display("§aArco §7(§f§lArcher§7)")
						.lore("§7Clique com o §6direito §7para acertar seus oponentes."),
				new Stack(Material.ARROW).display("§aFlecha").lore("§7Utilize a flecha para acertar seus oponentes!"));
	}
}