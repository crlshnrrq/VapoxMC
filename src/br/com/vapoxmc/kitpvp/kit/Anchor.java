package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.bukkit.event.Listener;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Anchor extends Kit implements Listener {

	public Anchor() {
		super("Anchor", "Não tome e nem dê repulsão!", new Stack(Material.ANVIL));
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(player) instanceof Anchor) {
				player.setVelocity(new Vector());
				player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 4F, 4F);
				Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.setVelocity(new Vector()), 1L);
			}
			if (VapoxPvP.getKit(damager) instanceof Anchor) {
				damager.playSound(damager.getLocation(), Sound.ANVIL_BREAK, 4F, 4F);
				player.setVelocity(new Vector());
				Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.setVelocity(new Vector()), 1L);
			}
		}
	}
}