package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class SwitcherKit extends Kit implements Listener {

	public static final Stack BALLS = new Stack(Material.SNOW_BALL, 64).display("§7» §fBolas de Neve §7«");

	public SwitcherKit() {
		super("Switcher", "Troque de lugar com o jogador que você atingir.", new Stack(Material.SNOW_BALL), true,
				BALLS);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.addItem(BALLS);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Snowball) {
			Player player = (Player) event.getEntity();
			Snowball damager = (Snowball) event.getDamager();
			if (damager.getShooter() instanceof Player) {
				Player shooter = (Player) damager.getShooter();
				if (system.getKit(shooter) instanceof SwitcherKit) {
					if (shooter.getName().equals(player.getName()))
						return;
					Location loc = player.getLocation();
					player.teleport(shooter.getLocation());
					player.sendMessage(
							"§c§l[" + this.getName().toUpperCase() + "] §fVocê foi atingido por um §cSwitcher§f.");
					shooter.teleport(loc);
					shooter.sendMessage("§a§l[" + this.getName().toUpperCase() + "] §fVocê trocou de lugar com §a"
							+ player.getName() + "§f.");
				}
			}
		}
	}
}