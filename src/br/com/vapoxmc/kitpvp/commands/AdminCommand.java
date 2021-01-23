package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;

public final class AdminCommand implements CommandExecutor {

	public static final Stack KNOCKBACK = new Stack(Material.IRON_FENCE).display("§7» §bKnockback Teste"),
			QUICKADMIN = new Stack(Material.MAGMA_CREAM).display("§7» §bQuick Admin"),
			INFO = new Stack(Material.BOOK).display("§7» §bInformações"),
			CLICKTEST = new Stack(Material.WOOD_SWORD).display("§7» §bTeste de Cliques");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.admin")) {
				if (!VapoxPvP.hasAdmin(player)) {
					VapoxPvP.addAdmin(player);
					VapoxPvP.removeKit(player);
					VapoxPvP.removeWarp(player);
					VapoxPvP.removeProtection(player);
					for (Player players : Bukkit.getOnlinePlayers())
						if (!players.hasPermission("command.admin"))
							players.hidePlayer(player);
					player.setGameMode(GameMode.CREATIVE);
					player.setFlying(true);
					player.sendMessage("§e§l[ADMIN] §fVocê §a§lentrou §fno modo admin.");

					PlayerInventory inv = player.getInventory();
					VapoxPvP.setArmorAndInventory(player, inv.getArmorContents(), inv.getContents());
					inv.setArmorContents(null);
					inv.clear();

					inv.setItem(2, KNOCKBACK);
					inv.setItem(4, QUICKADMIN);
					inv.setItem(6, INFO);
					inv.setItem(8, CLICKTEST);
					player.updateInventory();
				} else {
					VapoxPvP.removeAdmin(player);
					Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(player));
					VapoxUtils.sendActionBar(player, "§fVocê saiu do modo §cADMIN§f.");

					player.setGameMode(GameMode.CREATIVE);
					if (VapoxPvP.hasArmorAndInventory(player)) {
						PlayerInventory inv = player.getInventory();
						inv.setArmorContents(VapoxPvP.getArmor(player));
						inv.setContents(VapoxPvP.getInventory(player));
						VapoxPvP.removeArmorAndInventory(player);
					}
					player.updateInventory();
					player.sendMessage("§e§l[ADMIN] §fVocê §c§lsaiu §fdo modo admin.");
				}
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}