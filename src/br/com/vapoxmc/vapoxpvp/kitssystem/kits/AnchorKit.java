package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class AnchorKit extends Kit implements Listener {

	public AnchorKit() {
		super("Anchor", "Não tome e nem aplique repulsão.", new Stack(Material.ANVIL), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (system.getKit(player) instanceof AnchorKit) {
				player.setVelocity(new Vector());
				player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 4F, 4F);
				Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.setVelocity(new Vector()), 1L);
			}
			if (system.getKit(damager) instanceof AnchorKit) {
				damager.playSound(damager.getLocation(), Sound.ANVIL_BREAK, 4F, 4F);
				player.setVelocity(new Vector());
				Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.setVelocity(new Vector()), 1L);
			}
		}
	}
}