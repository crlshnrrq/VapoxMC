package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerGroup {
	DONO("Dono", "ciphen.tag.dono", ChatColor.DARK_RED), DIRETOR("Diretor", "ciphen.tag.diretor", ChatColor.DARK_RED),
	COORD("Coord", "ciphen.tag.coord", ChatColor.DARK_AQUA), ADMIN("Admin", "ciphen.tag.admin", ChatColor.RED),
	GERENTE("Gerente", "ciphen.tag.gerente", ChatColor.RED), MODGC("ModGC", "ciphen.tag.modgc", ChatColor.DARK_PURPLE),
	MOD("Mod", "ciphen.tag.mod", ChatColor.DARK_PURPLE), TRIAL("Trial", "ciphen.tag.trial", ChatColor.LIGHT_PURPLE),
	AJUDANTE("Ajudante", "ciphen.tag.ajudante", ChatColor.BLUE),
	BUILDER("Builder", "ciphen.tag.builder", ChatColor.DARK_GREEN),
	YOUTUBERPLUS("YT+", "ciphen.tag.youtuberplus", ChatColor.DARK_AQUA),
	YOUTUBER("YT", "ciphen.tag.youtuber", ChatColor.AQUA), BETA("Beta", "ciphen.tag.beta", ChatColor.YELLOW),
	HYPER("Hyper", "ciphen.tag.hyper", ChatColor.AQUA), PRO("Pro", "ciphen.tag.pro", ChatColor.GOLD),
	MVP("MvP", "ciphen.tag.mvp", ChatColor.BLUE), VIP("VIP", "ciphen.tag.vip", ChatColor.GREEN),
	MEMBRO("Membro", "ciphen.tag.membro", ChatColor.GRAY);

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