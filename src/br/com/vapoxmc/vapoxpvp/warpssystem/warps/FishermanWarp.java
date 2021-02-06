package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class FishermanWarp extends Warp implements Listener {

	private final HashMap<UUID, Long> cooldownMap;
	private List<Location> randomLocations;
	public static final Stack ROD = new Stack(Material.FISHING_ROD).display("§aVareta! §7(§f§lFisherman§7)")
			.lore("§fItem de: §a§lFISHERMAN§f.");

	public FishermanWarp() {
		super("Fisherman",
				"Venha se divertir jogando seus amigos no void, a habilidade é essencial para matar e sorte para não morrer.",
				new Stack(Material.FISHING_ROD), null);
		this.cooldownMap = new HashMap<>();
		this.randomLocations = Arrays.asList(new Location(Bukkit.getWorlds().get(0), 8002, 100, 7976, 0, 0),
				new Location(Bukkit.getWorlds().get(0), 8026, 100, 8032, 150, 0),
				new Location(Bukkit.getWorlds().get(0), 7987, 100, 8035, 200, 0),
				new Location(Bukkit.getWorlds().get(0), 7970, 100, 8015, 250, 0),
				new Location(Bukkit.getWorlds().get(0), 7979, 100, 7965, 320, 0),
				new Location(Bukkit.getWorlds().get(0), 7992, 100, 7997, 275, 0));
	}

	public Location getLocation() {
		return this.getRandomLocations().get(new Random().nextInt(this.getRandomLocations().size()));
	}

	public List<Location> getRandomLocations() {
		return randomLocations;
	}

	public boolean hasCooldown(Player player) {
		return this.cooldownMap.containsKey(player.getUniqueId());
	}

	public String getRemaingCooldown(Player player) {
		long time = this.getCooldown(player);
		if (time <= 0)
			time = 1;
		return time + " segundo" + (time > 1 ? "s" : "");
	}

	public long getCooldown(Player player) {
		long time = System.currentTimeMillis() + 1000L;
		if (this.hasCooldown(player))
			time = this.cooldownMap.get(player.getUniqueId());
		time = (time - System.currentTimeMillis()) / 1000L;
		if (time <= 0)
			this.removeCooldown(player);
		return time;
	}

	public void addCooldown(Player player, int seconds) {
		this.cooldownMap.put(player.getUniqueId(), (seconds * 1000L) + System.currentTimeMillis());
	}

	public void removeCooldown(Player player) {
		this.cooldownMap.remove(player.getUniqueId());
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(0, ROD);

		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 12000, 10));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		Player player = event.getPlayer();
		if (event.getWarp() instanceof FishermanWarp)
			VapoxPvP.removeProtection(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		if (event.getEntity() instanceof Player && system.getWarp((Player) event.getEntity()) instanceof FishermanWarp
				&& (event.getCause().name().contains("FIRE") || event.getCause().name().contains("LAVA")))
			event.setDamage(0.025D);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof FishermanWarp && event.hasItem()
				&& event.getItem().isSimilar(ROD.toItemStack()) && event.getAction().name().contains("RIGHT")
				&& this.hasCooldown(player)) {
			event.setCancelled(true);
			player.sendMessage(
					" §c• §fAguarde §c" + this.getRemaingCooldown(player) + " §fpara usar a §c§nVara§r §fnovamente.");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerFish(PlayerFishEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof FishermanWarp && event.getCaught() instanceof Player) {
			Player caught = (Player) event.getCaught();
			if (system.getWarp(caught) instanceof FishermanWarp) {
				if (player.getName().equals(caught.getName())) {
					event.setCancelled(true);
					player.sendMessage(" §c• §fVoCê não pode puxar a §cvocê §fmesmo.");
				} else {
					caught.teleport(player.getLocation(), TeleportCause.PLUGIN);
					if (!VapoxPvP.isInCombat(caught))
						VapoxPvP.addCombat(caught, player);
					else
						VapoxPvP.setCombatTime(caught, 10);
					this.addCooldown(caught, 1);
					caught.sendMessage(" §e• §fVocê foi pescado por §e" + player.getName() + "§f!");
					player.sendMessage(" §e• §fVocê pescou §e" + caught.getName() + "§f!");
				}

				player.getItemInHand().setDurability((short) 0);
				player.updateInventory();
			} else {
				event.setCancelled(true);
				player.sendMessage(" §c• §fEste Jogador não está na §c§nWarp Fisherman§f!");
			}
		}
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof FishermanWarp && event.getTo().getY() <= 65D)
			player.damage(player.getMaxHealth());
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof FishermanWarp
				&& event.getItemDrop().getItemStack().isSimilar(ROD.toItemStack()))
			event.setCancelled(true);
	}
}