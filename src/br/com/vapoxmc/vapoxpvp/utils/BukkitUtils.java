package br.com.vapoxmc.vapoxpvp.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public final class BukkitUtils {

	private static Object getPrivateField(Object object, String field) {
		try {
			Class<?> clazz = object.getClass();
			Field objectField = clazz.getDeclaredField(field);
			objectField.setAccessible(true);
			Object result = objectField.get(object);
			objectField.setAccessible(false);
			return result;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void registerCommand(Command command) {
		((CraftServer) Bukkit.getServer()).getCommandMap().register(command.getName(), command);
	}

	@SuppressWarnings("unchecked")
	public static void unregisterCommand(Command command) {
		SimpleCommandMap map = ((CraftServer) Bukkit.getServer()).getCommandMap();
		HashMap<String, Command> commands = (HashMap<String, Command>) getPrivateField(map, "knownCommands");
		commands.remove(command.getName());
	}
}