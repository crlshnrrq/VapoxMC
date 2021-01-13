package br.com.vapoxmc.kitpvp.warp;

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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;

public final class UMvUMWarp extends Warp implements Listener {

	private final Map<UUID, UUID> enemyMap = new HashMap<>();
	private final Map<UUID, List<UUID>> inviteMap = new HashMap<>();
	private final List<UUID> fastDuel = new ArrayList<>();
	public static final Stack INVITE_ITEM = new Stack(Material.BLAZE_ROD).display("§aDesafiar! §7(1v1)")
			.lore("§fClique com o §6direito §fpara desafiar alguém!");

	public UMvUMWarp() {
		super("1v1", new Stack(Material.BLAZE_ROD), new Location(Bukkit.getWorlds().get(0), 49894, 93, 49980, 0, 0));
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
				if (!player.hasPermission("ciphen.comandos.admin"))
					player.hidePlayer(players);
				if (!enemy.hasPermission("ciphen.comandos.admin"))
					enemy.hidePlayer(players);
			});

			this.setEnemy(player, enemy);
			this.setEnemy(enemy, player);
		} catch (Exception ex) {
		}
	}

	@EventHandler
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
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
							"§a§l[1V1] §fBatalha iniciada! §a" + righted.getName() + " §f§lVS §a" + player.getName());
					player.sendMessage(
							"§a§l[1V1] §fBatalha iniciada! §a" + player.getName() + " §f§lVS §a" + righted.getName());
				} else if (this.hasInvite(righted, player))
					player.sendMessage("§c§l[1V1] §cVocê já enviou um convite a " + righted.getName());
				else {
					this.addInvite(righted, player);
					Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
						if (this.hasInvite(righted, player))
							this.removeInvite(righted, player);
					}, 100L);
					righted.sendMessage("§e§l[1V1] §e" + player.getName()
							+ " §fte desafiou para uma batalha, você tem §e§l5 §fsegundos para aceitar!");
					player.sendMessage("§e§l[1V1] §fVocê desafiou §e" + righted.getName()
							+ " §fpara uma batalha, ele tem §e§l5 §fsegundos para aceitar!");
				}
			}
		}
	}

	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (this.hasEnemy(player)) {
			Player enemy = this.getEnemy(player);

			Bukkit.getOnlinePlayers().forEach(players -> {
				player.showPlayer(players);
				enemy.showPlayer(players);
			});
			Bukkit.getOnlinePlayers().stream().filter(players -> VapoxPvP.hasAdmin(players)).forEach(players -> {
				if (!player.hasPermission("ciphen.comandos.admin"))
					player.hidePlayer(players);
				if (!enemy.hasPermission("ciphen.comandos.admin"))
					enemy.hidePlayer(players);
			});

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

			this.removeEnemy(player);
			this.removeEnemy(enemy);

			Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> VapoxPvP.setWarp(enemy, this), 10L);
		}
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof UMvUMWarp
				&& event.getItemDrop().getItemStack().isSimilar(INVITE_ITEM.toItemStack()))
			event.setCancelled(true);
	}

	@EventHandler
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (this.hasEnemy(player)) {
			event.setCancelled(true);
			player.sendMessage("§c§l[1V1] §fVocê não pode executar §c§lnenhum §fcomando em batalha!");
		}
	}
}