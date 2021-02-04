package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class StomperKit extends Kit implements Listener {

	public StomperKit() {
		super("Stomper", "Esmague os seus oponentes!", new Stack(Material.IRON_BOOTS), true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof StomperKit) {
				player.getNearbyEntities(3.5D, 1D, 3.5D).stream().filter(entities -> entities instanceof Player)
						.forEach(entities -> {
							Player target = (Player) entities;
							if (target.isSneaking() || system.getKit(target) instanceof AntiStomperKit)
								target.damage(4D, player);
							else
								target.damage(event.getDamage(), player);
							target.sendMessage("§e§l[" + this.getName().toUpperCase() + "] §fVocê foi stompado por §e"
									+ player.getName() + "§f!");

							player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 2F, 1F);
							player.sendMessage("§e§l[" + this.getName().toUpperCase() + "] §fVocê stompou §e"
									+ target.getName() + "§f!");
						});
				if (event.getDamage() > 4D)
					event.setDamage(4D);
			}
		}
	}
}