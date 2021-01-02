package br.com.vapoxmc.kitpvp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public final class MySQL {

	private Connection connection;
	private final String host, database, username, password;
	private final int port;

	public MySQL(String host, int port, String database, String username, String password) throws SQLException {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;

		try {
			if (this.connection == null || this.connection.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username,
						this.password);
			}
		} catch (ClassNotFoundException ex) {
			Bukkit.getConsoleSender()
					.sendMessage("§cNão foi possível conectar ao Banco de Dados. (ClassNotFoundException)");
		}
	}

	public ResultSet selectFrom(String table, String parameters) throws SQLException {
		return connection.prepareStatement("SELECT * FROM `" + table + "` WHERE " + parameters).executeQuery();
	}

	public void createTable(String table, String parameters) throws SQLException {
		connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + table + "` " + parameters).execute();
	}

	public void insertInto(String table, String parameters, String values) throws SQLException {
		connection.prepareStatement("INSERT INTO `" + table + "` " + parameters + " VALUES " + values).executeUpdate();
	}

	public void update(String table, String parameters, String values) throws SQLException {
		connection.prepareStatement("UPDATE `" + table + "` SET " + values + " WHERE " + parameters).executeUpdate();
	}
}