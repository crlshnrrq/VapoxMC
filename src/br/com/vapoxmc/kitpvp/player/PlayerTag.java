package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;

public enum PlayerTag {
	MEMBRO("§7", ChatColor.GRAY, 22),
	TORNEIO("§e§lTORNEIO §e", ChatColor.YELLOW, 21),
	VIP("§a§lVIP §a", ChatColor.GREEN, 20),
	MVP("§9§lMVP §9", ChatColor.BLUE, 19),
	PRO("§6§lPRO §6", ChatColor.GOLD, 18),
	HYPER("§b§lHYPER §b", ChatColor.AQUA, 17),
	BETA("§e§lBETA §e", ChatColor.YELLOW, 16),
	PARTNER("§6§lPARTNER §e", ChatColor.GOLD, 15),
	YOUTUBER("§b§lYT §b", ChatColor.AQUA, 14),
	YOUTUBERPLUS("§3§lYT+ §3", ChatColor.DARK_AQUA, 13),
	STAFFER("§e§lSTAFFER §e", ChatColor.YELLOW, 12),
	AJUDANTE("§9§lAJUDANTE §9", ChatColor.BLUE, 11),
	TRIALMOD("§5§lTRIALMOD §5", ChatColor.DARK_PURPLE, 10),
	MOD("§5§lMOD §5", ChatColor.DARK_PURPLE, 9),
	MODGC("§5§lGC §5", ChatColor.DARK_PURPLE, 8),
	GERENTE("§c§lGERENTE §c", ChatColor.RED, 7),
	ADMIN("§c§lADMIN §c", ChatColor.RED, 6),
	COORD("§3§lCOORD §3", ChatColor.DARK_AQUA, 5),
	DIRETOR("§4§lDIRETOR §4", ChatColor.DARK_RED, 4),
	DONO("§4§lDONO §4", ChatColor.DARK_RED, 3),
	BUILDER("§2§lBUILDER §2", ChatColor.DARK_GREEN, 2),
	DEVELOPER("§3§lDEVELOPER §3", ChatColor.DARK_AQUA, 1);
	
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