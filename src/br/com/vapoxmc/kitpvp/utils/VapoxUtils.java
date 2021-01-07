package br.com.vapoxmc.kitpvp.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

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
}