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

public final class PoseidonKit extends Kit implements Listener {

	public PoseidonKit() {
		super("Poseidon", "Se torne o deus dos mares e receba seus poderes.", new Stack(Material.WATER_BUCKET), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		Block block = event.getTo().getBlock();
		if (system.getKit(player) instanceof PoseidonKit && (block.getType().name().contains("WATER")
				|| block.getRelative(BlockFace.UP).getType().name().contains("WATER"))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0), true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0), true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0), true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.DROWNING) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof PoseidonKit)
				event.setCancelled(true);
		}
	}
}