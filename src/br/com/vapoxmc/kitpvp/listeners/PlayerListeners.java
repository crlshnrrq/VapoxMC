package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import br.com.vapoxmc.kitpvp.warp.FPSWarp;
import br.com.vapoxmc.kitpvp.warp.UMvUMWarp;
import br.com.vapoxmc.kitpvp.warp.Warp;

public final class PlayerListeners implements Listener {

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);

		if (!PlayerAccount.existAccount(player.getName()))
			PlayerAccount.createAccount(player);

		Warp warp = VapoxPvP.getDefaultWarp();
		VapoxPvP.setWarp(player, warp);

		try {
			player.teleport(new Location(Bukkit.getWorlds().get(0), 1000, 102, 1000));
		} catch (Exception ex) {
		}

		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.teleport(warp.getLocation()), 10L);
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> warp.giveItems(player), 20L);

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + player.getName() + " clear");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + player.getName() + " prefix &7");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + player.getName() + " priority 19");
		player.setDisplayName("§7" + player.getName());

		if (player.hasPermission("ciphen.comandos.gamemode"))
			player.setGameMode(GameMode.CREATIVE);

		for (int i = 0; i < 200; i++)
			player.sendMessage(" ");

		player.sendMessage("§eConectado ao servidor §f\"kitpvp\"§e.");
		player.sendMessage("§7Olá, seja bem-vindo ao §e§lVapox§7.");
		player.sendMessage("§7Clique com o direito no §ebaú §7para selecionar um §ekit§7.");
		player.sendMessage("§7Utilize §e/discord §7para acessar nosso discord.");
		player.sendMessage("§7Utilize §e/score §7para alterar sua §escoreboard§7.");
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity(), killer = player.getKiller();
		event.setDeathMessage(null);
		event.getDrops().clear();
		event.setNewExp(0);

		if (killer != null && killer != player) {
			if (VapoxPvP.getWarp(player) instanceof UMvUMWarp) {
				UMvUMWarp warp = (UMvUMWarp) VapoxPvP.getWarp(player);
				if (warp.hasEnemy(player)) {
					Player enemy = warp.getEnemy(player);

					warp.removeEnemy(player);
					warp.removeEnemy(enemy);
					PlayerAccount.getGeral().addAbate(enemy).addMorte(player);
					PlayerAccount.get1v1().addVitoria(enemy).addDerrota(player);
					player.sendMessage("§c§l[1V1] §fVocê perdeu a batalha contra §c" + enemy.getName() + "§f.");

					int coins = VapoxUtils.getRandomCoins(), points = VapoxUtils.getRandomPoints();
					PlayerAccount.getGeral().addMoedas(enemy, coins).addPontos(enemy, points);
					enemy.sendMessage("§c§l[1V1] §fVocê venceu a batalha contra §a" + player.getName() + "§f.");
					if (enemy.hasPermission("ciphen.doublexp")) {
						PlayerAccount.getGeral().addMoedas(enemy, coins);
						enemy.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + (coins * 2) + " §fmoedas! §a§l(x2)");
					} else
						enemy.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + coins + " §fmoedas!");
					enemy.sendMessage("§a§l[PONTOS] §fVocê recebeu §a" + points + " §fpontos!");

					Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
						VapoxPvP.setWarp(player, warp);
						VapoxPvP.setWarp(enemy, warp);
					}, 10L);
				}
			} else {
				if (VapoxPvP.isInCombat(killer))
					VapoxPvP.removeCombat(killer);
				int coins = VapoxUtils.getRandomCoins(), points = VapoxUtils.getRandomPoints();
				PlayerAccount.getGeral().addMoedas(killer, coins).addPontos(killer, points).addAbate(killer)
						.addMorte(player);
				killer.sendMessage("§c§l[MORTE] §fVocê matou o jogador §c" + player.getName() + "§f.");
				if (killer.hasPermission("ciphen.doublexp")) {
					PlayerAccount.getGeral().addMoedas(killer, coins);
					killer.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + (coins * 2) + " §fmoedas! §a§l(x2)");
				} else
					killer.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + coins + " §fmoedas!");
				killer.sendMessage("§a§l[PONTOS] §fVocê recebeu §a" + points + " §fpontos!");

				if (VapoxPvP.getWarp(killer) instanceof FPSWarp) {
					PlayerInventory inv = killer.getInventory();
					if (inv.getHelmet() != null)
						inv.getHelmet().setDurability((short) -1);
					if (inv.getChestplate() != null)
						inv.getChestplate().setDurability((short) -1);
					if (inv.getLeggings() != null)
						inv.getLeggings().setDurability((short) -1);
					if (inv.getBoots() != null)
						inv.getBoots().setDurability((short) -1);
					killer.updateInventory();
					killer.sendMessage("§a§l[FPS] §fSua armadura foi §a§lregenerada§f.");
				}

				VapoxPvP.removeKit(player);
				if (VapoxPvP.isInCombat(player))
					VapoxPvP.removeCombat(player);
				player.sendMessage("§c§l[MORTE] §fVocê morreu para o jogador §c" + killer.getName() + "§f.");
			}
			if (PlayerAccount.getGeral().getKillStreak(player) > 5)
				Bukkit.broadcastMessage("§c§l[KILLSTREAK] §fO jogador §c" + player.getName()
						+ " §fperdeu o seu killstreak de §c§l" + PlayerAccount.getGeral().getKillStreak(player)
						+ " §fpara §c" + killer.getName() + "§f!");
			PlayerAccount.getGeral().setKillStreak(player, 0);

			int killStreak = PlayerAccount.getGeral().addKillStreak(killer).getKillStreak(killer);
			if (killStreak % 5 == 0)
				Bukkit.broadcastMessage("§e§l[KILLSTREAK] §fO jogador §e" + player.getName()
						+ " §fatingiu um killstreak de §e§l" + killStreak + " §fabates!");
		} else
			player.sendMessage("§c§l[MORTE] §fVocê morreu por §ccausas desconhecidas§f, portanto §cnão foi contado§f.");
	}

	@EventHandler
	private void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Warp warp = VapoxPvP.getDefaultWarp();

		VapoxPvP.setWarp(player, warp);
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.teleport(warp.getLocation()), 10L);
	}

	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);

		PlayerInventory inv = player.getInventory();
		inv.setArmorContents(null);
		inv.clear();

		((UMvUMWarp) VapoxPvP.getWarpByName("1v1")).removeEnemy(player);
		VapoxPvP.removeKit(player);
	}

	@EventHandler
	private void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		VapoxPvP.removeKitCooldown(player);
	}
}