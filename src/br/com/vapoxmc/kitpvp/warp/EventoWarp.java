package br.com.vapoxmc.kitpvp.warp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;

public final class EventoWarp extends Warp implements Listener {

	public EventoWarp() {
		super("Evento", "vapoxpvp.warp.evento.description", new Stack(Material.CAKE),
				new Location(Bukkit.getWorlds().get(0), 4000, 102, 4000));
	}

	@Override
	public void giveItems(Player player) {
		super.giveItems(player);

		VapoxPvP.removeProtection(player);
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasEventoPlayer(player)) {
			if (VapoxPvP.getEventoBuild()) {
				if (VapoxPvP.getPlacedblocks().contains(event.getBlock())) {
					event.setCancelled(false);
					VapoxPvP.getPlacedblocks().remove(event.getBlock());
				} else {
					event.setCancelled(true);
					player.sendMessage("§c§l[EVENTO] §fVocê só pode quebrar blocos colocados por outros jogadores!");
				}
			}
		}
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& VapoxPvP.hasEventoPlayer((Player) event.getEntity()) && !VapoxPvP.getEventoPvP())
			event.setCancelled(true);
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && VapoxPvP.hasEventoPlayer((Player) event.getEntity())
				&& !VapoxPvP.getEventoPvP())
			event.setCancelled(true);
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasEventoPlayer(player)) {
			if (VapoxPvP.getEventoBuild())
				VapoxPvP.getPlacedblocks().add(event.getBlock());
		}
	}

	@EventHandler
	private void onPlayerBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasEventoPlayer(player)) {
			if (VapoxPvP.getEventoBuild())
				event.setCancelled(false);
			else
				event.setCancelled(true);
		}
	}

	@EventHandler
	private void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasEventoPlayer(player)) {
			if (VapoxPvP.getEventoBuild())
				event.setCancelled(false);
			else
				event.setCancelled(true);
		}
	}

	@EventHandler
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().split(" ")[0];
		if (VapoxPvP.hasEventoPlayer(player)
				&& Arrays.asList("Spawn", "ResetKit", "Fly", "Kit", "Warp").contains(message.toLowerCase())) {
			event.setCancelled(true);
			player.sendMessage("§c§l[EVENTO] §fVocê não pode executar esse comando no evento! §7(" + message + ")");
			player.sendMessage("§c§l[EVENTO] §fCaso queira sair do evento, digite §c/evento sair§f.");
		}
	}
}