package br.com.vapoxmc.kitpvp.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import br.com.vapoxmc.kitpvp.warp.EventoWarp;
import br.com.vapoxmc.kitpvp.warp.FPSWarp;
import br.com.vapoxmc.kitpvp.warp.FishermanWarp;
import br.com.vapoxmc.kitpvp.warp.KnockbackWarp;
import br.com.vapoxmc.kitpvp.warp.PotPvPWarp;

public final class CombatLogListeners implements Listener {

	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.isInCombat(player)) {
			Player enemy = VapoxPvP.getCombatEnemy(player);
			int points = VapoxUtils.getRandomPoints(), coins = VapoxUtils.getRandomCoins();
			PlayerAccount.addPontos(enemy, points);
			PlayerAccount.addMoedas(enemy, coins);
			VapoxPvP.removeCombat(enemy);
			enemy.sendMessage("§c§l[COMBATE] §c" + player.getName() + " §fdeslogou em combate e você recebeu a kill!");
			enemy.sendMessage("§c§l[MORTE] §fVocê matou o jogador §c" + player.getName() + "§f.");
			if (enemy.hasPermission("ciphen.doublexp")) {
				PlayerAccount.addMoedas(enemy, coins);
				enemy.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + (coins * 2) + " §fmoedas! §a§l(x2)");
			} else
				enemy.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + coins + " §fmoedas!");
			enemy.sendMessage("§a§l[PONTOS] §fVocê recebeu §a" + points + " §fpontos!");
		}
	}

	@EventHandler
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.isInCombat(player)) {
			List<String> commands = Arrays.asList("/spawn", "/resetkit", "/admin", "/kit", "/warp");
			if (commands.contains(event.getMessage().split(" ")[0].toLowerCase())) {
				event.setCancelled(true);
				player.sendMessage("§c§l[COMBATE] §fVocê não pode usar comandos em combate.");
			}
		}
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity(), damager = (Player) event.getDamager();
			if ((VapoxPvP.hasKit(player) && VapoxPvP.hasKit(damager))
					|| (VapoxPvP.getWarp(player) instanceof KnockbackWarp
							&& VapoxPvP.getWarp(damager) instanceof KnockbackWarp)
					|| (VapoxPvP.getWarp(player) instanceof FPSWarp && VapoxPvP.getWarp(damager) instanceof FPSWarp)
					|| (VapoxPvP.getWarp(player) instanceof EventoWarp
							&& VapoxPvP.getWarp(damager) instanceof EventoWarp)
					|| (VapoxPvP.getWarp(player) instanceof FishermanWarp
							&& VapoxPvP.getWarp(damager) instanceof FishermanWarp)
					|| (VapoxPvP.getWarp(player) instanceof PotPvPWarp
							&& VapoxPvP.getWarp(damager) instanceof PotPvPWarp)) {
				if (!VapoxPvP.isInCombat(damager))
					VapoxPvP.addCombat(damager, player);
				else
					VapoxPvP.setCombatTime(damager, 30);
			}
		}
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity(), killer = player.getKiller();
		if (VapoxPvP.isInCombat(player))
			VapoxPvP.removeCombat(player);
		if (killer != null && killer != player && VapoxPvP.isInCombat(killer))
			VapoxPvP.removeCombat(killer);
	}
}