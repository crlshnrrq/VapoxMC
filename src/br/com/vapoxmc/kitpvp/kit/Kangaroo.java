package br.com.vapoxmc.kitpvp.kit;

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Kangaroo extends Kit implements Listener {

	public static final Map<UUID, Integer> kangarooMap = new HashMap<>();
	public static final List<UUID> fallList = new ArrayList<>();
	public static final Stack ROCKET = new Stack(Material.FIREWORK).display("§aKangaroo §7(§fClique§7)")
			.lore("§7Clique com o §6direito §7para pular duas vezes.");

	public Kangaroo() {
		super("Kangaroo", "Pule que nem um canguru!", new Stack(Material.FIREWORK), ROCKET);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(ROCKET);
	}

	@EventHandler(priority = EventPriority.HIGH)
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Kangaroo && event.hasItem()
				&& event.getItem().isSimilar(ROCKET.toItemStack())) {
			event.setCancelled(true);
			if (!VapoxPvP.isInCombat(player)) {
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
					player.setVelocity(new Vector(player.getVelocity().getX(), 1.0, player.getVelocity().getZ()));
					kangarooMap.put(player.getUniqueId(), 1);
				} else if (kangarooMap.get(player.getUniqueId()) != 2) {
					player.setVelocity(new Vector(player.getVelocity().getX(), 1.0, player.getVelocity().getZ()));
					kangarooMap.put(player.getUniqueId(), 2);
				}
			} else
				player.sendMessage("§c§l[COMBATE] §fVocê não pode utilizar o kangaroo em combate!");
		}
	}

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Kangaroo) {
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

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL && event.getDamage() > 7D) {
			Player player = (Player) event.getEntity();
			if (VapoxPvP.getKit(player) instanceof Kangaroo)
				event.setDamage(3.5D);
		}
	}
}