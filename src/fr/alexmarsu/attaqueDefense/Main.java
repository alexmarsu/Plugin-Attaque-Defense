package fr.alexmarsu.attaqueDefense;

import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.World;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.material.Banner;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private ZoneDeJeu zone;
	private Jeu jeu;
	private Logger log;
	
	@Override
	public void onEnable(){
		Listener l = new PluginListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(l, this);
		this.setLog(getServer().getLogger());
		this.setZone(new ZoneDeJeu());
		this.setJeu(new Jeu(this));
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			if (cmd.getName().equalsIgnoreCase("positionDrapeau")) {
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				this.getZone().setPosDrapeau(loc);
				return true;
			}else if(cmd.getName().equalsIgnoreCase("setSpawnAttaque")){
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				this.getZone().addSpawnAttaquant(loc);
			}else if(cmd.getName().equalsIgnoreCase("setSpawnDefense")){
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				this.getZone().addSpawnDefenseur(loc);
			}else if(cmd.getName().equalsIgnoreCase("joinAttaqueDefense")){
				Player pl = (Player) sender;
				if(!this.getJeu().getListPlayer().contains(pl)){
					this.getJeu().getListPlayer().add(pl);
				}else{
					pl.sendMessage(ChatColor.RED+"Vous êtes déjà inscrit");
				}
			}else if(cmd.getName().equalsIgnoreCase("lancerAttaqueDefense")){
				this.getJeu().lancerJeu();
			}
		}else{
			sender.sendMessage("Cette commande doit être lancée par un joueur");
		}
		/*CraftWorld world = (CraftWorld) loc.getWorld();
		CraftBlockState ban = CraftBanner.getBlockState(world.getHandle(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		pl.sendMessage(ban.toString());*/
		return false;
	}

	public ZoneDeJeu getZone() {
		return zone;
	}

	private void setZone(ZoneDeJeu zone) {
		this.zone = zone;
	}
	
	public Jeu getJeu(){
		return jeu;
	}
	
	private void setJeu(Jeu jeu){
		this.jeu = jeu;
	}

	public Logger getLog() {
		return log;
	}

	private void setLog(Logger log) {
		this.log = log;
	}
}
