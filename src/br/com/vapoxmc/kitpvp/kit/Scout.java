package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class Scout extends Kit {

	public static final Stack POTION = new Stack(Material.WOOL, 1, 3).display("§aPoção §7(§f§lScout§7)")
			.lore("§7Clique com o §6direito §7para receber os efeitos.");

	public Scout() {
		super("Scout", "Aumente sua velocidade com a poção!", new Stack(Material.GOLD_BOOTS),
				new Stack(Material.LEATHER_BOOTS).display("§aBota §7(§f§lScout§7)")
						.lore("§7Utilize as §6botas §7para correr!"),
				POTION);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setBoots(new Stack(Material.LEATHER_BOOTS).display("§aBota §7(§f§lScout§7)")
				.lore("§7Utilize as §6botas §7para correr!"));
		inv.addItem();
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getKit(player) instanceof Scout && event.getAction().name().contains("RIGHT") && event.hasItem()
				&& event.getItem().isSimilar(POTION.toItemStack())) {
			event.setCancelled(true);
			if (!VapoxPvP.hasKitCooldown(player)) {
				VapoxPvP.addKitCooldown(player, 15);
				player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 1F, 1F);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1), true);
				player.sendMessage("§a§l[" + this.getName().toUpperCase()
						+ "] §fVocê recebeu efeito de §avelocidade §fpor §a5 segundos§f!");
			} else
				player.sendMessage("§c§l[" + this.getName().toUpperCase() + "] §fAguarde §c"
						+ VapoxPvP.getFormattedKitCooldown(player) + " §fpara usar novamente!");
		}
	}
}