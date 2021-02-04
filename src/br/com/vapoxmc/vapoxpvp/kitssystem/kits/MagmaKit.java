package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class MagmaKit extends Kit implements Listener {

	public MagmaKit() {
		super("Magma", "Tenha chance de colocar fogo em seus oponentes.", new Stack(Material.FLINT_AND_STEEL), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& (Math.random() > 0.4 && Math.random() < 0.1)) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (system.getKit(damager) instanceof MagmaKit)
				player.setFireTicks(150);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getFrom().getBlock().getType().name().contains("WATER")) {
			Player player = event.getPlayer();
			if (system.getKit(player) instanceof MagmaKit)
				player.damage(2D);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player
				&& (event.getCause().name().contains("FIRE") || event.getCause().name().contains("LAVA"))) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof MagmaKit)
				event.setCancelled(true);
		}
	}
}