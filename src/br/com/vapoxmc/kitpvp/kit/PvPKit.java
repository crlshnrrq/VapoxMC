package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class PvPKit extends Kit {

	public PvPKit() {
		super("PvP", "Kit sem abilidades, com uma espada afiada!", new Stack(Material.STONE_SWORD),
				new Stack(Material.STONE_SWORD).ench(Enchantment.DAMAGE_ALL, 1).display("§aEspada §7(§f§lPvP§7)")
						.lore("§7Clique com o §6esquerdo §7para atacar um oponente."));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setItem(0, new Stack(Material.STONE_SWORD).ench(Enchantment.DAMAGE_ALL, 1).display("§aEspada §7(§f§lPvP§7)")
				.lore("§7Clique com o §6esquerdo §7para atacar um oponente."));
	}
}