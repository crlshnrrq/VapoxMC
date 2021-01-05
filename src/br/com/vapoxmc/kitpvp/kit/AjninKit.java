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

public final class AjninKit extends Kit implements Listener {

	public final Map<UUID, UUID> tegratMap = new HashMap<>();

	public AjninKit() {
		super("Ajnin", "Teleporte seu oponente até você!", new Stack(Material.NETHER_STAR));
	}

	@EventHandler
	private void onEntityDamamgeByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof AjninKit)
				tegratMap.put(damager.getUniqueId(), player.getUniqueId());
		}
	}

	@EventHandler
	private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isSneaking())
			return;

		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof AjninKit) {
			if (!VapoxPvP.hasKitCooldown(player)) {
				if (tegratMap.containsKey(player.getUniqueId())) {
					Player target = Bukkit.getPlayer(tegratMap.get(player.getUniqueId()));
					if (target != null && !target.isDead() && player.getLocation().distance(target.getLocation()) <= 35D
							&& VapoxPvP.hasKit(target)) {
						VapoxPvP.addKitCooldown(player, 10);
						player.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fVocê puxou o jogador §a"
								+ target.getName() + "§faté você!");

						target.teleport(player.getLocation());
						target.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §e" + player.getName()
								+ " §fteleportou você até ele!");
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
		tegratMap.remove(event.getEntity().getUniqueId());
	}
}