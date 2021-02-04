package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Sets;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

@SuppressWarnings("deprecation")
public final class FlashKit extends Kit implements Listener {

	public static final Stack VELOCIMETER = new Stack(Material.REDSTONE_TORCH_ON).display("§7» §fVelocímetro §7«");
	private HashSet<Byte> ignoreBlocks = new HashSet<>();

	public FlashKit() {
		super("Flash", "Avance no tempo e corra distâncias em alta velocidade.", new Stack(Material.REDSTONE_TORCH_ON),
				true);
		this.ignoreBlocks = Sets.newHashSet((byte) Material.AIR.getId(), (byte) Material.FIRE.getId(),
				(byte) Material.SNOW.getId(), (byte) Material.RED_MUSHROOM.getId(),
				(byte) Material.BROWN_MUSHROOM.getId(), (byte) Material.VINE.getId(),
				(byte) Material.WATER_LILY.getId(), (byte) Material.LONG_GRASS.getId(), (byte) Material.SIGN.getId(),
				(byte) Material.SIGN_POST.getId(), (byte) Material.WALL_SIGN.getId(), (byte) Material.WATER.getId(),
				(byte) Material.STATIONARY_WATER.getId(), (byte) Material.LAVA.getId(),
				(byte) Material.STATIONARY_LAVA.getId(), (byte) Material.TORCH.getId(),
				(byte) Material.REDSTONE_TORCH_ON.getId(), (byte) Material.REDSTONE_TORCH_OFF.getId());
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(VELOCIMETER);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getKit(player) instanceof FlashKit && event.hasItem()
				&& event.getItem().isSimilar(VELOCIMETER.toItemStack()) && event.getAction().name().contains("RIGHT")) {
			event.setCancelled(true);
			if (!this.hasCooldown(player)) {
				this.addCooldown(player, 40);
				List<Block> blocks = player.getLastTwoTargetBlocks(ignoreBlocks, 100);
				if (blocks.size() > 1 && blocks.get(1).getType() != Material.AIR) {
					Block block = blocks.get(0);
					if (player.getLocation().distance(block.getLocation()) > 5D) {
						player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
						Location loc = block.getLocation().clone().add(0.5, 0.5, 0.5);
						loc.setYaw(player.getLocation().getYaw());
						loc.setPitch(player.getLocation().getPitch());
						player.teleport(loc);
						player.playSound(loc, Sound.ENDERMAN_TELEPORT, 1F, 1F);
						player.getWorld().strikeLightningEffect(loc);
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 1), true);
					}
				}
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ this.getRemaingCooldown(player) + " §fpara usar novamente!");
		}
	}
}