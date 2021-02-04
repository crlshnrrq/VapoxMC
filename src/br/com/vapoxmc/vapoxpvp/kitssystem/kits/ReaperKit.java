package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class ReaperKit extends Kit implements Listener {

	public static final Stack SCYTHE = new Stack(Material.WOOD_HOE).display("§7» §fFoice §7«");

	public ReaperKit() {
		super("Reaper", "Se torne a reencarnação da morte e ceife seus inimigos.", new Stack(Material.WOOD_HOE), true,
				SCYTHE);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(SCYTHE);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if (system.getKit(damager) instanceof ReaperKit && damager.getItemInHand().isSimilar(SCYTHE.toItemStack()))
				player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0), true);
		}
	}
}