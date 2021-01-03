package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class KitPvPListeners implements Listener {

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem())
			if (event.getMaterial() == Material.MUSHROOM_SOUP && event.getAction().name().contains("RIGHT")
					&& player.getHealth() != player.getMaxHealth()) {
				player.setHealth(player.getHealth() < (player.getMaxHealth() - 7D) ? (player.getHealth() + 7D)
						: player.getMaxHealth());
				event.getItem().setType(Material.BOWL);
				player.updateInventory();
			} else if (event.getMaterial() == Material.COMPASS && VapoxPvP.hasKit(player)) {
				for (int i = 0; i < 100; i++)
					for (Entity entities : player.getNearbyEntities(i, i, i)) {
						if (entities instanceof Player) {
							Player players = (Player) entities;
							if (player.getLocation().distance(players.getLocation()) > 0) {
								player.setCompassTarget(players.getLocation());
								player.sendMessage("§a§l[COMPASS] §fBússola apontando para §a" + players.getName());
								return;
							}
						}
					}
				player.setCompassTarget(new Location(player.getWorld(), 0, 65, 0));
				player.sendMessage("§c§l[COMPASS] §fNão encontramos nenhum jogador, bússola apontando para o feast.");
			}
	}
}