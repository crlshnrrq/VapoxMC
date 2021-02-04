package br.com.vapoxmc.kitpvp.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class EventoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("Entrar")) {
					if (VapoxPvP.isEventoActive()) {
						if (VapoxPvP.isEventoOpen()) {
							if (!VapoxPvP.hasEventoPlayer(player)) {
								VapoxPvP.addEventoPlayer(player);
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
										((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
												.getWarpByName("Evento"));
								player.sendMessage("§e§l[EVENTO] §fVocê entrou no §eevento§f.");
							} else
								player.sendMessage("§e§l[EVENTO] §fVocê já está no evento, digite §c/evento sair§f!");
						} else
							player.sendMessage("§c§l[EVENTO] §fO evento está fechado!");
					} else
						player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
				} else if (args[0].equalsIgnoreCase("Sair")) {
					if (VapoxPvP.hasEventoPlayer(player)) {
						VapoxPvP.removeEventoPlayer(player);
						((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getDefaultWarp());
						player.sendMessage("§e§l[EVENTO] §fVocê saiu do §eevento§f.");
					} else
						player.sendMessage("§e§l[EVENTO] §fVocê §cnão §festá em nenhum §eevento§f!");
				} else if (args[0].equalsIgnoreCase("Info")) {
					if (VapoxPvP.isEventoActive()) {
						player.sendMessage("§e§l[EVENTO] §fInformações:");
						player.sendMessage("§e§l[EVENTO] §fTeleporte: " + (VapoxPvP.isEventoOpen() ? "§aON" : "§cOFF"));
						player.sendMessage("§e§l[EVENTO] §fEvento: " + (VapoxPvP.isEventoActive() ? "§aON" : "§cOFF"));
						player.sendMessage("§e§l[EVENTO] §fPVP" + (VapoxPvP.getEventoPvP() ? "§aON" : "§cOFF"));
						player.sendMessage("§e§l[EVENTO] §fBuild: " + (VapoxPvP.getEventoBuild() ? "§aON" : "§cOFF"));
						player.sendMessage("§e§l[EVENTO] §fParticipantes: §a" + VapoxPvP.getEventoPlayers().size());
					} else
						player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
				} else {
					if (args[0].equalsIgnoreCase("TP")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								if (!VapoxPvP.isEventoOpen()) {
									VapoxPvP.setEventoOpen(true);
									player.sendMessage("§a§l[EVENTO] §fVocê §aativou §fo §eteleporte §fdo evento!");
								} else {
									VapoxPvP.setEventoOpen(false);
									player.sendMessage("§c§l[EVENTO] §fVocê §cdesativou §fo §eteleporte §fdo evento!");
								}
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("Start")) {
						if (player.hasPermission("command.evento")) {
							if (!VapoxPvP.isEventoActive()) {
								VapoxPvP.setEventoActive(true);
								VapoxPvP.setEventoOpen(true);
								VapoxPvP.setEventoPvP(false);
								VapoxPvP.setEventoBuild(false);
								VapoxPvP.addEventoPlayer(player);
								((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player,
										((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
												.getWarpByName("Evento"));
								player.sendMessage("§e§l[EVENTO] §fVocê §aligou §fo evento!");
								player.sendMessage("§e§l[EVENTO] §fPvP: §cOFF");
								player.sendMessage("§e§l[EVENTO] §fBuild: §cOFF");
								player.sendMessage("§e§l[EVENTO] §fTP: §aON");
								player.sendMessage("§e§l[EVENTO] §fVocê entrou no §eevento§f.");
							} else
								player.sendMessage("§e§l[EVENTO] §fO evento já está §aligado§f!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("Build")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								if (!VapoxPvP.getEventoBuild()) {
									VapoxPvP.setEventoBuild(true);
									player.sendMessage("§e§l[EVENTO] §fVocê §aativou §fa construção.");
								} else {
									VapoxPvP.setEventoBuild(false);
									player.sendMessage("§e§l[EVENTO] §fVocê §cdesativou §fa construção.");
								}
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("SetSkin")) {
						if (player.hasPermission("command.evento")) {
							if (args.length > 1) {
								VapoxPvP.getEventoPlayers().forEach(players -> {
									Bukkit.dispatchCommand(players, "Skin Set " + players.getName() + " " + args[1]);
									players.sendMessage(
											"§e§l[EVENTO] §fA sua skin foi alterada por §e" + player.getName() + "§f.");
								});
								player.sendMessage("§e§l[EVENTO] §fVocê alterou a skin de §e"
										+ VapoxPvP.getEventoPlayers().size() + " pessoas§f.");
							} else
								player.sendMessage("§e§l[EVENTO] §cComando incorreto, utilize /evento setskin [Skin]");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("Kick")) {
						if (player.hasPermission("command.evento")) {
							if (args.length > 1) {
								Player target = Bukkit.getPlayer(args[1]);
								if (target != null) {
									if (VapoxPvP.hasEventoPlayer(target)) {
										player.sendMessage("§e§l[EVENTO] §fVocê removeu o jogador §c" + target.getName()
												+ " §fdo evento.");

										VapoxPvP.getEventoPlayers()
												.forEach(players -> players.sendMessage("§e§l[EVENTO] §c"
														+ target.getName() + " §ffoi desclassificado do evento!"));
										VapoxPvP.removeEventoPlayer(target);
										((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(
												target,
												((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
														.getDefaultWarp());
										target.sendMessage(
												"§c§l[EVENTO] §fVocê foi removido do evento por §c" + player.getName());
									} else
										player.sendMessage("§c§l[EVENTO] §fEsse jogador não está no evento.");
								} else
									player.sendMessage("§cJogador \"" + args[1] + "\" não encontrado.");
							} else
								player.sendMessage("§e§l[EVENTO] §cComando incorreto, utilize /evento kick [Jogador]");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("Kit")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								if (args.length > 1) {
									if (args[1].equalsIgnoreCase("PvPBuild")) {
										VapoxPvP.getEventoPlayers().forEach(players -> {
											PlayerInventory inv = players.getInventory();
											inv.setArmorContents(null);
											inv.clear();

											inv.setHelmet(new Stack(Material.IRON_HELMET));
											inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
											inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
											inv.setBoots(new Stack(Material.IRON_BOOTS));

											inv.setItem(0, new Stack(Material.DIAMOND_SWORD)
													.ench(Enchantment.DAMAGE_ALL, 1).display("§4» §cEspada"));
											inv.setItem(1, new Stack(Material.COBBLE_WALL, 64));
											inv.setItem(2, new Stack(Material.WOOD, 64));
											inv.setItem(7, new Stack(Material.LAVA_BUCKET));
											inv.setItem(8, new Stack(Material.WATER_BUCKET));
											inv.setItem(13,
													new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft §7«"));
											inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64)
													.display("§7» §fRecraft §7«"));
											inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft §7«"));
											inv.setItem(17, new Stack(Material.IRON_PICKAXE));
											inv.setItem(26, new Stack(Material.IRON_AXE));

											for (int i = 0; i < 36; i++)
												inv.addItem(new Stack(Material.MUSHROOM_SOUP));

											players.sendMessage(
													"§e§l[EVENTO] §fVocê recebeu o kit §e" + args[1] + "§f!");
										});
										player.sendMessage("§e§l[EVENTO] §fVocê setou o kit §e" + args[1] + "§f!");
									} else if (args[1].equalsIgnoreCase("PvP")) {
										VapoxPvP.getEventoPlayers().forEach(players -> {
											PlayerInventory inv = players.getInventory();
											inv.setArmorContents(null);
											inv.clear();

											inv.setHelmet(new Stack(Material.IRON_HELMET));
											inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
											inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
											inv.setBoots(new Stack(Material.IRON_BOOTS));

											inv.setItem(0, new Stack(Material.DIAMOND_SWORD)
													.ench(Enchantment.DAMAGE_ALL, 1).display("§4» §cEspada"));
											inv.setItem(13,
													new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft §7«"));
											inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64)
													.display("§7» §fRecraft §7«"));
											inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft §7«"));

											for (int i = 0; i < 36; i++)
												inv.addItem(new Stack(Material.MUSHROOM_SOUP));

											players.sendMessage(
													"§e§l[EVENTO] §fVocê recebeu o kit §e" + args[1] + "§f!");
										});
										player.sendMessage("§e§l[EVENTO] §fVocê setou o kit §e" + args[1] + "§f!");
									} else if (args[1].equalsIgnoreCase("PotPvP")) {
										VapoxPvP.getEventoPlayers().forEach(players -> {
											PlayerInventory inv = players.getInventory();
											inv.setArmorContents(null);
											inv.clear();

											inv.setHelmet(new Stack(Material.IRON_HELMET));
											inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
											inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
											inv.setBoots(new Stack(Material.IRON_BOOTS));

											inv.setItem(0, new Stack(Material.DIAMOND_SWORD)
													.ench(Enchantment.DAMAGE_ALL, 1).display("§4» §cEspada"));
											inv.setItem(6, new Stack(Material.POTION, 1, 8259));
											inv.setItem(7, new Stack(Material.POTION, 1, 8201));
											inv.setItem(8, new Stack(Material.POTION, 1, 8194));

											for (int i = 0; i < 36; i++)
												inv.addItem(new Stack(Material.POTION, 1, 16421));

											players.sendMessage(
													"§e§l[EVENTO] §fVocê recebeu o kit §e" + args[1] + "§f!");
										});
										player.sendMessage("§e§l[EVENTO] §fVocê setou o kit §e" + args[1] + "§f!");
									} else if (args[1].equalsIgnoreCase("SemRecraft")) {
										VapoxPvP.getEventoPlayers().forEach(players -> {
											PlayerInventory inv = players.getInventory();
											inv.setArmorContents(null);
											inv.clear();

											inv.setHelmet(new Stack(Material.IRON_HELMET));
											inv.setChestplate(new Stack(Material.IRON_CHESTPLATE));
											inv.setLeggings(new Stack(Material.IRON_LEGGINGS));
											inv.setBoots(new Stack(Material.IRON_BOOTS));

											inv.setItem(0, new Stack(Material.DIAMOND_SWORD)
													.ench(Enchantment.DAMAGE_ALL, 1).display("§4» §cEspada"));

											for (int i = 0; i < 36; i++)
												inv.addItem(new Stack(Material.MUSHROOM_SOUP));

											players.sendMessage(
													"§e§l[EVENTO] §fVocê recebeu o kit §e" + args[1] + "§f!");
										});
										player.sendMessage("§e§l[EVENTO] §fVocê setou o kit §e" + args[1] + "§f!");
									} else {
										player.sendMessage(
												"§c§l[EVENTO] §fO kit \"§c" + args[1] + "§f\" não foi encontrado!");
										player.sendMessage(
												"§c§l[EVENTO] §fCaso queira criar um kit, utilize §c/evento skit§f.");
									}
								} else {
									player.sendMessage("§e§l[EVENTO] §cComando incorreto, utilize /evento kit [Kit]");
									player.sendMessage(
											"§e§l[EVENTO] §fKits: §apvpbuild§f, §apvp§f, §apotpvp§f, §asemrecraft§f.");
								}
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("GMAll")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								VapoxPvP.getEventoPlayers().forEach(players -> players.setGameMode(GameMode.SURVIVAL));
								player.sendMessage(
										"§e§l[EVENTO] §fModo de jogo dos jogadores alterado para §esurvival§f.");
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("PvP")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								if (!VapoxPvP.getEventoPvP()) {
									VapoxPvP.setEventoPvP(true);
									player.sendMessage("§e§l[EVENTO] §fVocê §aativou §fo pvp!");
								} else {
									VapoxPvP.setEventoPvP(false);
									player.sendMessage("§e§l[EVENTO] §fVocê §cdesativou §fo pvp!");
								}
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("TPAll")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								VapoxPvP.getEventoPlayers().forEach(players -> players.teleport(player.getLocation()));
								player.sendMessage("§e§l[EVENTO] §fVocê puxou todos os jogadores!");
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("ClearArena")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								int index = 0;
								if (!VapoxPvP.getPlacedblocks().isEmpty()) {
									for (Block blocks : new ArrayList<>(VapoxPvP.getPlacedblocks())) {
										blocks.setType(Material.AIR);
										VapoxPvP.getPlacedblocks().remove(blocks);
										index++;
									}
								}
								player.sendMessage("§e§l[EVENTO] §fVocê retirou os blocos do §eevento§f! §7(" + index
										+ " blocos)");
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("SKit")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								VapoxPvP.getEventoPlayers().forEach(players -> {
									players.getInventory().setArmorContents(player.getInventory().getArmorContents());
									players.getInventory().setContents(player.getInventory().getContents());
									players.sendMessage("§e§l[EVENTO] §fVocê recebeu o kit §eevento§f!");
								});
								player.sendMessage("§e§l[EVENTO] §fVocê setou o kit §eevento§f!");
							} else
								player.sendMessage("§c§l[EVENTO] §fNão há eventos no momento!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					} else if (args[0].equalsIgnoreCase("Stop")) {
						if (player.hasPermission("command.evento")) {
							if (VapoxPvP.isEventoActive()) {
								VapoxPvP.setEventoPvP(false);
								VapoxPvP.setEventoBuild(false);
								VapoxPvP.setEventoOpen(false);
								VapoxPvP.setEventoActive(false);
								new ArrayList<>(VapoxPvP.getPlacedblocks()).forEach(blocks -> {
									blocks.setType(Material.AIR);
									VapoxPvP.getPlacedblocks().remove(blocks);
								});
								player.sendMessage("§e§l[EVENTO] §fVocê forçou o §cdesligamento §fdo evento!");
								new ArrayList<>(VapoxPvP.getEventoPlayers()).forEach(players -> {
									VapoxPvP.removeEventoPlayer(players);
									((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(players,
											((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
													.getDefaultWarp());
									players.sendMessage("§e§l[EVENTO] §fO evento foi §cfinalizado§f.");
								});
							} else
								player.sendMessage("§e§l[EVENTO] §fO evento já está §cdesligado§f!");
						} else
							player.sendMessage(
									"§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando");
					}
				}
			} else {
				player.sendMessage("§b§lVapoxMC - Sistema de Eventos");
				player.sendMessage("§b/evento entrar §f- Comandos do evento.");
				player.sendMessage("§b/evento sair §f- Saia do evento.");
				player.sendMessage("§b/evento help §f- Mostra essa mensagem.");
				if (player.hasPermission("command.evento")) {
					player.sendMessage("§7VapoxMC - Staff");
					player.sendMessage("§b/evento start §f- Inicia o evento.");
					player.sendMessage("§b/evento tp §f- Abre/fecha o teleport do evento.");
					player.sendMessage("§b/evento gmall §f- Seta todos no gamemode survival.");
					player.sendMessage("§b/evento pvp §f- liga/desliga o pvp do evento.");
					player.sendMessage("§b/evento build §f- liga/desliga o build do evento.");
					player.sendMessage("§b/evento tpall §f- teleporta todos do evento.");
					player.sendMessage("§b/evento info §f- Informações do evento.");
					player.sendMessage("§b/evento cleararena §f- Limpa a arena.");
					player.sendMessage("§b/evento stop §f- Finaliza o evento.");
					player.sendMessage("§b/evento skit §f- Seta um kit personalizado para todos.");
					player.sendMessage("§b/evento kick [Jogador] §f- Remove um jogador do evento.");
					player.sendMessage("§b/evento setskin [Skin] §f- Seta uma skin personalizada para todos.");
					player.sendMessage("§b/evento kit [Kit] §f- Seta um kit padrão para todos.");
				}
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}