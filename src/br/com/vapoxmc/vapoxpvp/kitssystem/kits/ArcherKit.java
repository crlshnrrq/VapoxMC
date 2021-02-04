package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class ArcherKit extends Kit implements Listener {

	public static final Stack BOW = new Stack(Material.BOW).ench(Enchantment.ARROW_INFINITE, 1)
			.display("§7» §fArco §7«"), ARROW = new Stack(Material.ARROW).display("§7» §fFlecha §7«");

	public ArcherKit() {
		super("Archer", "Utilize seu arco e flecha para atingir oponentes.", new Stack(Material.BOW), true, BOW, ARROW);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(BOW, ARROW);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof ArcherKit
				&& (event.getItemDrop().getItemStack().isSimilar(BOW.toItemStack())
						|| event.getItemDrop().getItemStack().isSimilar(ARROW.toItemStack())))
			event.setCancelled(true);
	}
}