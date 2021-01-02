package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Urgal extends Kit {

	public Urgal() {
		super("Urgal", "Aumente sua força com a poção", new Stack(Material.RED_ROSE), new Stack(Material.WOOL, 1, 14)
				.display("§aPoção §7(§f§lUrgal§7)").lore("§7Clique com o §6direito §7para receber os efeitos."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(new Stack(Material.WOOL, 1, 14).display("§aPoção §7(§f§lUrgal§7)")
				.lore("§7Clique com o §6direito §7para receber os efeitos."));
	}
}