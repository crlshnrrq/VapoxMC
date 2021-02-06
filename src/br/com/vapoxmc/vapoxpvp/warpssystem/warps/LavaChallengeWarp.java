package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class LavaChallengeWarp extends Warp implements Listener {

	public LavaChallengeWarp() {
		super("Lava Challenge", "Treine seu refil e recraft, além de conseguir moedas e pontos ao concluir os níveis.",
				new Stack(Material.LAVA_BUCKET), new Location(Bukkit.getWorlds().get(0), 150000, 65, 150000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(13, new Stack(Material.BOWL, 64).display("§7» §fRecraft §7«"));
		inv.setItem(14, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft §7«"));
		inv.setItem(15, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft §7«"));

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));
	}

	private boolean isAbusing(Player player) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return false;
		return player.isFlying() || player.getAllowFlight() || player.getGameMode() == GameMode.CREATIVE
				|| player.getGameMode() == GameMode.SPECTATOR || !player.getActivePotionEffects().isEmpty()
				|| !(system.getWarp(player) instanceof LavaChallengeWarp) || VapoxPvP.hasAdmin(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		if (event.getWarp() instanceof LavaChallengeWarp) {
			Player player = event.getPlayer();
			VapoxPvP.removeProtection(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		if (system.getWarp(player) instanceof LavaChallengeWarp && event.getNewGameMode() != GameMode.SURVIVAL) {
			event.setCancelled(true);
			player.sendMessage(
					" §c• §fVocê não pode §calterar o gamemode §festando na Warp §c§n" + this.getName() + "§f.");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		if (system.getWarp(player) instanceof LavaChallengeWarp && event.getCause() == TeleportCause.COMMAND) {
			event.setCancelled(true);
			player.sendMessage(" §c• §fVocê não pode §cteleportar §festando na Warp §c§n" + this.getName() + "§f.");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		if (event.getEntity() instanceof Player
				&& system.getWarp((Player) event.getEntity()) instanceof LavaChallengeWarp
				&& (event.getCause().name().contains("FIRE") || event.getCause().name().contains("LAVA")))
			return;
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("staff.customsigns") && event.getLine(0).equalsIgnoreCase("Lava Challenge")) {
			if (event.getLine(1).equalsIgnoreCase("Facil")) {
				event.setLine(0, "§6§lDESAFIO");
				event.setLine(1, "§7Nível:");
				event.setLine(2, "§a§lFácil");
				event.setLine(3, "§7(Clique)");
				player.sendMessage(" §e• §fPlaca de §e§nNível Fácil§r §fcriada com sucesso.");
			} else if (event.getLine(1).equalsIgnoreCase("Medio")) {
				event.setLine(0, "§6§lDESAFIO");
				event.setLine(1, "§7Nível:");
				event.setLine(2, "§e§lMédio");
				event.setLine(3, "§7(Clique)");
				player.sendMessage(" §e• §fPlaca de §e§nNível Médio§r §fcriada com sucesso.");
			} else if (event.getLine(1).equalsIgnoreCase("Dificil")) {
				event.setLine(0, "§6§lDESAFIO");
				event.setLine(1, "§7Nível:");
				event.setLine(2, "§c§lDíficil");
				event.setLine(3, "§7(Clique)");
				player.sendMessage(" §e• §fPlaca de §e§nNível Difícil§r §fcriada com sucesso.");
			} else if (event.getLine(1).equalsIgnoreCase("Insano")) {
				event.setLine(0, "§6§lDESAFIO");
				event.setLine(1, "§7Nível:");
				event.setLine(2, "§8§lINSANO");
				event.setLine(3, "§7(Clique)");
				player.sendMessage(" §e• §fPlaca de §e§nNível Insano§r §fcriada com sucesso.");
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (event.hasBlock() && event.getClickedBlock().getType().name().contains("SIGN")
				&& event.getAction().name().contains("RIGHT")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0).equals("§6§lDESAFIO") && sign.getLine(1).equals("§7Nível:")
					&& sign.getLine(3).equals("§7(Clique)") && !this.isAbusing(player)) {
				if (sign.getLine(2).equals("§a§lFácil")) {
					system.setWarp(player, this);
					Bukkit.broadcastMessage(" §b• §7" + player.getName()
							+ " §fcompletou o §aNível Fácil §fda Warp §b§nLava Challenge§f!");

					int coins = 10, points = 1;
					PlayerAccount.addLavaChallengeFacil(player);
					PlayerAccount.addMoedas(player, coins);
					PlayerAccount.addPontos(player, points);
					if (player.hasPermission("coinsbooster.x2")) {
						PlayerAccount.addMoedas(player, coins);
						player.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
					} else
						player.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
					player.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");
				} else if (sign.getLine(2).equals("§e§lMédio")) {
					system.setWarp(player, this);
					Bukkit.broadcastMessage(" §b• §7" + player.getName()
							+ " §fcompletou o §eNível Médio §fda Warp §b§nLava Challenge§f!");

					int coins = 50, points = 5;
					PlayerAccount.addLavaChallengeMedio(player);
					PlayerAccount.addMoedas(player, coins);
					PlayerAccount.addPontos(player, points);
					if (player.hasPermission("coinsbooster.x2")) {
						PlayerAccount.addMoedas(player, coins);
						player.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
					} else
						player.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
					player.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");
				} else if (sign.getLine(2).equals("§c§lDíficil")) {
					system.setWarp(player, this);
					Bukkit.broadcastMessage(" §b• §7" + player.getName()
							+ " §fcompletou o §cNível Difícil §fda Warp §b§nLava Challenge§f!");

					int coins = 100, points = 10;
					PlayerAccount.addLavaChallengeDificil(player);
					PlayerAccount.addMoedas(player, coins);
					PlayerAccount.addPontos(player, points);
					if (player.hasPermission("coinsbooster.x2")) {
						PlayerAccount.addMoedas(player, coins);
						player.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
					} else
						player.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
					player.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");
				} else if (sign.getLine(2).equals("§8§lINSANO")) {
					system.setWarp(player, this);
					Bukkit.broadcastMessage(" §b• §7" + player.getName()
							+ " §fcompletou o §8Nível Insano §fda Warp §b§nLava Challenge§f!");

					int coins = 150, points = 20;
					PlayerAccount.addLavaChallengeInsano(player);
					PlayerAccount.addMoedas(player, coins);
					PlayerAccount.addPontos(player, points);
					if (player.hasPermission("coinsbooster.x2")) {
						PlayerAccount.addMoedas(player, coins);
						player.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
					} else
						player.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
					player.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");
				}
			}
		}
	}
}