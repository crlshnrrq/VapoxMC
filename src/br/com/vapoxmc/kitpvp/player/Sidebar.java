package br.com.vapoxmc.kitpvp.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Sidebar {

	private final String name;
	private String displayName;
	private List<String> lines;

	public Sidebar(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
		this.lines = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getLines() {
		return this.lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void addLine(String line) {
		while (this.getLines().contains(line))
			line += "§r";
		this.getLines().add(line);
	}

	public void removeLine(String line) {
		this.getLines().remove(line);
	}

	public void sendSidebar(Player player) {
		Scoreboard scoreboard = player.getScoreboard();
		Objective objective = scoreboard.getObjective(this.getName());
		if (objective != null) {
			objective.unregister();
			objective = scoreboard.registerNewObjective(this.getName(), "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(this.getDisplayName());
		}

		int index = this.getLines().size();
		for (String line : this.getLines()) {
			String prefix = "";
			if (line.length() > 16) {
				prefix = line.substring(0, 16);
				line = line.substring(16);
			}

			Team team = scoreboard.getTeam("line-" + index);
			if (team == null)
				team = scoreboard.registerNewTeam("line-" + index);
			team.setPrefix(prefix);
			team.setSuffix("");

			objective.getScore(line).setScore(index);
			index--;
		}
	}

	public void updateLine(Player player, String line, String suffix) {
		String prefix = "";
		if (line.length() > 16) {
			prefix = line.substring(0, 16);
			line = line.substring(16);
		}
		for (Team teams : player.getScoreboard().getTeams()) {
			if (teams.getName().startsWith("line-") && teams.getPrefix().equals(prefix) && teams.hasEntry(line))
				teams.setSuffix(suffix);
		}
	}

	public void update(Player player) {
	}
}