package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Viking extends Kit implements Listener {

	public static final Stack VIKING_AXE = new Stack(Material.STONE_AXE).display("§aMachado §7(§f§lViking§7)")
			.lore("§7Clique com o §6esquerdo §7para atacar um oponente.");

	public Viking() {
		super("Viking", "De mais dano com seu machado!", new Stack(Material.STONE_AXE), VIKING_AXE);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setItem(0, VIKING_AXE);
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (VapoxPvP.getKit(damager) instanceof Viking && damager.getItemInHand().getType().name().contains("_AXE"))
				event.setDamage(event.getDamage() + 2D);
		}
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Viking
				&& event.getItemDrop().getItemStack().isSimilar(VIKING_AXE.toItemStack()))
			event.setCancelled(true);
	}
}