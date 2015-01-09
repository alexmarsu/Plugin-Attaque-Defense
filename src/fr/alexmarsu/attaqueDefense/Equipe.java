package fr.alexmarsu.attaqueDefense;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Equipe {

	private ArrayList<Player> listPlayer;
	private String name;
	private ChatColor color;	

	public Equipe(String name, ChatColor color) {
		this.setColor(color);
		this.setName(name);
		this.setListPlayer(new ArrayList<Player>());
	}

	public ArrayList<Player> getListPlayer() {
		return listPlayer;
	}

	public String getName() {
		return name;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setListPlayer(ArrayList<Player> listPlayer) {
		this.listPlayer = listPlayer;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setColor(ChatColor color) {
		this.color = color;
	}
	
	public void addPlayer(Player player){
		this.getListPlayer().add(player);
	}
}
