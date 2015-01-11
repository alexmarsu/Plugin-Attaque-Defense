package fr.alexmarsu.attaqueDefense;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlockState;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.material.Banner;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private ZoneDeJeu zone;
	private Jeu jeu;
	private Logger log;
	private Config configuration;
	private PluginListener pl;
	private Player joueurDrapeau;
	private Drapeau drapeau;

	@Override
	public void onEnable(){
		this.setPl(new PluginListener(this));
		this.setLog(getServer().getLogger());
		this.setZone(new ZoneDeJeu());
		this.setJeu(new Jeu(this));
		this.setConfiguration(new Config(this));
		this.initDrapeau();
		if(getConfig() == null){
			this.saveConfig();
		}
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			if (cmd.getName().equalsIgnoreCase("positionDrapeau")) {
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				addFlagSpawnPoint(loc);
				initDrapeau();
				return true;
			}else if(cmd.getName().equalsIgnoreCase("setSpawnAttaque")){
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				this.addSpawnPoint("attaque", pl);
				sender.sendMessage("Il y a "+this.getSpawnPoints("attaque").size()+" points de spawn pour les attaquants");
			}else if(cmd.getName().equalsIgnoreCase("setSpawnDefense")){
				Player pl = (Player) sender;
				Location loc = pl.getLocation();
				this.addSpawnPoint("defense", pl);
				sender.sendMessage("Il y a "+this.getSpawnPoints("defense").size()+" points de spawn pour les defenseurs");
			}else if(cmd.getName().equalsIgnoreCase("joinAttaqueDefense")){
				Player pl = (Player) sender;
				if(!this.getJeu().getListPlayer().contains(pl)){
					this.getJeu().getListPlayer().add(pl);
				}else{
					pl.sendMessage(ChatColor.RED+"Vous êtes déjà inscrit");
				}
			}else if(cmd.getName().equalsIgnoreCase("lancerAttaqueDefense")){
				this.getJeu().lancerJeu();
			}else if(cmd.getName().equalsIgnoreCase("positionBase")){
				Player pl = (Player) sender;
				if(args[0].equals("1")|| args[0].equals("2")){
					pl.sendMessage("test");
					this.addPositionBase(Integer.parseInt(args[0]), pl.getLocation());
				}
			}else if(cmd.getName().equalsIgnoreCase("testAQ")){
				sender.sendMessage(Integer.toString(getConfiguration().getTemps()));
				this.getJeu().addPlayer((Player) sender);
				this.getJeu().demarerTimer(this.getConfiguration().getTemps());
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

	private Config getConfiguration() {
		return configuration;
	}

	private void setConfiguration(Config configuration) {
		this.configuration = configuration;
	}
	
	public void addFlagSpawnPoint(Location loc){
		getConfig().set("drapeau.x", loc.getBlockX());
		getConfig().set("drapeau.y", loc.getBlockY());
		getConfig().set("drapeau.z", loc.getBlockZ());
		getConfig().set("drapeau.world", loc.getWorld().getName());
		saveConfig();
	}
	
	public void addPositionBase(int i,Location loc){
		getConfig().set("base."+i+".x", loc.getBlockX());
		getConfig().set("base."+i+".y", loc.getBlockY());
		getConfig().set("base."+i+".z", loc.getBlockZ());
		getConfig().set("base."+i+".world", loc.getWorld().getName());
		saveConfig();
	}
	
	public Location getPositionBase(int i){
		return new Location(this.getServer().getWorld(this.getConfig().getString("base."+i+".world")),
				getConfig().getInt("base."+i+".x"),
				getConfig().getInt("base."+i+".y"),
				getConfig().getInt("base."+i+".z"));
	}
	
	public Location getFlagSpawnPoint(){
		return new Location(this.getServer().getWorld(this.getConfig().getString("drapeau.world")),
				this.getConfig().getInt("drapeau.x"),
				this.getConfig().getInt("drapeau.y"),
				this.getConfig().getInt("drapeau.z"));
		
	}
	
	public void addSpawnPoint(String s,Player player){
		if(!getConfig().isSet("spawn."+s+".index")){
			getConfig().set("spawn."+s+".index", 0);
		}
		int actualIndex = getIndex(s)+1;
		getConfig().set("spawn."+s+"."+actualIndex+".x", player.getLocation().getBlockX());
		getConfig().set("spawn."+s+"."+actualIndex+".y", player.getLocation().getBlockY());
		getConfig().set("spawn."+s+"."+actualIndex+".z", player.getLocation().getBlockZ());
		getConfig().set("spawn."+s+"."+actualIndex+".world", player.getWorld().getName());
		setIndex(s, actualIndex);
		saveConfig();
	}
	
	public ArrayList<Location> getSpawnPoints(String s){
		ArrayList<Location> loc = new ArrayList<Location>();
		for(int i=1;i<=getIndex(s);i++){
			Location loca = new Location(this.getServer().getWorld(this.getConfig().getString("spawn."+s+"."+i+".world")),
								this.getConfig().getInt("spawn."+s+"."+i+".x"),
								this.getConfig().getInt("spawn."+s+"."+i+".y"),
								this.getConfig().getInt("spawn."+s+"."+i+".z"));
			loc.add(loca);
		}
		return loc;
	}
	
	public Location getSpawnPoint(String s,int i){
		if(getIndex(s) >= i){
			return new Location(this.getServer().getWorld(this.getConfig().getString("spawn."+s+"."+i+".world")),
					this.getConfig().getInt("spawn."+s+"."+i+".x"),
					this.getConfig().getInt("spawn."+s+"."+i+".y"),
					this.getConfig().getInt("spawn."+s+"."+i+".z"));
		}else{
			return null;
		}
	}
	
	public int getIndex(String s){
		return getConfig().getInt("spawn."+s+".index");
	}
	
	public void setIndex(String s, int i){
		getConfig().set("spawn."+s+".index",i);
		saveConfig();
	}
	
	public void bloquerJoueurs(){
		getPl().setBlock(true);
	}
	
	public void debloquerJoueurs(){
		getPl().setBlock(false);
	}

	public PluginListener getPl() {
		return pl;
	}

	public void setPl(PluginListener pl) {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(pl, this);
		this.pl = pl;
	}

	public void prendLeDrapeau(Player player){
		this.equiperDrapeau(player);
		this.supprimerDrapeauPos();
		this.setJoueurDrapeau(player);
		if(this.getEquipe(player).isAttaquant()){
			afficherPriseDrapeau(player);
			this.equiperDrapeau(player);
			this.supprimerDrapeauPos();
			this.setJoueurDrapeau(player);
		}
	}
	
	private void afficherPriseDrapeau(Player player) {
				
	}

	public Equipe getEquipe(Player player){
		Equipe[] e = this.getJeu().getEquipes();
		if(e[0].getListPlayer().contains(player)){
			return e[0];
		}else{
			return e[1];
		}
	}
	
	public void equiperDrapeau(Player player){
		player.getInventory().setHelmet(this.getDrapeau().getItem());
	}
	
	public void supprimerDrapeauPos(){
		this.getFlagSpawnPoint().getBlock().setType(Material.AIR);
	}
	
	public void ajouterDrapeauPos(){
		this.getDrapeau().placerDrapeau(this.getFlagSpawnPoint());
	}

	public Player getJoueurDrapeau() {
		return joueurDrapeau;
	}

	public void setJoueurDrapeau(Player joueurDrapeau) {
		this.joueurDrapeau = joueurDrapeau;
	}

	public void afficherDrapeauPerdu() {
		// TODO Auto-generated method stub
		
	}

	public void perdLeDrapeau(Player player) {
		this.afficherDrapeauPerdu();
		this.ajouterDrapeauPos();
	}
	
	private void initDrapeau(){
		if(getFlagSpawnPoint().getBlock().getType() != Material.STANDING_BANNER){
			getLog().info("Erreur : Aucun drapeau trouvé");
		}else{
			Block b = getFlagSpawnPoint().getBlock();
			System.out.println(Byte.toString(b.getData()));
			CraftBanner banner = new CraftBanner(b);
			ItemStack item = new ItemStack(Material.BANNER);
			BannerMeta meta = (BannerMeta)item.getItemMeta();
			meta.setBaseColor(banner.getBaseColor());
			meta.setPatterns(banner.getPatterns());
			item.setItemMeta(meta);
			this.setDrapeau(new Drapeau(item,meta,b,b.getData()));
		}
	}

	public Drapeau getDrapeau() {
		return drapeau;
	}

	public void setDrapeau(Drapeau drapeau) {
		this.drapeau = drapeau;
	}
}
