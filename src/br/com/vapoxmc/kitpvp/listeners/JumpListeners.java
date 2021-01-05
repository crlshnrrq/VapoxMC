package br.com.vapoxmc.kitpvp.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

public final class JumpListeners implements Listener {

	private static final ArrayList<UUID> noFallDamage = new ArrayList<>();

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
			Location loc = event.getTo().getBlock().getLocation();
			player.setVelocity(player.getLocation().getDirection().multiply(0).setY(3));
			player.playSound(loc, Sound.ORB_PICKUP, 6F, 1F);
			player.playEffect(loc, Effect.ENDER_SIGNAL, null);
			player.playEffect(loc, Effect.CLICK1, null);
			player.playEffect(loc, Effect.BLAZE_SHOOT, null);
			if (!noFallDamage.contains(player.getUniqueId()))
				noFallDamage.add(player.getUniqueId());
		}
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL) {
			Player player = (Player) event.getEntity();
			if (noFallDamage.contains(player.getUniqueId())) {
				event.setCancelled(true);
				noFallDamage.remove(player.getUniqueId());
			}
		}
	}
}