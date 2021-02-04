package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class TurtleKit extends Kit implements Listener {

	public TurtleKit() {
		super("Turtle", "Mostre o seu poderoso casco inquebrável.", new Stack(Material.DIAMOND_CHESTPLATE), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) | !system.isEnable())
			return;

		if (event.getDamager() instanceof Player && event.getDamage() > 1D) {
			Player damager = (Player) event.getDamager();
			if (system.getKit(damager) instanceof TurtleKit && damager.isSneaking())
				event.setDamage(1D);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) | !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamage() > 1D) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof TurtleKit && player.isSneaking())
				event.setDamage(1D);
		}
	}
}