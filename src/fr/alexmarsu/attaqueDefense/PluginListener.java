package fr.alexmarsu.attaqueDefense;

import java.util.Collection;

import javax.persistence.Entity;

import net.minecraft.server.v1_8_R1.BlockBanner;
import net.minecraft.server.v1_8_R1.ItemBanner;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginListener implements Listener{
	
	private Main main;
	public Boolean block;
	
	public PluginListener(Main main) {
		super();
		this.main = main;
		this.setBlock(false);
	}
		
	@EventHandler
    public void playerinteract(PlayerInteractEvent e){
        if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK){
        	Location loc = e.getClickedBlock().getLocation();
        	if(loc.getBlockX() == main.getFlagSpawnPoint().getBlockX() &&
        			loc.getBlockY() == main.getFlagSpawnPoint().getBlockY() &&
        			loc.getBlockZ() == main.getFlagSpawnPoint().getBlockZ() &&
        			loc.getWorld() == main.getFlagSpawnPoint().getWorld()){
        		main.prendLeDrapeau(e.getPlayer());
        	}
        }
    }
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e){
		if(block){
			Location loc = e.getPlayer().getLocation();
			if(loc.getX()!=e.getTo().getX() || loc.getY()!=e.getTo().getY() || loc.getZ()!=e.getTo().getZ()){
				e.getPlayer().teleport(e.getPlayer().getLocation());
			}
		}else{
			Location loc1 = main.getPositionBase(1);
			Location loc2 = main.getPositionBase(2);
			Location locp = e.getPlayer().getLocation();
			Location tmp;
			if(((loc1.getBlockX() <= loc2.getBlockX() && (
					(locp.getBlockX() >= loc1.getBlockX() &&  locp.getBlockX() <= loc2.getBlockX()) &&
					(locp.getBlockZ() >= loc1.getBlockZ() &&  locp.getBlockZ() <= loc2.getBlockZ()))) ||
					(loc1.getBlockX() >= loc2.getBlockX() && (
							(locp.getBlockX() >= loc2.getBlockX() &&  locp.getBlockX() <= loc1.getBlockX()) &&
							(locp.getBlockZ() >= loc2.getBlockZ() &&  locp.getBlockZ() <= loc1.getBlockZ())))) && loc1.getBlockY() == locp.getBlockY()){
				e.getPlayer().sendMessage("test");
			}

		
			
		}
	}
	
	@EventHandler
	public void PlayerDeathEvent(PlayerDeathEvent e){
		if(e.getEntity() == main.getJoueurDrapeau()){
			main.perdLeDrapeau(e.getEntity());
			e.getEntity().sendMessage("Vous perdez le drapeau");
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		main.getServer().dispatchCommand(main.getServer().getConsoleSender(),"title "+e.getPlayer().getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Attaquant\",\"color\":\"red\",\"bold\":\"true\"}]}");
		main.getServer().dispatchCommand(main.getServer().getConsoleSender(),"title "+e.getPlayer().getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Capturez le drapeau ennemi\",\"color\":\"green\",\"bold\":\"true\"}]}");
	}
	
	public void setBlock(boolean b){
		block = b;
	}
	
	public boolean getBlock(){
		return block;
	}
		
}
