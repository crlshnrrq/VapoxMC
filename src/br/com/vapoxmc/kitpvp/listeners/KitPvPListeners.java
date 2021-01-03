package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class KitPvPListeners implements Listener {

	@EventHandler
	private void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("ciphen.eventos.placa")) {
			if (event.getLine(0).equalsIgnoreCase("Sopa")) {
				event.setLine(0, "§8§l[-§fx§8§l-]");
				event.setLine(1, "§aSopas!");
				event.setLine(2, "§e§l[CLIQUE]");
				event.setLine(3, "§8§l[-§fx§8§l-]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			} else if (event.getLine(0).equalsIgnoreCase("Recraft")) {
				event.setLine(0, "§8§l[-§fx§8§l-]");
				event.setLine(1, "§aRecraft!");
				event.setLine(2, "§e§l[CLIQUE]");
				event.setLine(3, "§8§l[-§fx§8§l-]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			} else if (event.getLine(0).equalsIgnoreCase("PotPvP")) {
				event.setLine(0, "§8§l[-§fx§8§l-]");
				event.setLine(1, "§aPoção!");
				event.setLine(2, "§e§l[CLIQUE]");
				event.setLine(3, "§8§l[-§fx§8§l-]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			}
		}
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem())
			if (event.getMaterial() == Material.MUSHROOM_SOUP && event.getAction().name().contains("RIGHT")
					&& player.getHealth() != player.getMaxHealth()) {
				player.setHealth(player.getHealth() < (player.getMaxHealth() - 7D) ? (player.getHealth() + 7D)
						: player.getMaxHealth());
				event.getItem().setType(Material.BOWL);
				player.updateInventory();
			} else if (event.getMaterial() == Material.COMPASS && VapoxPvP.hasKit(player)) {
				for (int i = 0; i < 100; i++)
					for (Entity entities : player.getNearbyEntities(i, i, i)) {
						if (entities instanceof Player) {
							Player players = (Player) entities;
							if (player.getLocation().distance(players.getLocation()) > 0) {
								player.setCompassTarget(players.getLocation());
								player.sendMessage("§a§l[COMPASS] §fBússola apontando para §a" + players.getName());
								return;
							}
						}
					}
				player.setCompassTarget(new Location(player.getWorld(), 0, 65, 0));
				player.sendMessage("§c§l[COMPASS] §fNão encontramos nenhum jogador, bússola apontando para o feast.");
			}
		if (event.hasBlock() && event.getClickedBlock().getType().name().contains("SIGN")
				&& event.getAction().name().contains("RIGHT")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0).equals("§8§l[-§fx§8§l-]") && sign.getLine(2).equals("§e§l[CLIQUE]")
					&& sign.getLine(3).equals("§8§l[-§fx§8§l-]")) {
				event.setCancelled(true);

				if (sign.getLine(1).equals("§aSopas!")) {
					Inventory inv = Bukkit.createInventory(null, 54, "");
					for (int i = 0; i < 54; i++)
						inv.addItem(new Stack(Material.MUSHROOM_SOUP));
					player.openInventory(inv);
				} else if (sign.getLine(1).equals("§aRecraft!")) {
					Inventory inv = Bukkit.createInventory(null, 9, "");

					inv.setItem(3, new Stack(Material.BOWL, 64).display("§7» §fRecraft").lore(""));
					inv.setItem(4, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft").lore(""));
					inv.setItem(5, new Stack(Material.RED_MUSHROOM, 64).display("§7» Recraft").lore(""));

					player.openInventory(inv);
				} else if (sign.getLine(1).equals("§aPoção!")) {
					Inventory inv = Bukkit.createInventory(null, 54, "");
					for (int i = 0; i < 54; i++)
						inv.addItem(new Stack(Material.POTION, 1, 16421).display("§7» §fPoção"));
					player.openInventory(inv);
				}
			}
		}
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItemDrop().getItemStack();
		if (VapoxPvP.hasKit(player) && (item.getType().name().contains("_SWORD") || item.getType() == Material.COMPASS))
			event.setCancelled(true);
	}
}