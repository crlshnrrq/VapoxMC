package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;

public enum PlayerTag {
	MEMBRO("§7", ChatColor.GRAY, 22, true),
	TORNEIO("§e§lTORNEIO §e", ChatColor.YELLOW, 21, false),
	VIP("§a§lVIP §a", ChatColor.GREEN, 20, true),
	MVP("§9§lMVP §9", ChatColor.BLUE, 19, true),
	PRO("§6§lPRO §6", ChatColor.GOLD, 18, true),
	HYPER("§b§lHYPER §b", ChatColor.AQUA, 17, true),
	BETA("§e§lBETA §e", ChatColor.YELLOW, 16, true),
	PARTNER("§6§lPARTNER §6", ChatColor.GOLD, 15, true),
	YOUTUBER("YouTuber", "§b§lYT §b", ChatColor.AQUA, 14, true),
	YOUTUBERPLUS("YouTuber+", "§3§lYT+ §3", ChatColor.DARK_AQUA, 13, true),
	STAFFER("§2§lSTAFFER §2", ChatColor.DARK_GREEN, 12, false),
	AJUDANTE("§9§lAJUDANTE §9", ChatColor.BLUE, 11, true),
	TRIALMOD("TrialMod", "§d§lTRIALMOD §d", ChatColor.LIGHT_PURPLE, 10, true),
	MOD("§5§lMOD §5", ChatColor.DARK_PURPLE, 9, true),
	MODGC("ModGC", "§5§lMODGC §5", ChatColor.DARK_PURPLE, 8, true),
	GERENTE("§c§lGERENTE §c", ChatColor.RED, 7, true),
	ADMIN("§c§lADMIN §c", ChatColor.RED, 6, true),
	COORD("§3§lCOORD §3", ChatColor.DARK_AQUA, 5, true),
	DIRETOR("§4§lDIRETOR §4", ChatColor.DARK_RED, 4, true),
	BUILDER("§2§lBUILDER §2", ChatColor.DARK_GREEN, 3, true),
	DEVELOPER("§3§lDEVELOPER §3", ChatColor.DARK_AQUA, 2, true),
	DONO("§4§lDONO §4", ChatColor.DARK_RED, 1, true);

	private final String name;
	private final String prefix;
	private final ChatColor color;
	private final int priority;
	private final boolean receiveOnJoin;

	private PlayerTag(String prefix, ChatColor color, int priority, boolean receiveOnJoin) {
		this.name = this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
		this.prefix = prefix;
		this.color = color;
		this.priority = priority;
		this.receiveOnJoin = receiveOnJoin;
	}

	private PlayerTag(String name, String prefix, ChatColor color, int priority, boolean receiveOnJoin) {
		this.name = name;
		this.prefix = prefix;
		this.color = color;
		this.priority = priority;
		this.receiveOnJoin = receiveOnJoin;
	}

	public String getName() {
		return name;
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

	public boolean isReceiveOnJoin() {
		return this.receiveOnJoin;
	}

	public static PlayerTag getByName(String name) {
		for (PlayerTag tag : values()) {
			if (tag.getName().equalsIgnoreCase(name))
				return tag;
		}
		return null;
	}
}