package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class VampireKit extends Kit implements Listener {

	public VampireKit() {
		super("Vampire", "Sugue as energias vitais ao matar um jogador.", new Stack(Material.REDSTONE), true);
	}

	@EventHandler(priority = EventPriority.HIGH)
	private void onPlayerDeath(PlayerDeathEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getEntity(), killer = player.getKiller();
		if (killer != null && killer != player && system.getKit(killer) instanceof VampireKit)
			killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 140, 1), true);
	}
}