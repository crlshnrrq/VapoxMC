package br.com.vapoxmc.kitpvp.kit;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

@SuppressWarnings("deprecation")
public final class Thor extends Kit implements Listener {

	public static final Stack MJOLNIR = new Stack(Material.GOLD_AXE).display("§aEspada §7(§f§lThor§7)")
			.lore("§7Clique com o §6direitor §7para jogar raios.");

	public Thor() {
		super("Thor", "Jogue raios com o seu machado!", new Stack(Material.GOLD_AXE), MJOLNIR);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.addItem(MJOLNIR);
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Thor && event.getAction().name().contains("RIGHT") && event.hasItem()
				&& event.getItem().isSimilar(MJOLNIR.toItemStack())) {
			if (!VapoxPvP.hasKitCooldown(player)) {
				VapoxPvP.addKitCooldown(player, 6);
				player.getWorld().strikeLightning(player.getTargetBlock((HashSet<Byte>) null, 10).getLocation());
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ VapoxPvP.getFormattedKitCooldown(player) + " §fpara usar novamente!");
		}
	}
}