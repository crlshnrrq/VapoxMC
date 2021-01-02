package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Kangaroo extends Kit {

	public Kangaroo() {
		super("Kangaroo", "Pule que nem um canguru!", new Stack(Material.FIREWORK), new Stack(Material.FIREWORK)
				.display("§aKangaroo §7(§fClique§7)").lore("§7Clique com o §6direito §7para pular duas vezes."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(new Stack(Material.FIREWORK).display("§aKangaroo §7(§fClique§7)")
				.lore("§7Clique com o §6direito §7para pular duas vezes."));
	}
}