package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class KangarooKit extends Kit implements Listener {

	public static final Map<UUID, Integer> kangarooMap = new HashMap<>();
	public static final List<UUID> fallList = new ArrayList<>();
	public static final Stack ROCKET = new Stack(Material.FIREWORK).display("§7» §fFoguete §7«");

	public KangarooKit() {
		super("Kangaroo", "Pule que nem um canguru.", new Stack(Material.FIREWORK), true, ROCKET);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(ROCKET);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof KangarooKit)
				this.addCooldown(player, 7);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof KangarooKit && event.hasItem()
				&& event.getItem().isSimilar(ROCKET.toItemStack())) {
			event.setCancelled(true);
			if (!this.hasCooldown(player)) {
				if (player.isSneaking()) {
					if (!fallList.contains(player.getUniqueId())) {
						player.setVelocity(player.getLocation().getDirection().multiply(1.2));
						player.setVelocity(new Vector(player.getVelocity().getX(), 0.5, player.getVelocity().getZ()));
						kangarooMap.put(player.getUniqueId(), 1);
					} else if (kangarooMap.get(player.getUniqueId()) != 2) {
						player.setVelocity(player.getLocation().getDirection().multiply(1.2));
						player.setVelocity(new Vector(player.getVelocity().getX(), 0.5, player.getVelocity().getZ()));
						kangarooMap.put(player.getUniqueId(), 2);
					}
				} else if (!fallList.contains(player.getUniqueId())) {
					player.setVelocity(new Vector(player.getVelocity().getX(), 0.9, player.getVelocity().getZ()));
					kangarooMap.put(player.getUniqueId(), 1);
				} else if (kangarooMap.get(player.getUniqueId()) != 2) {
					player.setVelocity(new Vector(player.getVelocity().getX(), 0.9, player.getVelocity().getZ()));
					kangarooMap.put(player.getUniqueId(), 2);
				}
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof KangarooKit) {
			Block block = event.getFrom().getBlock();
			if (block.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				if (block.getType() != Material.AIR) {
					Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(),
							() -> kangarooMap.put(player.getUniqueId(), 0), 1L);
					fallList.remove(player.getUniqueId());
				}
				if (block.getRelative(BlockFace.DOWN).getType() != Material.AIR || block.getType() != Material.AIR) {
					Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(),
							() -> kangarooMap.put(player.getUniqueId(), 0), 1L);
					fallList.remove(player.getUniqueId());
				}
			} else
				fallList.add(player.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL && event.getDamage() > 7D) {
			Player player = (Player) event.getEntity();
			if (system.getKit(player) instanceof KangarooKit)
				event.setDamage(7D);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof KangarooKit
				&& event.getItemDrop().getItemStack().isSimilar(ROCKET.toItemStack()))
			event.setCancelled(true);
	}
}