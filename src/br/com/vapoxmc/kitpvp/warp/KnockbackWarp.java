package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class KnockbackWarp extends Warp implements Listener {

	public static final Stack STICK = new Stack(Material.STICK).ench(Enchantment.KNOCKBACK, 3)
			.display("§aGraveto! §7(§f§lKnockback§7)").lore("§fItem de: §a§lKNOCKBACK§f.");

	public KnockbackWarp() {
		super("Knockback", new Stack(Material.STICK), new Location(Bukkit.getWorlds().get(0), 2000, 102, 2000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(0, STICK);

		VapoxPvP.removeProtection(player);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof KnockbackWarp
				&& event.getItemDrop().getItemStack().isSimilar(STICK.toItemStack())) {
			event.setCancelled(true);
		}
	}
}