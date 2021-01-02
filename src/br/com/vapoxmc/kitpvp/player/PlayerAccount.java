package br.com.vapoxmc.kitpvp.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.MySQL;

public final class PlayerAccount {

	private static MySQL mysql;

	public static void createConnection() {
		try {
			mysql = new MySQL("127.0.0.1", 3306, "vapoxmc", "root", "");
		} catch (SQLException ex) {
			Bukkit.getConsoleSender().sendMessage("§cNão foi possível conectar ao Banco de Dados. (SQLException)");
		}

		try {
			mysql.createTable("geral",
					"(Nickname VARCHAR(16), Moedas INT(13), Pontos INT(13), KillStreak INT(13), Abates INT(13), Mortes INT(13))");
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§cNão foi possível criar a Tabela \"geral\". (SQLException)");
		}

		try {
			mysql.createTable("umvum", "(Nickname VARCHAR(16), WinStreak INT(13), Vitorias INT(13), Derrotas INT(13))");
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§cNão foi possível criar a Tabela \"umvum\". (SQLException)");
		}

		try {
			mysql.createTable("sumo", "(Nickname VARCHAR(16), WinStreak INT(13), Vitorias INT(13), Derrotas INT(13))");
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§cNão foi possível criar a Tabela \"sumo\". (SQLException)");
		}
	}

	public static boolean existAccount(String nickname) {
		try {
			return mysql.selectFrom("geral", "Nickname='" + nickname + "'").next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void createAccount(Player player) {
		if (!existAccount(player.getName())) {
			try {
				mysql.insertInto("geral", "(Nickname, Moedas, Pontos, Abates, Mortes, KillStreak)",
						"('" + player.getName() + "', '0', '0', '0', '0', '0')");
				mysql.insertInto("umvum", "(Nickname, Vitorias, Derrotas, WinStreak)",
						"('" + player.getName() + "', '0', '0', '0')");
				mysql.insertInto("sumo", "(Nickname, Vitorias, Derrotas, WinStreak)",
						"('" + player.getName() + "', '0', '0', '0')");
			} catch (SQLException ex) {
				ex.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage("§cNão foi possível criar a Conta de " + player.getName() + ". (SQLException)");
			}
		}
	}

	public static Geral getGeral() {
		return new Geral();
	}

	public static class Geral {

		public int getMoedas(Player player) {
			try {
				ResultSet set = mysql.selectFrom("geral", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Moedas");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Geral setMoedas(Player player, int amount) {
			try {
				mysql.update("geral", "Nickname='" + player.getName() + "'", "Moedas='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("geral", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Pontos");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Geral setPontos(Player player, int amount) {
			try {
				mysql.update("geral", "Nickname='" + player.getName() + "'", "Pontos='" + amount + "'");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addPontos(Player player, int amount) {
			this.setPontos(player, this.getPontos(player) + amount);
			return getGeral();
		}

		public int getKillStreak(Player player) {
			try {
				ResultSet set = mysql.selectFrom("geral", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("KillStreak");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Geral setKillStreak(Player player, int amount) {
			try {
				mysql.update("geral", "Nickname='" + player.getName() + "'", "KillStreak='" + amount + "'");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return getGeral();
		}

		public Geral addKillStreak(Player player) {
			this.setKillStreak(player, this.getKillStreak(player) + 1);
			return getGeral();
		}

		public int getAbates(Player player) {
			try {
				ResultSet set = mysql.selectFrom("geral", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Abates");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Geral setAbates(Player player, int amount) {
			try {
				mysql.update("geral", "Nickname='" + player.getName() + "'", "Abates='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("geral", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Mortes");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Geral setMortes(Player player, int amount) {
			try {
				mysql.update("geral", "Nickname='" + player.getName() + "'", "Mortes='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("umvum", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("WinStreak");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public UMvUM setWinStreak(Player player, int amount) {
			try {
				mysql.update("umvum", "Nickname='" + player.getName() + "'", "WinStreak='" + amount + "'");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return get1v1();
		}

		public UMvUM addWinStreak(Player player) {
			this.setWinStreak(player, this.getWinStreak(player) + 1);
			return get1v1();
		}

		public int getVitorias(Player player) {
			try {
				ResultSet set = mysql.selectFrom("umvum", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Vitorias");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public UMvUM setVitorias(Player player, int amount) {
			try {
				mysql.update("umvum", "Nickname='" + player.getName() + "'", "Vitorias='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("umvum", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Derrotas");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public UMvUM setDerrotas(Player player, int amount) {
			try {
				mysql.update("umvum", "Nickname='" + player.getName() + "'", "Derrotas='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("sumo", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("WinStreak");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Sumo setWinStreak(Player player, int amount) {
			try {
				mysql.update("sumo", "Nickname='" + player.getName() + "'", "WinStreak='" + amount + "'");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return getSumo();
		}

		public Sumo addWinStreak(Player player) {
			this.setWinStreak(player, this.getWinStreak(player) + 1);
			return getSumo();
		}

		public int getVitorias(Player player) {
			try {
				ResultSet set = mysql.selectFrom("sumo", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Vitorias");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Sumo setVitorias(Player player, int amount) {
			try {
				mysql.update("sumo", "Nickname='" + player.getName() + "'", "Vitorias='" + amount + "'");
			} catch (SQLException ex) {
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
			try {
				ResultSet set = mysql.selectFrom("sumo", "Nickname='" + player.getName() + "'");
				set.next();
				return set.getInt("Derrotas");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return 0;
		}

		public Sumo setDerrotas(Player player, int amount) {
			try {
				mysql.update("sumo", "Nickname='" + player.getName() + "'", "Derrotas='" + amount + "'");
			} catch (SQLException ex) {
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