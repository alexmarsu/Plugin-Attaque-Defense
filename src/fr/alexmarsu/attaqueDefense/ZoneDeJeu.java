package fr.alexmarsu.attaqueDefense;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBanner;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_8_R1.BlockBanner;
import net.minecraft.server.v1_8_R1.Material;
import net.minecraft.server.v1_8_R1.Position;

public class ZoneDeJeu {
	
	private ArrayList<Location> spawnPointsAtt;
	private ArrayList<Location> spawnPointsDef;
	private Location posDrapeau;
	private Block drapeau;
	
	public ZoneDeJeu(){
		this.setSpawnPointsAtt(new ArrayList<Location>());
		this.setSpawnPointsDef(new ArrayList<Location>());
		this.creerDrapeau();
	}

	public Location getPosDrapeau() {
		return posDrapeau;
	}
	
	public ArrayList<Location> getSpawnPointsDef() {
		return spawnPointsDef;
	}
	
	public ArrayList<Location> getSpawnPointsAtt() {
		return spawnPointsAtt;
	}

	private void setSpawnPointsAtt(ArrayList<Location> spawnPointsAtt) {
		this.spawnPointsAtt = spawnPointsAtt;
	}

	public void setPosDrapeau(Location PosDrapeau) {
		this.posDrapeau = PosDrapeau;
	}

	private void setSpawnPointsDef(ArrayList<Location> spawnPointsDef) {
		this.spawnPointsDef = spawnPointsDef;
	}
	
	public void creerDrapeau(){
	}
	
	public void enleverDrapeau(){
		getPosDrapeau().getBlock().setType(org.bukkit.Material.AIR);
	}
	
	public void placerDrapeau(){
		
	}

	public Block getDrapeau() {
		return drapeau;
	}

	private void setDrapeau(Block drapeau) {
		this.drapeau = drapeau;
	}
	
	public void addSpawnAttaquant(Location loc){
		this.getSpawnPointsAtt().add(loc);
	}
	
	public void addSpawnDefenseur(Location loc){
		this.getSpawnPointsDef().add(loc);
	}
}
