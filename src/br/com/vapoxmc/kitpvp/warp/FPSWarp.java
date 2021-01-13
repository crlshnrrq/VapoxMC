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

public final class FPSWarp extends Warp implements Listener {

	public static final Stack SWORD = new Stack(Material.DIAMOND_SWORD).ench(Enchantment.DAMAGE_ALL, 1)
			.display("§aEspada §7(§f§lFPS§7)").lore("§7Clique com o §6esquerdo §7para atacar um oponente.");

	public FPSWarp() {
		super("FPS", new Stack(Material.DIAMOND_SWORD), new Location(Bukkit.getWorlds().get(0), 6000, 105, 6000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setHelmet(new Stack(Material.IRON_HELMET));
		inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
		inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
		inv.setBoots(new Stack(Material.IRON_BOOTS));

		inv.setItem(13, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft"));

		inv.setItem(0, SWORD);

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));

		VapoxPvP.removeProtection(player);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof FPSWarp
				&& event.getItemDrop().getItemStack().isSimilar(SWORD.toItemStack()))
			event.setCancelled(true);
	}
}