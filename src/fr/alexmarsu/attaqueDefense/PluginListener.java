package fr.alexmarsu.attaqueDefense;

import java.util.Collection;

import net.minecraft.server.v1_8_R1.BlockBanner;
import net.minecraft.server.v1_8_R1.ItemBanner;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginListener implements Listener{
	
	private Main main;
	
	public PluginListener(Main main) {
		super();
		this.main = main;
	}
	
	@EventHandler
    public void playerinteract(PlayerInteractEvent e){
        if(e.getAction() == Action.LEFT_CLICK_BLOCK){
            Player player = e.getPlayer();
            Block b = e.getClickedBlock();
            player.sendMessage(b.toString());
            if(b.getType().equals(Material.STANDING_BANNER) && main.getZone().getPosDrapeau() == b.getLocation()){
            	CraftBanner banner = new CraftBanner(b);
            }
        
        }
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		main.getServer().dispatchCommand(main.getServer().getConsoleSender(),"title "+e.getPlayer().getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Attaquant\",\"color\":\"red\",\"bold\":\"true\"}]}");
		main.getServer().dispatchCommand(main.getServer().getConsoleSender(),"title "+e.getPlayer().getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Capturez le drapeau ennemi\",\"color\":\"green\",\"bold\":\"true\"}]}");
	}
		
}
