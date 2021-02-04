package br.com.vapoxmc.vapoxpvp.kitssystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.kitssystem.commands.KitCommand;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerRemoveKitEvent;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerSelectKitEvent;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.InfoKitGUI;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.KitsDesabilitadosGUI;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.KitsHabilitadosGUI;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.SeusKitsGUI;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.TodosOsKitsGUI;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.AjninKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.AnchorKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.AntiStomperKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.ArcherKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.BoxerKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.CamelKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.CriticalKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.FishermanKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.FlashKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.GrandpaKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.KangarooKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.MagmaKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.MonkKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.NinjaKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.PoseidonKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.PvPKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.ReaperKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.ScoutKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.SnailKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.StomperKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.SwitcherKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.ThorKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.TurtleKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.UrgalKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.VampireKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.VikingKit;
import br.com.vapoxmc.vapoxpvp.kitssystem.kits.ViperKit;
import br.com.vapoxmc.vapoxpvp.system.BukkitSystem;

public final class KitsSystem extends BukkitSystem {

	private Kit noneKit, defaultKit;
	private ArrayList<Kit> kits;
	private HashMap<UUID, Kit> kitMap;

	public Kit getNoneKit() {
		return this.noneKit;
	}

	public Kit getDefaultKit() {
		return this.defaultKit;
	}

	public ArrayList<Kit> getKits() {
		return this.kits;
	}

	public boolean containsKit(Kit kit) {
		return this.getKits().contains(kit);
	}

	public void addKit(Kit kit) {
		if (!this.containsKit(kit))
			this.getKits().add(kit);
		if (kit instanceof Listener)
			this.addListener((Listener) kit);
	}

	public void removeKit(Kit kit) {
		this.getKits().remove(kit);
		if (kit instanceof Listener)
			this.removeListener((Listener) kit);
	}

	public Kit getKitByName(String name) {
		return this.getKits().stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public KitsSystem(Plugin plugin) {
		super("Kits", plugin);
	}

	@Override
	public void onEnable() {
		if (this.getNoneKit() == null)
			this.noneKit = new Kit("Nenhum", "Sem descrição.", new Stack(Material.STAINED_GLASS_PANE), true);
		if (this.getDefaultKit() == null)
			this.defaultKit = new PvPKit();
		if (this.getKits() == null)
			this.kits = new ArrayList<>();
		if (this.kitMap == null)
			this.kitMap = new HashMap<>();

		this.addListener(new InfoKitGUI());
		this.addListener(new KitsDesabilitadosGUI());
		this.addListener(new KitsHabilitadosGUI());
		this.addListener(new SeusKitsGUI());
		this.addListener(new TodosOsKitsGUI());
		this.addListener(new KitsListeners());

		this.addCommand(new KitCommand());

		this.addKit(this.getDefaultKit());
		this.addKit(new AjninKit());
		this.addKit(new AnchorKit());
		this.addKit(new AntiStomperKit());
		this.addKit(new ArcherKit());
		this.addKit(new BoxerKit());
		this.addKit(new CamelKit());
		this.addKit(new CriticalKit());
		this.addKit(new FishermanKit());
		this.addKit(new FlashKit());
		this.addKit(new GrandpaKit());
		this.addKit(new KangarooKit());
		this.addKit(new MagmaKit());
		this.addKit(new MonkKit());
		this.addKit(new NinjaKit());
		this.addKit(new PoseidonKit());
		this.addKit(new ReaperKit());
		this.addKit(new ScoutKit());
		this.addKit(new SnailKit());
		this.addKit(new StomperKit());
		this.addKit(new SwitcherKit());
		this.addKit(new ThorKit());
		this.addKit(new TurtleKit());
		this.addKit(new UrgalKit());
		this.addKit(new VampireKit());
		this.addKit(new VikingKit());
		this.addKit(new ViperKit());

		super.onEnable();
	}

	@Override
	public void onDisable() {
		if (this.getNoneKit() != null) {
			this.removeKit(this.getNoneKit());
			this.noneKit = null;
		}
		if (this.getDefaultKit() != null) {
			this.removeKit(this.getDefaultKit());
			this.defaultKit = null;
		}
		if (this.getKits() != null) {
			this.getKits().clear();
			this.kits = null;
		}
		if (this.kitMap != null) {
			this.kitMap.clear();
			this.kitMap = null;
		}
		super.onDisable();
	}

	public int getPlayersWithKits() {
		return this.kitMap.size();
	}

	public int getPlayersWithKit(Kit kit) {
		return (int) this.kitMap.values().stream().filter(kits -> kits == kit).count();
	}

	public boolean hasKit(Player player) {
		return this.kitMap.containsKey(player.getUniqueId());
	}

	public Kit getKit(Player player) {
		return this.kitMap.getOrDefault(player.getUniqueId(), this.getNoneKit());
	}

	public boolean setKit(Player player, Kit kit) {
		PlayerSelectKitEvent event = new PlayerSelectKitEvent(player, kit);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			kit.applyKit(player);
			this.kitMap.put(player.getUniqueId(), kit);
			return true;
		}
		return false;
	}

	public Kit removeKit(Player player) {
		PlayerRemoveKitEvent event = new PlayerRemoveKitEvent(player, this.getKit(player));
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled())
			this.kitMap.remove(player.getUniqueId());

		return event.getKit();
	}
}