package br.com.vapoxmc.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;

public final class StomperKit extends Kit implements Listener {

	public StomperKit() {
		super("Stomper", "Esmague os seus oponentes!", new Stack(Material.IRON_BOOTS));
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL && event.getDamage() > 6D) {
			Player player = (Player) event.getEntity();
			if (VapoxPvP.getKit(player) instanceof StomperKit) {
				player.getNearbyEntities(3.5D, 1D, 3.5D).stream().filter(entities -> entities instanceof Player)
						.forEach(entities -> {
							Player target = (Player) entities;
							if (target.isSneaking() || VapoxPvP.getKit(target) instanceof AntiStomperKit)
								target.damage(4D, player);
							else {
								target.setHealth(0D);
								target.sendMessage(
										"§c§l[MORTE] §fVocê morreu para o jogador §c" + player.getName() + "§f.");

								int moedas = VapoxUtils.getRandomCoins(), pontos = VapoxUtils.getRandomPoints();
								PlayerAccount.getGeral().addMoedas(player, moedas).addPontos(player, pontos);
								player.sendMessage("§c§l[MORTE] §fVocê matou o jogador §c" + target.getName() + "§f.");
								player.sendMessage("§a§l[PONTOS] §fVocê recebeu §a" + pontos + " §fpontos!");
								if (player.hasPermission("ciphen.doublexp")) {
									PlayerAccount.getGeral().addPontos(player, pontos);
									player.sendMessage(
											"§a§l[MOEDAS] §fVocê recebeu §a" + (moedas * 2) + " §fmoedas! §a§l(X2)");
								} else
									player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + moedas + " §fmoedas!");
							}

							player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 2F, 1F);
						});
				event.setDamage(4D);
			}
		}
	}
}