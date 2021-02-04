package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.generalsystem.events.PlayerKillEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class FPSWarp extends Warp implements Listener {

	public static final Stack SWORD = new Stack(Material.DIAMOND_SWORD).ench(Enchantment.DAMAGE_ALL, 1)
			.display("§aEspada §7(§f§lFPS§7)").lore("§7Clique com o §6esquerdo §7para atacar um oponente.");

	public FPSWarp() {
		super("FPS", "Jogue em um local mais limpo e simples que estabiliza o FPS dos jogadores.",
				new Stack(Material.DIAMOND_SWORD), new Location(Bukkit.getWorlds().get(0), 6000, 115, 6000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setHelmet(new Stack(Material.IRON_HELMET));
		inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
		inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
		inv.setBoots(new Stack(Material.IRON_BOOTS));

		inv.setItem(13, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft §7«"));
		inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft §7«"));
		inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft §7«"));

		inv.setItem(0, SWORD);

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		VapoxPvP.addProtection(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof FPSWarp
				&& event.getItemDrop().getItemStack().isSimilar(SWORD.toItemStack()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerKill(PlayerKillEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer(), killer = player.getKiller();
		if (killer != null && killer != player && system.getWarp(player) instanceof FPSWarp
				&& system.getWarp(killer) instanceof FPSWarp) {
			PlayerInventory inv = killer.getInventory();
			boolean helmet = false, chestplate = false, leggings = false, boots = false;
			for (ItemStack armor : inv.getArmorContents()) {
				if (armor == null || armor.getType() == Material.AIR)
					continue;
				String name = armor.getType().name();

				if (name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS")
						|| name.contains("BOOTS"))
					armor.setDurability((short) -1);

				if (name.contains("HELMET"))
					helmet = true;
				if (name.contains("CHESTPLATE"))
					chestplate = true;
				if (name.contains("LEGGINGS"))
					leggings = true;
				if (name.contains("BOOTS"))
					boots = true;
			}
			for (ItemStack items : inv.getContents()) {
				if (items == null || items.getType() == Material.AIR)
					continue;
				String name = items.getType().name();

				if (name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS")
						|| name.contains("BOOTS"))
					items.setDurability((short) -1);

				if (name.contains("HELMET"))
					helmet = true;
				if (name.contains("CHESTPLATE"))
					chestplate = true;
				if (name.contains("LEGGINGS"))
					leggings = true;
				if (name.contains("BOOTS"))
					boots = true;
			}
			if (!helmet)
				inv.setHelmet(new Stack(Material.IRON_HELMET));
			if (!chestplate)
				inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
			if (!leggings)
				inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
			if (!boots)
				inv.setBoots(new Stack(Material.IRON_BOOTS));

			killer.updateInventory();
			killer.sendMessage("§a§l[FPS] §fSua armadura foi §a§lregenerada§f.");
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
		if (system.getWarp(player) instanceof FPSWarp) {
			if (VapoxPvP.hasProtection(player) && this.getLocation().distance(event.getTo()) > 10) {
				VapoxPvP.removeProtection(player);
				player.setFallDistance(-20F);
				player.sendMessage("§e• §fVocê perdeu a §e§nProteção de Spawn§f.");
			}
		}
	}
}