package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Viking extends Kit {

	public Viking() {
		super("Viking", "De mais dano com seu machado!", new Stack(Material.STONE_AXE), new Stack(Material.STONE_AXE)
				.display("§aMachado §7(§f§lViking§7)").lore("§7Clique com o §6esquerdo §7para atacar um oponente."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setItem(0, new Stack(Material.STONE_AXE).display("§aMachado §7(§f§lViking§7)")
				.lore("§7Clique com o §6esquerdo §7para atacar um oponente."));
	}
}