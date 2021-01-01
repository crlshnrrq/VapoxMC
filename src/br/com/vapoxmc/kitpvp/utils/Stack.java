package br.com.vapoxmc.kitpvp.utils;

import java.util.Arrays;

import org.bukkit.Material;
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

	public Stack display(String display) {
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(display);
		this.setItemMeta(meta);
		return this;
	}

	public Stack lore(String... lore) {
		ItemMeta meta = this.getItemMeta();
		meta.setLore(Arrays.asList(lore));
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