package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;

public class PvPKit extends Kit {

	public static final Stack SWORD = new Stack(Material.STONE_SWORD).ench(Enchantment.DAMAGE_ALL, 1)
			.display("§7» §fEspada §7«");

	public PvPKit() {
		super("PvP", "Kit sem habilidades, apenas com uma espada afiada.", new Stack(Material.STONE_SWORD), true,
				SWORD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setItem(0, SWORD);
	}
}