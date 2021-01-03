package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class ArcherKit extends Kit implements Listener {

	public static final Stack BOW = new Stack(Material.BOW).ench(Enchantment.ARROW_INFINITE, 1)
			.display("§aArco §7(§f§lArcher§7)").lore("§7Clique com o §6direito §7para acertar seus oponentes."),
			ARROW = new Stack(Material.ARROW).display("§aFlecha")
					.lore("§7Utilize a flecha para acertar seus oponentes!");

	public ArcherKit() {
		super("Archer", "Utilize seu arco e flecha para atingir oponentes!", new Stack(Material.BOW), BOW, ARROW);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(BOW, ARROW);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof ArcherKit
				&& (event.getItemDrop().getItemStack().isSimilar(BOW.toItemStack())
						|| event.getItemDrop().getItemStack().isSimilar(ARROW.toItemStack())))
			event.setCancelled(true);
	}
}