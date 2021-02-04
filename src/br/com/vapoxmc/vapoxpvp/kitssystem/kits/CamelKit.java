package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class CamelKit extends Kit implements Listener {

	public CamelKit() {
		super("Camel", "Receba habilidades especiais em seu habitat natural.", new Stack(Material.SAND), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.CONTACT) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof CamelKit)
				event.setCancelled(true);
		}
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);
		if (system.getKit(player) instanceof CamelKit
				&& (block.getBiome().name().contains("DESERT") || block.getType().name().contains("SAND")))
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1), true);
	}
}