package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public class MonkKit extends Kit implements Listener {

	public static final Stack MONK_ROD = new Stack(Material.BLAZE_ROD).display("§7» §fEmbaralhar §7«");

	public MonkKit() {
		super("Monk", "Embaralhe o inventário do seu oponente.", new Stack(Material.BLAZE_ROD), true, MONK_ROD);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(MONK_ROD);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof MonkKit && player.getItemInHand().isSimilar(MONK_ROD.toItemStack())
				&& event.getRightClicked() instanceof Player) {
			Player righted = (Player) event.getRightClicked();
			event.setCancelled(true);

			if (system.hasKit(righted)) {
				if (!this.hasCooldown(player)) {
					this.addCooldown(player, 5);
					player.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fVocê utilizou o seu kit monk!");

					PlayerInventory inv = righted.getInventory();
					int slot = new Random().nextInt(inv.getSize());
					ItemStack item = inv.getItem(slot);
					inv.setItem(slot, inv.getItemInHand());
					inv.setItemInHand(item);
					righted.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fSeu inventário foi trocado!");
				} else
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
							+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fEsse jogador não está em combate!");
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
		if (system.getKit(player) instanceof MonkKit
				&& event.getItemDrop().getItemStack().isSimilar(MONK_ROD.toItemStack()))
			event.setCancelled(true);
	}
}