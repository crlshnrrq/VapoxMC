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

	public static Geral getGeral() {
		return new Geral();
	}

	public static class Geral {

		public int getMoedas(Player player) {
			return config.getInt("status." + player.getName() + ".geral.moedas", 0);
		}

		public Geral setMoedas(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".geral.moedas", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addMoedas(Player player, int amount) {
			this.setMoedas(player, this.getMoedas(player) + amount);
			return getGeral();
		}

		public Geral drawMoedas(Player player, int amount) {
			int result = getMoedas(player) - amount;
			this.setMoedas(player, result < 0 ? 0 : result);
			return getGeral();
		}

		public int getPontos(Player player) {
			return config.getInt("status." + player.getName() + ".geral.pontos", 0);
		}

		public Geral setPontos(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".geral.pontos", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addPontos(Player player, int amount) {
			this.setPontos(player, this.getPontos(player) + amount);
			return getGeral();
		}

		public int getKillStreak(Player player) {
			return config.getInt("status." + player.getName() + ".geral.killstreak", 0);
		}

		public Geral setKillStreak(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".geral.killstreak", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addKillStreak(Player player) {
			this.setKillStreak(player, this.getKillStreak(player) + 1);
			return getGeral();
		}

		public int getAbates(Player player) {
			return config.getInt("status." + player.getName() + ".geral.abates", 0);
		}

		public Geral setAbates(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".geral.abates", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addAbates(Player player, int amount) {
			this.setAbates(player, this.getAbates(player) + amount);
			return getGeral();
		}

		public Geral addAbate(Player player) {
			return this.addAbates(player, 1);
		}

		public int getMortes(Player player) {
			return config.getInt("status." + player.getName() + ".geral.mortes", 0);
		}

		public Geral setMortes(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".geral.mortes", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addMortes(Player player, int amount) {
			this.setMortes(player, this.getMortes(player) + amount);
			return getGeral();
		}

		public Geral addMorte(Player player) {
			return this.addMortes(player, 1);
		}
	}

	public static UMvUM get1v1() {
		return new UMvUM();
	}

	public static class UMvUM {

		public int getWinStreak(Player player) {
			return config.getInt("status." + player.getName() + ".1v1.winstreak");
		}

		public UMvUM setWinStreak(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".1v1.winstreak", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return get1v1();
		}

		public UMvUM addWinStreak(Player player) {
			this.setWinStreak(player, this.getWinStreak(player) + 1);
			return get1v1();
		}

		public int getVitorias(Player player) {
			return config.getInt("status." + player.getName() + ".1v1.vitorias");
		}

		public UMvUM setVitorias(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".1v1.vitorias", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return get1v1();
		}

		public UMvUM addVitorias(Player player, int amount) {
			this.setVitorias(player, this.getVitorias(player) + amount);
			return get1v1();
		}

		public UMvUM addVitoria(Player player) {
			return this.addVitorias(player, 1);
		}

		public int getDerrotas(Player player) {
			return config.getInt("status." + player.getName() + ".1v1.derrotas", 0);
		}

		public UMvUM setDerrotas(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".1v1.derrotas", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return get1v1();
		}

		public UMvUM addDerrotas(Player player, int amount) {
			this.setDerrotas(player, this.getDerrotas(player) + amount);
			return get1v1();
		}

		public UMvUM addDerrota(Player player) {
			return addDerrotas(player, 1);
		}
	}

	public static Sumo getSumo() {
		return new Sumo();
	}

	public static class Sumo {

		public int getWinStreak(Player player) {
			return config.getInt("status." + player.getName() + ".sumo.winstreak", 0);
		}

		public Sumo setWinStreak(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".sumo.winstreak", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getSumo();
		}

		public Sumo addWinStreak(Player player) {
			this.setWinStreak(player, this.getWinStreak(player) + 1);
			return getSumo();
		}

		public int getVitorias(Player player) {
			return config.getInt("status." + player.getName() + ".sumo.vitorias", 0);
		}

		public Sumo setVitorias(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".sumo.vitorias", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getSumo();
		}

		public Sumo addVitorias(Player player, int amount) {
			this.setVitorias(player, this.getVitorias(player) + amount);
			return getSumo();
		}

		public Sumo addVitoria(Player player) {
			return this.addVitorias(player, 1);
		}

		public int getDerrotas(Player player) {
			return config.getInt("status." + player.getName() + ".sumo.derrotas", 0);
		}

		public Sumo setDerrotas(Player player, int amount) {
			try {
				config.set("status." + player.getName() + ".sumo.derrotas", amount);
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return getSumo();
		}

		public Sumo addDerrotas(Player player, int amount) {
			this.setDerrotas(player, this.getDerrotas(player) + amount);
			return getSumo();
		}

		public Sumo addDerrota(Player player) {
			return addDerrotas(player, 1);
		}
	}
}