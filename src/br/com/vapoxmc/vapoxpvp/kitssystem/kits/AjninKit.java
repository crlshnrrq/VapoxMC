package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerRemoveKitEvent;

public final class AjninKit extends Kit implements Listener {

	public final Map<UUID, UUID> tegratMap = new HashMap<>();

	public AjninKit() {
		super("Ajnin", "Teleporte seu oponente até você.", new Stack(Material.NETHER_STAR), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamamgeByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (system.getKit(damager) instanceof AjninKit)
				this.tegratMap.put(damager.getUniqueId(), player.getUniqueId());
		}
	}

	@EventHandler
	private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		if (event.isSneaking())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof AjninKit) {
			if (!this.hasCooldown(player)) {
				if (this.tegratMap.containsKey(player.getUniqueId())) {
					Player target = Bukkit.getPlayer(this.tegratMap.get(player.getUniqueId()));
					if (target != null && !target.isDead() && player.getLocation().distance(target.getLocation()) <= 35D
							&& system.hasKit(target)) {
						this.addCooldown(player, 10);
						player.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fVocê puxou o jogador §a"
								+ target.getName() + " §faté você!");

						target.teleport(player.getLocation());
						target.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §e" + player.getName()
								+ " §fteleportou você até ele!");
					} else
						player.sendMessage(
								"§c§l[" + this.getName().toUpperCase() + "] §cO seu ajnin não encontrou ninguém!");
				} else
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §cVocê não atingiu ninguém!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
		}
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		this.tegratMap.remove(event.getEntity().getUniqueId());
	}

	@EventHandler
	private void onPlayerRemoveKit(PlayerRemoveKitEvent event) {
		Player player = event.getPlayer();
		if (event.getKit() instanceof AjninKit)
			this.tegratMap.remove(player.getUniqueId());
	}
}