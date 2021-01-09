package br.com.vapoxmc.kitpvp.utils;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public final class VapoxUtils {

	public static int getRandomPoints() {
		return (int) (Math.random() * -20 + 30);
	}

	public static int getRandomCoins() {
		return (int) (Math.random() * -20 + 30);
	}

	public static void sendActionBar(Player player, String message) {
		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{'text': '" + message + "'}"), (byte) 2));
	}

	public static void sendTab(Player player, String header, String footer) {
		try {

			PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
			Field head = packet.getClass().getDeclaredField("a");
			head.setAccessible(true);
			head.set(packet, ChatSerializer.a("'" + header + "'"));

			Field foot = packet.getClass().getDeclaredField("b");
			foot.setAccessible(true);
			foot.set(packet, ChatSerializer.a("'" + footer + "'"));

			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
	}
}