package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Scout extends Kit {

	public Scout() {
		super("Scout", "Aumente sua velocidade com a poção!", new Stack(Material.GOLD_BOOTS),
				new Stack(Material.LEATHER_BOOTS).display("§aBota §7(§f§lScout§7)")
						.lore("§7Utilize as §6botas §7para correr!"),
				new Stack(Material.WOOL, 1, 3).display("§aPoção §7(§f§lScout§7)")
						.lore("§7Clique com o §6direito §7para receber os efeitos."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setBoots(new Stack(Material.LEATHER_BOOTS).display("§aBota §7(§f§lScout§7)")
				.lore("§7Utilize as §6botas §7para correr!"));
		inv.addItem(new Stack(Material.WOOL, 1, 3).display("§aPoção §7(§f§lScout§7)")
				.lore("§7Clique com o §6direito §7para receber os efeitos."));
	}
}