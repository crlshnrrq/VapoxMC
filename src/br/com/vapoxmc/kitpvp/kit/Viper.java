package br.com.vapoxmc.kitpvp.kit;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Viper extends Kit implements Listener {

	public Viper() {
		super("Viper", "Tenha chancê de envenenar seus oponentes!", new Stack(Material.SPIDER_EYE));
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
				&& new Random().nextInt(100) <= 30) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof Viper
					&& damager.getItemInHand().getType().name().contains("_SWORD"))
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
		}
	}
}