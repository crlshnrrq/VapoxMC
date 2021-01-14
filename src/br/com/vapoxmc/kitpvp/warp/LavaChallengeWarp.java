package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class LavaChallengeWarp extends Warp implements Listener {

	public LavaChallengeWarp() {
		super("Lava Challenge", new Stack(Material.LAVA_BUCKET),
				new Location(Bukkit.getWorlds().get(0), 80000, 47, 80000));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(13, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft"));

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));

		VapoxPvP.removeProtection(player);
	}

	private boolean isAbusing(Player player) {
		return player.isFlying() || player.getAllowFlight() || player.getGameMode() == GameMode.CREATIVE
				|| !player.getActivePotionEffects().isEmpty()
				|| !(VapoxPvP.getWarp(player) instanceof LavaChallengeWarp) || VapoxPvP.hasAdmin(player);
	}

	@EventHandler
	private void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof LavaChallengeWarp && event.getNewGameMode() == GameMode.CREATIVE) {
			event.setCancelled(true);
			player.sendMessage("§e§l[CHALLENGE] §fVocê não pode alterar o seu gamemode com o kit §cLavaChallenge§f!");
		}
	}

	@EventHandler
	private void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("ciphen.eventos.placa")) {
			if (event.getLine(0).equalsIgnoreCase("P_Easy")) {
				event.setLine(0, "§6§lCHALLENGE");
				event.setLine(1, "§aNível:");
				event.setLine(2, "§a§lFácil");
				event.setLine(3, "§e§l[CLIQUE!]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			} else if (event.getLine(0).equalsIgnoreCase("P_Medium")) {
				event.setLine(0, "§6§lCHALLENGE");
				event.setLine(1, "§aNível:");
				event.setLine(2, "§e§lMédio");
				event.setLine(3, "§e§l[CLIQUE!]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			} else if (event.getLine(0).equalsIgnoreCase("P_Hard")) {
				event.setLine(0, "§6§lCHALLENGE");
				event.setLine(1, "§aNível:");
				event.setLine(2, "§c§lDíficil");
				event.setLine(3, "§e§l[CLIQUE!]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			} else if (event.getLine(0).equalsIgnoreCase("P_Insane")) {
				event.setLine(0, "§6§lCHALLENGE");
				event.setLine(1, "§aNível:");
				event.setLine(2, "§8§lINSANO");
				event.setLine(3, "§e§l[CLIQUE!]");
				player.sendMessage("§e§l[PLACA] §ePlaca criada com sucesso!");
			}
		}
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasBlock() && event.getClickedBlock().getType().name().contains("SIGN")
				&& event.getAction().name().contains("RIGHT")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0).equals("§6§lCHALLENGE") && sign.getLine(1).equals("§aNível:")
					&& sign.getLine(3).equals("§e§l[CLIQUE!]") && !this.isAbusing(player)) {
				if (sign.getLine(2).equals("§a§lFácil")) {
					VapoxPvP.setWarp(player, this);
					PlayerAccount.addMoedas(player, 300);
					Bukkit.broadcastMessage("§a§l[CHALLENGE] §f" + player.getName()
							+ " §fcompletou o nível §afácil §fdo LavaChallenge!");
					player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a300 §fmoedas de §aLavaChallenge");
				} else if (sign.getLine(2).equals("§e§lMédio")) {
					VapoxPvP.setWarp(player, this);
					PlayerAccount.addMoedas(player, 700);
					Bukkit.broadcastMessage("§a§l[CHALLENGE] §f" + player.getName()
							+ " §fcompletou o nível §emédio §fdo LavaChallenge!");
					player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a700 §fmoedas de §aLavaChallenge");
				} else if (sign.getLine(2).equals("§c§lDíficil")) {
					VapoxPvP.setWarp(player, this);
					PlayerAccount.addMoedas(player, 1200);
					Bukkit.broadcastMessage("§a§l[CHALLENGE] §f" + player.getName()
							+ " §fcompletou o nível §cdificil §fdo LavaChallenge!");
					player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a1,200 §fmoedas de §aLavaChallenge");
				} else if (sign.getLine(2).equals("§8§lINSANO")) {
					VapoxPvP.setWarp(player, this);
					PlayerAccount.addMoedas(player, 2800);
					Bukkit.broadcastMessage("§a§l[CHALLENGE] §f" + player.getName()
							+ " §fcompletou o nível §8§linsano §fdo LavaChallenge!");
					Bukkit.broadcastMessage("§a§l[CHALLENGE] §fRecompensa: §a§l2,800 §fmoedas, mandem gg!");
					player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a2,800 §fmoedas de §aLavaChallenge");
				}
			}
		}
	}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player
				&& VapoxPvP.getWarp((Player) event.getEntity()) instanceof LavaChallengeWarp)
			event.setCancelled(true);
	}
}