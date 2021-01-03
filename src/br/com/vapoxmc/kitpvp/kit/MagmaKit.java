package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class MagmaKit extends Kit implements Listener {

	public MagmaKit() {
		super("Magma", "Tenha chancê de colocar fogo em seus oponentes!", new Stack(Material.FLINT_AND_STEEL));
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& (Math.random() > 0.4 && Math.random() < 0.1)) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof MagmaKit)
				player.setFireTicks(150);
		}
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().getType().name().contains("WATER")) {
			Player player = event.getPlayer();
			if (VapoxPvP.getKit(player) instanceof MagmaKit)
				player.damage(2D);
		}
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player
				&& (event.getCause().name().contains("FIRE") || event.getCause().name().contains("LAVA"))) {
			Player player = (Player) event.getEntity();
			if (VapoxPvP.getKit(player) instanceof MagmaKit)
				event.setCancelled(true);
		}
	}
}