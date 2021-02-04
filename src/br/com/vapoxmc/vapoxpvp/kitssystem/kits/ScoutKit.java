package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class ScoutKit extends Kit implements Listener {

	public static final Stack POTION = new Stack(Material.WOOL, 1, 3).display("§7» §fPoção §7«");

	public ScoutKit() {
		super("Scout", "Aumente sua velocidade com a poção.", new Stack(Material.GOLD_BOOTS), true, POTION);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(POTION);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof ScoutKit && event.getAction().name().contains("RIGHT") && event.hasItem()
				&& event.getItem().isSimilar(POTION.toItemStack())) {
			event.setCancelled(true);
			if (!this.hasCooldown(player)) {
				this.addCooldown(player, 15);
				player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 1F, 1F);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1), true);
				player.sendMessage("§a§l[" + this.getName().toUpperCase()
						+ "] §fVocê recebeu efeito de §avelocidade §fpor §a5 segundos§f!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
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
		if (system.getKit(player) instanceof ScoutKit
				&& event.getItemDrop().getItemStack().isSimilar(POTION.toItemStack()))
			event.setCancelled(true);
	}
}