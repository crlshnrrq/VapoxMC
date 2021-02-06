package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerRemoveWarpEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class UMvUMWarp extends Warp implements Listener {

	private final Map<UUID, UUID> enemyMap = new HashMap<>();
	private final Map<UUID, List<UUID>> inviteMap = new HashMap<>();
	private final List<UUID> fastDuel = new ArrayList<>();
	public static final Stack INVITE_ITEM = new Stack(Material.BLAZE_ROD).display("§aDesafiar! §7(1v1)")
			.lore("§fClique com o §6direito §fpara desafiar alguém!");

	public UMvUMWarp() {
		super("1v1", "Lute contra outros jogadores em um duelo justo, a competitividade é alta, por isso tome cuidado.",
				new Stack(Material.BLAZE_ROD), new Location(Bukkit.getWorlds().get(0), 49894, 93, 49980, 0, 0));
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);
		inv.setItem(0, INVITE_ITEM);

		VapoxPvP.addProtection(player);
		Bukkit.getOnlinePlayers().forEach(players -> player.showPlayer(players));
	}

	public boolean hasEnemy(Player player) {
		return this.enemyMap.containsKey(player.getUniqueId());
	}

	public Player getEnemy(Player player) {
		if (this.hasEnemy(player))
			return Bukkit.getPlayer(this.enemyMap.get(player.getUniqueId()));
		return null;
	}

	public Map<UUID, UUID> getEnemyMap() {
		return this.enemyMap;
	}

	public void setEnemy(Player player, Player enemy) {
		this.enemyMap.put(player.getUniqueId(), enemy.getUniqueId());
	}

	public void removeEnemy(Player player) {
		this.enemyMap.remove(player.getUniqueId());
	}

	public List<UUID> getFastDuel() {
		return this.fastDuel;
	}

	public boolean inFastDuel(Player player) {
		return this.fastDuel.contains(player.getUniqueId());
	}

	public void addFastDuel(Player player) {
		if (this.inFastDuel(player))
			this.fastDuel.add(player.getUniqueId());
	}

	public void removeFastDuel(Player player) {
		this.fastDuel.remove(player.getUniqueId());
	}

	public List<UUID> getInvites(Player player) {
		return inviteMap.getOrDefault(player.getUniqueId(), new ArrayList<>());
	}

	public void clearInvites(Player player) {
		this.inviteMap.remove(player.getUniqueId());
	}

	public boolean hasInvite(Player player, Player inviter) {
		return this.getInvites(player).contains(inviter.getUniqueId());
	}

	public void addInvite(Player player, Player inviter) {
		List<UUID> invites = this.getInvites(player);
		if (!invites.contains(inviter.getUniqueId()))
			invites.add(inviter.getUniqueId());
		this.inviteMap.put(player.getUniqueId(), invites);
	}

	public void removeInvite(Player player, Player inviter) {
		List<UUID> invites = this.getInvites(player);
		invites.remove(inviter.getUniqueId());
		this.inviteMap.put(player.getUniqueId(), invites);
	}

	public void giveKit(Player player) {
		PlayerInventory inv = player.getInventory();
		inv.setArmorContents(null);
		inv.clear();

		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);

		player.resetMaxHealth();
		player.setHealth(20D);
		player.setFoodLevel(20);

		inv.setItem(0, new Stack(Material.STONE_SWORD));
		for (int i = 0; i < 8; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));

		player.closeInventory();

		VapoxPvP.removeProtection(player);
	}

	public void startDuel(Player player, Player enemy) {
		try {
			player.teleport(new Location(Bukkit.getWorlds().get(0), 49948, 92, 49966, 0, 0));
			enemy.teleport(new Location(Bukkit.getWorlds().get(0), 49948, 92, 50004, 180, 0));

			this.giveKit(player);
			this.giveKit(enemy);

			Bukkit.getOnlinePlayers().stream().filter(players -> !players.getName().equals(player.getName())
					&& !players.getName().equals(enemy.getName())).forEach(players -> {
						player.hidePlayer(players);
						enemy.hidePlayer(players);
					});
			Bukkit.getOnlinePlayers().stream().filter(players -> VapoxPvP.hasAdmin(players)).forEach(players -> {
				if (!player.hasPermission("command.admin"))
					player.hidePlayer(players);
				if (!enemy.hasPermission("command.admin"))
					enemy.hidePlayer(players);
			});

			this.setEnemy(player, enemy);
			this.setEnemy(enemy, player);
		} catch (Exception ex) {
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerRemoveWarp(PlayerRemoveWarpEvent event) {
		if (event.getWarp() instanceof UMvUMWarp) {
			Player player = event.getPlayer();
			this.removeEnemy(player);
			this.removeFastDuel(player);
			this.clearInvites(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.getWarp() instanceof UMvUMWarp) {
			Player player = event.getPlayer();
			this.removeEnemy(player);
			this.removeFastDuel(player);
			this.clearInvites(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Player && player.getItemInHand() != null
				&& player.getItemInHand().isSimilar(INVITE_ITEM.toItemStack())) {
			Player righted = (Player) event.getRightClicked();
			event.setCancelled(true);

			if (!this.hasEnemy(righted)) {
				if (this.hasInvite(player, righted)) {
					this.removeInvite(player, righted);
					this.removeInvite(righted, player);
					this.startDuel(righted, player);
					righted.sendMessage(
							" §a• §fBatalha iniciada: §a" + righted.getName() + " §fvs §a" + player.getName());
					player.sendMessage(
							" §a• §fBatalha iniciada: §a" + player.getName() + " §fvs §a" + righted.getName());
				} else if (this.hasInvite(righted, player))
					player.sendMessage(" §c• §fVocê já enviou um convite a §c" + righted.getName() + "§f.");
				else {
					this.addInvite(righted, player);
					righted.sendMessage(
							" §e• §7Você foi desafiado por §e§n" + player.getName() + "§r §fpara uma §ebatalha§f.");
					player.sendMessage(" §e• §fVocê desafiou §e§n" + righted.getName() + "§r §fpara uma batalha.");
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDeath(PlayerDeathEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getEntity();
		if (this.hasEnemy(player)) {
			Player enemy = this.getEnemy(player);

			Bukkit.getOnlinePlayers().forEach(players -> {
				player.showPlayer(players);
				enemy.showPlayer(players);
			});
			Bukkit.getOnlinePlayers().stream().filter(players -> VapoxPvP.hasAdmin(players)).forEach(players -> {
				if (!player.hasPermission("command.admin"))
					player.hidePlayer(players);
				if (!enemy.hasPermission("command.admin"))
					enemy.hidePlayer(players);
			});

			PlayerAccount.addAbate(enemy);
			PlayerAccount.addMorte(player);
			PlayerAccount.add1v1Vitoria(enemy);
			PlayerAccount.add1v1Derrota(player);
			PlayerAccount.add1v1WinStreak(enemy);
			PlayerAccount.set1v1WinStreak(player, 0);
			player.sendMessage(" §c• §fVocê §cperdeu §fa batalha contra §c§n" + enemy.getName() + "§f.");

			int coins = VapoxUtils.getRandomCoins(), points = VapoxUtils.getRandomPoints();
			PlayerAccount.addMoedas(enemy, coins);
			PlayerAccount.addPontos(enemy, points);
			enemy.sendMessage(" §c• §fVocê §avenceu §fa batalha contra §a" + player.getName() + "§f.");
			if (enemy.hasPermission("coinsbooster.x2")) {
				PlayerAccount.addMoedas(enemy, coins);
				enemy.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
			} else
				enemy.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
			enemy.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");

			this.removeEnemy(player);
			this.removeEnemy(enemy);

			Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(),
					() -> ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(enemy, this), 10L);

			int winStreak = PlayerAccount.get1v1WinStreak(enemy);
			if (winStreak % 5 == 0)
				Bukkit.broadcastMessage(" §6• §fO jogador §6§n" + enemy.getName() + "§r §fatingiu um winstreak de §6"
						+ winStreak + " vitórias§f!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerQuit(PlayerQuitEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (this.hasEnemy(player)) {
			Player enemy = this.getEnemy(player);

			Bukkit.getOnlinePlayers().forEach(players -> {
				player.showPlayer(players);
				enemy.showPlayer(players);
			});
			Bukkit.getOnlinePlayers().stream().filter(players -> VapoxPvP.hasAdmin(players)).forEach(players -> {
				if (!player.hasPermission("command.admin"))
					player.hidePlayer(players);
				if (!enemy.hasPermission("command.admin"))
					enemy.hidePlayer(players);
			});

			PlayerAccount.addAbate(enemy);
			PlayerAccount.addMorte(player);
			PlayerAccount.add1v1Vitoria(enemy);
			PlayerAccount.add1v1Derrota(player);
			player.sendMessage(" §c• §fVocê §cperdeu §fa batalha contra §c§n" + enemy.getName() + "§f.");

			int coins = VapoxUtils.getRandomCoins(), points = VapoxUtils.getRandomPoints();
			PlayerAccount.addMoedas(enemy, coins);
			PlayerAccount.addPontos(enemy, points);
			enemy.sendMessage(" §e• §fVocê §evenceu §fa batalha contra §e§n" + player.getName() + "§f.");
			if (enemy.hasPermission("coinsbooster.x2")) {
				PlayerAccount.addMoedas(enemy, coins);
				enemy.sendMessage(" §a• §fVocê recebeu §a" + (coins * 2) + " moedas §7(x2)§f.");
			} else
				enemy.sendMessage(" §a• §fVocê recebeu §a" + coins + " moedas§f.");
			enemy.sendMessage(" §a• §fVocê recebeu §a" + points + " pontos§f.");

			this.removeEnemy(player);
			this.removeEnemy(enemy);

			Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> system.setWarp(enemy, this), 10L);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof UMvUMWarp
				&& event.getItemDrop().getItemStack().isSimilar(INVITE_ITEM.toItemStack()))
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (this.hasEnemy(player)) {
			event.setCancelled(true);
			player.sendMessage("§c§l[1V1] §fVocê não pode executar §c§lnenhum §fcomando em batalha!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onCraftItem(CraftItemEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		if (event.getWhoClicked() instanceof Player
				&& system.getWarp((Player) event.getWhoClicked()) instanceof UMvUMWarp
				&& event.getRecipe().getResult().getType() == Material.BLAZE_POWDER)
			event.setCancelled(true);

	}
}