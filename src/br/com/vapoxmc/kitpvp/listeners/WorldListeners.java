package br.com.vapoxmc.kitpvp.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class WorldListeners implements Listener {

	@EventHandler
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		List<String> commands = Arrays.asList("/pex", "/promote", "/demote", "/permissionsex:pex",
				"/permissionsex:demote", "/permissionsex:promote", "/rl", "/reload", "/bukkit:reload", "/bukkit:rl");
		if (!player.hasPermission("ciphen.tag.dono")
				&& commands.contains(event.getMessage().split(" ")[0].toLowerCase())) {
			event.setCancelled(true);
			player.sendMessage("§c§l[BLOQUEIO] §fEsse comando está desativado.");
		}

		if (!player.isOp()) {
			List<String> otherCommands = Arrays.asList("/pl", "/plugin", "/ver", "/help", "/?", "/me", "/msg",
					"/minecraft:tell", "bukkit:msg", "bukkit:op", "bukkit:help", "plugins", "bukkit:pl",
					"bukkit:plugins", "version", "/bukkit:w", "/w", "/about", "/bukkit:about", "/minecraft:me",
					"/bukkit:version", "/plugins", "/bukkit:plugin", "/icanhasbukkit", "/bukkit:?", "/bukkit:help",
					"/bukkit:?", "/bukkit:ver");
			if (otherCommands.contains(event.getMessage().split(" ")[0].toLowerCase())) {
				event.setCancelled(true);
				player.sendMessage("§cÉ necessário ser [DIRETOR] ou superior para executar este comando!");
			}
		}
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
	}

	@EventHandler
	private void onItemSpawn(ItemSpawnEvent event) {
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> event.getEntity().remove(), 40L);
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		event.setDamage(event.getDamage() - 1.5D);

		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
				event.setDamage(event.getDamage() - 6.3D);

			if (player.getItemInHand().getType().name().contains("_SWORD")
					|| player.getItemInHand().getType().name().contains("_AXE")) {
				player.getItemInHand().setDurability((short) 0);
				player.updateInventory();
			}
		}
	}

	@EventHandler
	private void onEntityShootBow(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player)
			event.getBow().setDurability((short) -1);
	}

	@EventHandler
	private void onSignChange(SignChangeEvent event) {
		for (int index = 0; index < 4; index++)
			event.setLine(index, ChatColor.translateAlternateColorCodes('&', event.getLine(index)));
	}

	@EventHandler
	private void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
}