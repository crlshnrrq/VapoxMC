package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;

public enum PlayerTag {
	MEMBRO("§7", ChatColor.GRAY, 19), VIP("§a§lVIP §a", ChatColor.GREEN, 18), MVP("§9§lMVP §9", ChatColor.BLUE, 17),
	PRO("§6§lPRO §6", ChatColor.GOLD, 16), HYPER("§b§lHYPER §b", ChatColor.AQUA, 15),
	TORNEIO("§e§lTORNEIO §e", ChatColor.YELLOW, 14), BETA("§e§lBETA §e", ChatColor.YELLOW, 13),
	BUILDER("§2§lBUILDER §2", ChatColor.DARK_GREEN, 12), YOUTUBER("§b§lYT §b", ChatColor.AQUA, 11),
	YOUTUBERPLUS("§3§lYT+ §3", ChatColor.DARK_AQUA, 10), AJUDANTE("§9§lAJUDANTE §9", ChatColor.BLUE, 9),
	TRIAL("§d§lTRIAL §d", ChatColor.LIGHT_PURPLE, 8), MOD("§5§lMOD §5", ChatColor.DARK_PURPLE, 7),
	MODGC("§5§lMODGC §5", ChatColor.DARK_PURPLE, 6), GERENTE("§c§lGERENTE §c", ChatColor.RED, 5),
	ADMIN("§c§lADMIN §c", ChatColor.RED, 4), COORD("§3§lCOORD §3", ChatColor.DARK_AQUA, 3),
	DIRETOR("§4§lDIRETOR §4", ChatColor.DARK_RED, 2), DONO("§4§lDONO §4", ChatColor.DARK_RED, 1);

	private final String prefix;
	private final ChatColor color;
	private final int priority;

	private PlayerTag(String prefix, ChatColor color, int priority) {
		this.prefix = prefix;
		this.color = color;
		this.priority = priority;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public int getPriority() {
		return this.priority;
	}

	public static PlayerTag getByName(String name) {
		for (PlayerTag tag : values()) {
			if (tag.name().equalsIgnoreCase(name))
				return tag;
		}
		return null;
	}
}