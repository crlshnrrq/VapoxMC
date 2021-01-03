package br.com.vapoxmc.kitpvp.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class NinjaKit extends Kit implements Listener {

	public static final Map<UUID, UUID> targetMap = new HashMap<>();

	public NinjaKit() {
		super("Ninja", "Teleporte-se para seu oponente!", new Stack(Material.ENDER_PEARL));
	}

	@EventHandler
	private void onEntityDamamgeByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof NinjaKit)
				targetMap.put(damager.getUniqueId(), player.getUniqueId());
		}
	}

	@EventHandler
	private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isSneaking())
			return;

		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof NinjaKit) {
			if (!VapoxPvP.hasKitCooldown(player)) {
				if (targetMap.containsKey(player.getUniqueId())) {
					Player target = Bukkit.getPlayer(targetMap.get(player.getUniqueId()));
					if (target != null && !target.isDead() && player.getLocation().distance(target.getLocation()) <= 35D
							&& VapoxPvP.hasKit(target)) {
						VapoxPvP.addKitCooldown(player, 10);
						player.teleport(target.getLocation());
						player.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fTeleportado para o jogador §a"
								+ target.getName() + "§f!");
					} else
						player.sendMessage(
								"§c§l[" + this.getName().toUpperCase() + "] §cO seu ninja não encontrou ninguém!");
				} else
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §cVocê não atingiu ninguém!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ VapoxPvP.getFormattedKitCooldown(player) + " §fpara usar novamente!");
		}
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		targetMap.remove(event.getEntity().getUniqueId());
	}
}