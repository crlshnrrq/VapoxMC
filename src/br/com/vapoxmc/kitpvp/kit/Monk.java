package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Monk extends Kit {

	public Monk() {
		super("Monk", "Bagunçe o inventário do seu oponente!", new Stack(Material.BLAZE_ROD),
				new Stack(Material.BLAZE_ROD).display("§aVareta §7(§f§lMonk§7)")
						.lore("§7Clique com o §6direito §7para bagunçar seus oponentes."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(new Stack(Material.BLAZE_ROD).display("§aVareta §7(§f§lMonk§7)")
				.lore("§7Clique com o §6direito §7para bagunçar seus oponentes."));
	}
}