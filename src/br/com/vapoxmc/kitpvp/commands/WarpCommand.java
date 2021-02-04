package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class WarpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!((KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kit")).hasKit(player)) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("Knockback")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("Knockback"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
					} else if (args[0].equalsIgnoreCase("Fisherman")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("Fisherman"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
					} else if (args[0].equalsIgnoreCase("FPS")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("FPS"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
					} else if (args[0].equalsIgnoreCase("PotPvP")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("PotPvP"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
					} else if (args[0].equalsIgnoreCase("LavaChallenge")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("Lava Challenge"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
					} else if (args[0].equalsIgnoreCase("1v1")) {
						if (VapoxPvP.hasAdmin(player)) {
							VapoxPvP.removeAdmin(player);
							player.sendMessage("§e§l[ADMIN] §fVocê §e§lAINDA §festava no modo §a§ladmin§f!");
						}
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
										.getWarpByName("1v1"));
						player.sendMessage("§a§l[WARP] §fVocê foi teleportado para a warp §a" + args[0] + "§f!");
						player.sendMessage("§a§l[WARP] §fÉ necessário clicar §a§lduas §fvezes para confirmar o duelo.");
					} else
						player.sendMessage("§c§l[WARP] §fNão encontramos a warp \"§c" + args[0] + "§f\"");
				} else {
					player.sendMessage("§c§l[WARP] §fUtilize §c/warp [Warp]");
					player.sendMessage("§e§l[WARPS] §fKnockback, FPS, Fisherman, LavaChallenge, 1v1, PotPvP.");
				}
			} else
				player.sendMessage("§c§l[WARP] §fVocê está com um kit! utilize §c/spawn §fantes de ir para uma warp!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}