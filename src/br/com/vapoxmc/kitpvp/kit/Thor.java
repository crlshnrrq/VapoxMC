package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Thor extends Kit {

	public Thor() {
		super("Thor", "Jogue raios com o seu machado!", new Stack(Material.GOLD_AXE), new Stack(Material.GOLD_AXE)
				.display("§aEspada §7(§f§lThor§7)").lore("§7Clique com o §6direitor §7para jogar raios."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(new Stack(Material.GOLD_AXE).display("§aEspada §7(§f§lThor§7)")
				.lore("§7Clique com o §6direitor §7para jogar raios."));
	}
}