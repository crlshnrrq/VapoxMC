package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerRank {
	VAPOX("Vapox", '✪', ChatColor.DARK_RED, 20000), SAFIRA("Safira", '✡', ChatColor.AQUA, 18000),
	ELITE("Elite", '♜', ChatColor.RED, 16000), RUBY("Ruby", '♦', ChatColor.RED, 14000),
	ESMERALDA("Esmeralda", '✷', ChatColor.GREEN, 12000), DIAMANTE("Diamante", '❂', ChatColor.AQUA, 10000),
	GOLD("Gold", '✸', ChatColor.GOLD, 8000), PLATINA("Platina", '✮', ChatColor.RED, 6000),
	ADVANCED("Advanced", '✳', ChatColor.GRAY, 4000), PRIMARY("Primary", '⚌', ChatColor.GREEN, 2000),
	UNRANKED("Unranked", '-', ChatColor.WHITE, 0);

	private final String name;
	private final char symbol;
	private final ChatColor color;
	private final int pontos;

	private PlayerRank(String name, char symbol, ChatColor color, int pontos) {
		this.name = name;
		this.symbol = symbol;
		this.color = color;
		this.pontos = pontos;
	}

	public String getName() {
		return this.name;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public int getPontos() {
		return this.pontos;
	}

	public String getColoredName() {
		return this.getColor() + this.getName();
	}

	public String getColoredSymbol() {
		return this.getColor().toString() + this.getSymbol();
	}

	public String getColoredSymbolName() {
		return this.getColoredSymbol() + " " + this.getName();
	}

	public static PlayerRank getRank(Player player) {
		int pontos = PlayerAccount.getPontos(player);
		for (PlayerRank rank : values()) {
			if (pontos >= rank.getPontos())
				return rank;
		}
		return VAPOX;
	}
}