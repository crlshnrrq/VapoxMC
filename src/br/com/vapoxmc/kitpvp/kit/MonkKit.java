package br.com.vapoxmc.kitpvp.kit;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class MonkKit extends Kit implements Listener {

	public static final Stack MONK_ROD = new Stack(Material.BLAZE_ROD).display("§aVareta §7(§f§lMonk§7)")
			.lore("§7Clique com o §6direito §7para bagunçar seus oponentes.");

	public MonkKit() {
		super("Monk", "Bagunçe o inventário do seu oponente!", new Stack(Material.BLAZE_ROD), MONK_ROD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(MONK_ROD);
	}

	@EventHandler
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof MonkKit && player.getItemInHand().isSimilar(MONK_ROD.toItemStack())
				&& event.getRightClicked() instanceof Player) {
			Player righted = (Player) event.getRightClicked();
			event.setCancelled(true);

			if (VapoxPvP.hasKit(righted)) {
				if (!VapoxPvP.hasKitCooldown(player)) {
					VapoxPvP.addKitCooldown(player, 5);
					player.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fVocê utilizou o seu kit monk!");

					PlayerInventory inv = righted.getInventory();
					int slot = new Random().nextInt(inv.getSize());
					ItemStack item = inv.getItem(slot);
					inv.setItem(slot, inv.getItemInHand());
					inv.setItemInHand(item);
					righted.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fSeu inventário foi trocado!");
				} else
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
							+ VapoxPvP.getFormattedKitCooldown(player) + " §fpara usar novamente!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fEsse jogador não está em combate!");
		}
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof MonkKit
				&& event.getItemDrop().getItemStack().isSimilar(MONK_ROD.toItemStack()))
			event.setCancelled(true);
	}
}