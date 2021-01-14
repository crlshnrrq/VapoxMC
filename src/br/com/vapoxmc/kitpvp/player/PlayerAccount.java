package br.com.vapoxmc.kitpvp.player;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class PlayerAccount {

	private static File file;
	private static YamlConfiguration config;

	public static void createConnection() {
		file = new File(VapoxPvP.getInstance().getDataFolder(), "status.yml");
		if (!file.exists())
			file.getParentFile().mkdirs();
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static boolean existAccount(String nickname) {
		return config.contains("status." + nickname);
	}

	public static void createAccount(Player player) {
		if (!existAccount(player.getName())) {
			try {
				config.set("status." + player.getName() + ".geral.moedas", 0);
				config.set("status." + player.getName() + ".geral.pontos", 0);
				config.set("status." + player.getName() + ".geral.abates", 0);
				config.set("status." + player.getName() + ".geral.mortes", 0);
				config.set("status." + player.getName() + ".geral.killstreak", 0);

				config.set("status." + player.getName() + ".1v1.vitorias", 0);
				config.set("status." + player.getName() + ".1v1.derrotas", 0);
				config.set("status." + player.getName() + ".1v1.winstreak", 0);

				config.set("status." + player.getName() + ".sumo.vitorias", 0);
				config.set("status." + player.getName() + ".sumo.derrotas", 0);
				config.set("status." + player.getName() + ".sumo.winstreak", 0);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static int getMoedas(Player player) {
		return config.getInt("status." + player.getName() + ".geral.moedas", 0);
	}

	public static void setMoedas(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".geral.moedas", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addMoedas(Player player, int amount) {
		setMoedas(player, getMoedas(player) + amount);
	}

	public static void drawMoedas(Player player, int amount) {
		int result = getMoedas(player) - amount;
		setMoedas(player, result < 0 ? 0 : result);
	}

	public static int getPontos(Player player) {
		return config.getInt("status." + player.getName() + ".geral.pontos", 0);
	}

	public static void setPontos(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".geral.pontos", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addPontos(Player player, int amount) {
		setPontos(player, getPontos(player) + amount);
	}

	public static int getKillStreak(Player player) {
		return config.getInt("status." + player.getName() + ".geral.killstreak", 0);
	}

	public static void setKillStreak(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".geral.killstreak", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addKillStreak(Player player) {
		setKillStreak(player, getKillStreak(player) + 1);
	}

	public static int getAbates(Player player) {
		return config.getInt("status." + player.getName() + ".geral.abates", 0);
	}

	public static void setAbates(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".geral.abates", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addAbates(Player player, int amount) {
		setAbates(player, getAbates(player) + amount);
	}

	public static void addAbate(Player player) {
		addAbates(player, 1);
	}

	public static int getMortes(Player player) {
		return config.getInt("status." + player.getName() + ".geral.mortes", 0);
	}

	public static void setMortes(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".geral.mortes", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addMortes(Player player, int amount) {
		setMortes(player, getMortes(player) + amount);
	}

	public static void addMorte(Player player) {
		addMortes(player, 1);
	}

	public static int get1v1WinStreak(Player player) {
		return config.getInt("status." + player.getName() + ".1v1.winstreak");
	}

	public static void set1v1WinStreak(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".1v1.winstreak", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void add1v1WinStreak(Player player) {
		set1v1WinStreak(player, get1v1WinStreak(player) + 1);
	}

	public static int get1v1Vitorias(Player player) {
		return config.getInt("status." + player.getName() + ".1v1.vitorias");
	}

	public static void set1v1Vitorias(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".1v1.vitorias", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void add1v1Vitorias(Player player, int amount) {
		set1v1Vitorias(player, get1v1Vitorias(player) + amount);
	}

	public static void add1v1Vitoria(Player player) {
		add1v1Vitorias(player, 1);
	}

	public static int get1v1Derrotas(Player player) {
		return config.getInt("status." + player.getName() + ".1v1.derrotas", 0);
	}

	public static void set1v1Derrotas(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".1v1.derrotas", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void add1v1Derrotas(Player player, int amount) {
		set1v1Derrotas(player, get1v1Derrotas(player) + amount);
	}

	public static void add1v1Derrota(Player player) {
		add1v1Derrotas(player, 1);
	}

	public static int getSumoWinStreak(Player player) {
		return config.getInt("status." + player.getName() + ".sumo.winstreak", 0);
	}

	public static void setSumoWinStreak(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".sumo.winstreak", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void addSumoWinStreak(Player player) {
		setSumoWinStreak(player, getSumoWinStreak(player) + 1);

	}

	public static int getSumoVitorias(Player player) {
		return config.getInt("status." + player.getName() + ".sumo.vitorias", 0);
	}

	public static void setSumoVitorias(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".sumo.vitorias", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSumoVitorias(Player player, int amount) {
		setSumoVitorias(player, getSumoVitorias(player) + amount);
	}

	public static void addSumoVitoria(Player player) {
		addSumoVitorias(player, 1);
	}

	public static int getSumoDerrotas(Player player) {
		return config.getInt("status." + player.getName() + ".sumo.derrotas", 0);
	}

	public static void setSumoDerrotas(Player player, int amount) {
		try {
			config.set("status." + player.getName() + ".sumo.derrotas", amount);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSumoDerrotas(Player player, int amount) {
		setSumoDerrotas(player, getSumoDerrotas(player) + amount);
	}

	public void addSumoDerrota(Player player) {
		addSumoDerrotas(player, 1);
	}
}