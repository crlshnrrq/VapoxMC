package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class FishermanKit extends Kit implements Listener {

	public static final Stack FISHERMAN_ROD = new Stack(Material.FISHING_ROD).display("§7» §fVara de Pesca §7«");

	public FishermanKit() {
		super("Fisherman", "Pesque os seus oponentes.", new Stack(Material.FISHING_ROD), true, FISHERMAN_ROD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(FISHERMAN_ROD);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerFish(PlayerFishEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof FishermanKit && event.getCaught() instanceof Player) {
			Player caught = (Player) event.getCaught();
			if (system.hasKit(caught)) {
				if (player.getName().equals(caught.getName())) {
					event.setCancelled(true);
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fVocê não pode fisgar a si mesmo!");
				} else {
					caught.teleport(player.getLocation());
					caught.sendMessage(
							"§a§l[" + this.getName().toUpperCase() + "] §fVocê foi pescado por §a" + player.getName());
					player.sendMessage(
							"§a§l[" + this.getName().toUpperCase() + "] §fVocê pescou o jogador §a" + caught.getName());
				}

				player.getItemInHand().setDurability((short) 0);
				player.updateInventory();
			} else {
				event.setCancelled(true);
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fEsse jogador não está em pvp!");
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof FishermanKit
				&& event.getItemDrop().getItemStack().isSimilar(FISHERMAN_ROD.toItemStack()))
			event.setCancelled(true);
	}
}