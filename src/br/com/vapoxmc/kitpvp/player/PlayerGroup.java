package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerGroup {
	DONO("Dono", "tag.dono", ChatColor.DARK_RED),
	DIRETOR("Diretor", "tag.diretor", ChatColor.DARK_RED),
	COORD("Coord", "tag.coord", ChatColor.DARK_AQUA),
	ADMIN("Admin", "tag.admin", ChatColor.RED),
	GERENTE("Gerente", "tag.gerente", ChatColor.RED),
	MODGC("ModGC", "tag.modgc", ChatColor.DARK_PURPLE),
	MOD("Mod", "tag.mod", ChatColor.DARK_PURPLE),
	TRIAL("Trial", "tag.trial", ChatColor.LIGHT_PURPLE),
	AJUDANTE("Ajudante", "tag.ajudante", ChatColor.BLUE),
	BUILDER("Builder", "tag.builder", ChatColor.DARK_GREEN),
	YOUTUBERPLUS("YT+", "tag.youtuber+", ChatColor.DARK_AQUA),
	YOUTUBER("YT", "tag.youtuber", ChatColor.AQUA),
	BETA("Beta", "tag.beta", ChatColor.YELLOW),
	HYPER("Hyper", "tag.hyper", ChatColor.AQUA),
	PRO("Pro", "tag.pro", ChatColor.GOLD),
	MVP("MvP", "tag.mvp", ChatColor.BLUE),
	VIP("VIP", "tag.vip", ChatColor.GREEN),
	MEMBRO("Membro", "tag.membro", ChatColor.GRAY);

	private final String name, permission;
	private final ChatColor color;

	private PlayerGroup(String name, String permission, ChatColor color) {
		this.name = name;
		this.permission = permission;
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public String getPermission() {
		return this.permission;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public String getColoredName() {
		return this.getColor() + this.getName();
	}

	public String getBoldColoredName() {
		return this.getColor() + "§l" + this.getName();
	}

	public static PlayerGroup getByName(String name) {
		for (PlayerGroup group : values()) {
			if (group.name().equalsIgnoreCase(name))
				return group;
		}
		return null;
	}

	public static PlayerGroup getGroup(Player player) {
		for (PlayerGroup group : values()) {
			if (player.hasPermission(group.getPermission()))
				return group;
		}
		return MEMBRO;
	}
}