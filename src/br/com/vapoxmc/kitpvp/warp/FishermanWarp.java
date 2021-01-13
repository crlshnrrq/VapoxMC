package br.com.vapoxmc.kitpvp.warp;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class FishermanWarp extends Warp implements Listener {

	private List<Location> randomLocations;
	public static final Stack ROD = new Stack(Material.FISHING_ROD).display("§aVareta! §7(§f§lFisherman§7)")
			.lore("§fItem de: §a§lFISHERMAN§f.");

	public FishermanWarp() {
		super("Fisherman", new Stack(Material.FISHING_ROD), null);
		this.randomLocations = Arrays.asList(new Location(Bukkit.getWorlds().get(0), 8002, 100, 7976, 0, 0),
				new Location(Bukkit.getWorlds().get(0), 8026, 100, 8032, 150, 0),
				new Location(Bukkit.getWorlds().get(0), 7987, 100, 8035, 200, 0),
				new Location(Bukkit.getWorlds().get(0), 7970, 100, 8015, 250, 0),
				new Location(Bukkit.getWorlds().get(0), 7979, 100, 7965, 320, 0),
				new Location(Bukkit.getWorlds().get(0), 7992, 100, 7997, 275, 0));
	}

	public Location getLocation() {
		return this.getRandomLocations().stream().findAny().get();
	}

	public List<Location> getRandomLocations() {
		return randomLocations;
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(0, ROD);

		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12000, 10));
		VapoxPvP.removeProtection(player);
	}

	@EventHandler
	private void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof FishermanWarp && event.getCaught() instanceof Player) {
			Player caught = (Player) event.getCaught();
			if (VapoxPvP.getWarp(caught) instanceof FishermanWarp) {
				if (player.getName().equals(caught.getName())) {
					event.setCancelled(true);
					player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fVocê não pode fisgar a si mesmo!");
				} else {
					caught.teleport(player.getLocation());
					caught.sendMessage(
							"§a§l[" + this.getName().toUpperCase() + "] §fVocê foi pescado por §a" + player.getName());
					player.sendMessage(
							"§a§l[" + this.getName().toUpperCase() + "] §fVocê pescou o jogador §a" + caught.getName());
				}

				player.getItemInHand().setDurability((short) 0);
				player.updateInventory();
			} else {
				event.setCancelled(true);
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fEsse jogador não está em pvp!");
			}
		}
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof FishermanWarp
				&& event.getItemDrop().getItemStack().isSimilar(ROD.toItemStack()))
			event.setCancelled(true);
	}
}