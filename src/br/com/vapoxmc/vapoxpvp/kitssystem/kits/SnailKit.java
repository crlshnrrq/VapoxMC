package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class SnailKit extends Kit implements Listener {

	public SnailKit() {
		super("Snail", "Tenha 30% de chance de lentar seus oponentes.", new Stack(Material.SOUL_SAND), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& new Random().nextInt(100) <= 30) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (system.getKit(damager) instanceof SnailKit)
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0), true);
		}
	}
}