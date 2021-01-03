package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Fisherman extends Kit implements Listener {

	public static final Stack FISHERMAN_ROD = new Stack(Material.FISHING_ROD).display("§aVareta §7(§f§lFisherman§7)")
			.lore("§7Clique com o §6direito §7para pescar seus oponentes.");

	public Fisherman() {
		super("Fisherman", "Pesque os seus oponentes!", new Stack(Material.FISHING_ROD), FISHERMAN_ROD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(FISHERMAN_ROD);
	}

	@EventHandler
	private void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Fisherman && event.getCaught() instanceof Player) {
			Player caught = (Player) event.getCaught();
			if (VapoxPvP.hasKit(caught)) {
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

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Fisherman
				&& event.getItemDrop().getItemStack().isSimilar(FISHERMAN_ROD.toItemStack()))
			event.setCancelled(true);
	}
}