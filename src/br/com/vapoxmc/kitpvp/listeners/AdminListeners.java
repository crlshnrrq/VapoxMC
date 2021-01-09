package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.commands.AdminCommand;

public final class AdminListeners implements Listener {

	@EventHandler
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		if (event.getRightClicked() instanceof Player && VapoxPvP.hasAdmin(player)) {
			Player righted = (Player) event.getRightClicked();
			if (item == null || item.getType() == Material.AIR)
				Bukkit.dispatchCommand(player, "invsee " + righted.getName());
			else if (item != null) {
				if (item.isSimilar(AdminCommand.CLICKTEST.toItemStack()))
					Bukkit.dispatchCommand(player, "clicktest " + righted.getName());
				else if (item.isSimilar(AdminCommand.INFO.toItemStack()))
					Bukkit.dispatchCommand(player, "infocommand " + righted.getName());
				else if (item.isSimilar(AdminCommand.KNOCKBACK.toItemStack())) {
					Location loc = righted.getLocation();
					int y = loc.getBlockY();
					righted.setVelocity(new Vector(0D, 0.7D, 0D));
					Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
						boolean result = (loc.add(0D, 2D, 0D).getBlock().isEmpty()
								&& righted.getLocation().getBlockY() == y);

						player.sendMessage(" ");
						player.sendMessage("§fVerificação de §eKnockback §frealizada!");
						player.sendMessage("§fJogador verificado: §a" + righted.getName());
						player.sendMessage("§fProbabilidade: " + (result ? "§a100%" : "§c0%") + "§f.");
						player.sendMessage(" ");
					}, 8L);
				}
			}
		}
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasClickTest(player) && event.getAction().name().contains("LEFT"))
			VapoxPvP.setClickTest(player, VapoxPvP.getClickTest(player) + 1);
		if (VapoxPvP.hasAdmin(player) && event.hasItem()
				&& event.getItem().isSimilar(AdminCommand.QUICKADMIN.toItemStack())) {
			Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(player));
			player.sendMessage("§e§l[ADMIN] §fVocê §c§lESTA VISIVEL §fpara todos.");

			Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
				Bukkit.getOnlinePlayers().stream().filter(players -> !players.hasPermission("ciphen.comandos.admin"))
						.forEach(players -> players.hidePlayer(player));
				player.sendMessage("§e§l[ADMIN] §fVocê §c§lESTA INVISIVEL §fpara todos.");
			}, 10L);
		}
	}

	@EventHandler
	private void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (VapoxPvP.hasAdmin(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		if (VapoxPvP.hasAdmin(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event) {
		if (VapoxPvP.hasAdmin(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (VapoxPvP.hasAdmin(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && VapoxPvP.hasAdmin((Player) event.getEntity()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event) {
		if (VapoxPvP.hasAdmin(event.getPlayer()))
			VapoxPvP.removeAdmin(event.getPlayer());
	}
}