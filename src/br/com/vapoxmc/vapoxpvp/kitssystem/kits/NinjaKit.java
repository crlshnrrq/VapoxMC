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

public class NinjaKit extends Kit implements Listener {

	public final Map<UUID, UUID> targetMap = new HashMap<>();

	public NinjaKit() {
		super("Ninja", "Teleporte-se para seu oponente!", new Stack(Material.ENDER_PEARL), true);
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
			if (system.getKit(damager) instanceof NinjaKit)
				this.targetMap.put(damager.getUniqueId(), player.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		if (event.isSneaking())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof NinjaKit) {
			if (!this.hasCooldown(player)) {
				if (this.targetMap.containsKey(player.getUniqueId())) {
					Player target = Bukkit.getPlayer(this.targetMap.get(player.getUniqueId()));
					if (target != null && !target.isDead() && player.getLocation().distance(target.getLocation()) <= 35D
							&& system.hasKit(target)) {
						this.addCooldown(player, 10);
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
						+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDeath(PlayerDeathEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		this.targetMap.remove(event.getEntity().getUniqueId());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerRemoveKit(PlayerRemoveKitEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (event.getKit() instanceof NinjaKit)
			this.targetMap.remove(player.getUniqueId());
	}
}