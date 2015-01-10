package fr.alexmarsu.attaqueDefense;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private Main main;	
	private FileConfiguration config;
	
	public Config(Main main){
		this.setConfig(main.getConfig());
	}

	private Main getMain() {
		return main;
	}

	private void setMain(Main main) {
		this.main = main;
	}

	private FileConfiguration getConfig() {
		return config;
	}

	private void setConfig(FileConfiguration config) {
		this.config = config;
	}
	
	public int getTemps(){
		return this.getConfig().getInt("tempsRound");
	}
	
	public void addFlagSpawnPoint(Player player){
		main.getConfig().set("drapeau.x", player.getLocation().getBlockX());
		main.getConfig().set("drapeau.y", player.getLocation().getBlockY());
		main.getConfig().set("drapeau.z", player.getLocation().getBlockZ());
		main.getConfig().set("drapeau.world", player.getWorld().getName());
		main.saveConfig();
	}
	
	public Location getFlagSpawnPoint(){
		return new Location(main.getServer().getWorld(main.getConfig().getString("drapeau.world")),
				main.getConfig().getInt("drapeau.x"),
				main.getConfig().getInt("drapeau.y"),
				main.getConfig().getInt("drapeau.y"));
		
	}
	
	public void setSpawnPoint(Equipe e,Location loc){
		
	}
}
