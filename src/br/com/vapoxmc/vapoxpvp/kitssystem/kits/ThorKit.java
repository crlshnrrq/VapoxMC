package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

@SuppressWarnings("deprecation")
public final class ThorKit extends Kit implements Listener {

	public static final Stack MJOLNIR = new Stack(Material.GOLD_AXE).display("§7» §fMjolnir §7«");

	public ThorKit() {
		super("Thor", "Jogue raios com o seu machado.", new Stack(Material.GOLD_AXE), true, MJOLNIR);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(MJOLNIR);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof ThorKit && event.getAction().name().contains("RIGHT") && event.hasItem()
				&& event.getItem().isSimilar(MJOLNIR.toItemStack())) {
			if (!this.hasCooldown(player)) {
				this.addCooldown(player, 6);
				player.getWorld().strikeLightning(player.getTargetBlock((HashSet<Byte>) null, 20).getLocation());
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
		if (system.getKit(player) instanceof ThorKit
				&& event.getItemDrop().getItemStack().isSimilar(MJOLNIR.toItemStack()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.LIGHTNING) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof ThorKit)
				event.setCancelled(true);
			else
				player.setFireTicks(100);
		}
	}
}