package br.com.vapoxmc.kitpvp.kit;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Critical extends Kit implements Listener {

	public Critical() {
		super("Critical", "De danos criticos no oponente!", new Stack(Material.REDSTONE_BLOCK));
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& new Random().nextInt(100) <= 30) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof Critical) {
				event.setDamage(event.getDamage() + 0.8D);
				player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 10);
			}
		}
	}
}