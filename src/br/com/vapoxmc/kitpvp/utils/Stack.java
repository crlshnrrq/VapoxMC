package br.com.vapoxmc.kitpvp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public final class Stack extends ItemStack implements Cloneable {

	public Stack(Material type) {
		this(type, 1);
	}

	public Stack(Material type, int amount) {
		this(type, amount, 0);
	}

	public Stack(Material type, int amount, int durability) {
		super(type, amount, (short) durability);
	}

	public Stack hideAttributes() {
		ItemMeta meta = this.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.setItemMeta(meta);
		return this;
	}

	public Stack hideEnchants() {
		ItemMeta meta = this.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		this.setItemMeta(meta);
		return this;
	}

	public Stack ench(Enchantment ench, int lvl) {
		ItemMeta meta = this.getItemMeta();
		meta.addEnchant(ench, lvl, true);
		this.setItemMeta(meta);
		return this;
	}

	public Stack display(String display) {
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(display);
		this.setItemMeta(meta);
		return this;
	}

	public Stack lore(String lore, int split, int length) {
		ItemMeta meta = this.getItemMeta();
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		String color = "";
		for (String word : lore.split(" ")) {
			if (word.startsWith("§") || word.startsWith("&")) {
				color = word.substring(0, 2);
				word = word.substring(2);
			}
			if (line.split(" ").length >= split || line.length() >= length) {
				lines.add(color + line);
				line = "";
			}
			line += (line.isEmpty() ? "" : " ") + word;
		}
		lines.add(color + line);
		meta.setLore(lines);
		this.setItemMeta(meta);
		return this;
	}

	public Stack lore(String... lore) {
		ItemMeta meta = this.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		this.setItemMeta(meta);
		return this;
	}

	public Stack lore(List<String> lore) {
		ItemMeta meta = this.getItemMeta();
		meta.setLore(lore);
		this.setItemMeta(meta);
		return this;
	}

	public Stack addLore(String... lore) {
		ItemMeta meta = this.getItemMeta();
		ArrayList<String> newLore = new ArrayList<>();
		if (meta.hasLore())
			newLore.addAll(meta.getLore());
		newLore.addAll(Arrays.asList(lore));
		meta.setLore(newLore);
		this.setItemMeta(meta);
		return this;
	}

	public Stack owner(String owner) {
		SkullMeta meta = (SkullMeta) this.getItemMeta();
		meta.setOwner(owner);
		this.setItemMeta(meta);
		return this;
	}

	public ItemStack toItemStack() {
		ItemStack item = new ItemStack(this.getType(), this.getAmount(), this.getDurability());
		ItemMeta itemMeta = item.getItemMeta(), meta = this.getItemMeta();
		for (ItemFlag flag : ItemFlag.values())
			if (meta.hasItemFlag(flag))
				itemMeta.addItemFlags(flag);
		for (Entry<Enchantment, Integer> entry : this.getEnchantments().entrySet())
			itemMeta.addEnchant(entry.getKey(), entry.getValue(), true);
		if (meta.hasDisplayName())
			itemMeta.setDisplayName(meta.getDisplayName());
		if (meta.hasLore())
			itemMeta.setLore(meta.getLore());
		if (meta instanceof SkullMeta && ((SkullMeta) meta).hasOwner())
			((SkullMeta) itemMeta).setOwner(((SkullMeta) meta).getOwner());
		item.setItemMeta(itemMeta);
		return item;
	}

	@Override
	public Stack clone() {
		Stack stack = new Stack(this.getType(), this.getAmount(), this.getDurability());
		ItemMeta stackMeta = stack.getItemMeta(), meta = this.getItemMeta();
		for (ItemFlag flag : ItemFlag.values())
			if (meta.hasItemFlag(flag))
				stackMeta.addItemFlags(flag);
		for (Entry<Enchantment, Integer> entry : this.getEnchantments().entrySet())
			stack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
		if (meta.hasDisplayName())
			stackMeta.setDisplayName(meta.getDisplayName());
		if (meta.hasLore())
			stackMeta.setLore(meta.getLore());
		if (meta instanceof SkullMeta && ((SkullMeta) meta).hasOwner())
			((SkullMeta) stackMeta).setOwner(((SkullMeta) meta).getOwner());
		stack.setItemMeta(stackMeta);
		return stack;
	}
}