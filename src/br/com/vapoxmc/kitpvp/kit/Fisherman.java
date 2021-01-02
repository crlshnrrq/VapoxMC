package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Fisherman extends Kit {

	public Fisherman() {
		super("Fisherman", "Pesque os seus oponentes!", new Stack(Material.FISHING_ROD),
				new Stack(Material.FISHING_ROD).display("§aVareta §7(§f§lFisherman§7)")
						.lore("§7Clique com o §6direito §7para pescar seus oponentes."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(new Stack(Material.FISHING_ROD).display("§aVareta §7(§f§lFisherman§7)")
				.lore("§7Clique com o §6direito §7para pescar seus oponentes."));
	}
}