package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class KnockbackWarp extends Warp implements Listener {

	public static final Stack STICK = new Stack(Material.STICK).hideEnchants().ench(Enchantment.KNOCKBACK, 3)
			.display("§aGraveto! §7(§f§lKnockback§7)").lore("§fItem de: §a§lKNOCKBACK§f.");

	public KnockbackWarp() {
		super("Knockback", "Divirta-se arremesando seus inimigos para longe.", new Stack(Material.STICK),
				new Location(Bukkit.getWorlds().get(0), -30001, 25, -30001));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.addItem(STICK);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		if (event.getWarp() instanceof KnockbackWarp) {
			Player player = event.getPlayer();
			VapoxPvP.addProtection(player);
			player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1), true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.VOID) {
			Player player = (Player) event.getEntity();
			if (system.getWarp(player) instanceof KnockbackWarp)
				event.setDamage(20D);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof KnockbackWarp) {
			if (VapoxPvP.hasProtection(player) && this.getLocation().distance(event.getTo()) > 10) {
				VapoxPvP.removeProtection(player);
				player.setFallDistance(-10F);
				player.sendMessage("§e• §fVocê perdeu a §e§nProteção de Spawn§f.");
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof KnockbackWarp
				&& event.getItemDrop().getItemStack().isSimilar(STICK.toItemStack()))
			event.setCancelled(true);
	}
}