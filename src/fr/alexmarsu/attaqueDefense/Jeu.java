package fr.alexmarsu.attaqueDefense;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Jeu {
	
	private ArrayList<Player> listPlayer;
	private Equipe[] equipes;
	private Main main;
	
	public Jeu(Main main){
		this.setMain(main);
		this.setListPlayer(new ArrayList<Player>());
		this.initEquipes();
	}
	
	private void initEquipes(){
		Equipe[] eq = {new Equipe("bleu",ChatColor.BLUE),new Equipe("rouge",ChatColor.RED)};
		this.setEquipes(eq);
	}

	public ArrayList<Player> getListPlayer() {
		return listPlayer;
	}

	public Equipe[] getEquipes() {
		return equipes;
	}

	private void setListPlayer(ArrayList<Player> listPlayer) {
		this.listPlayer = listPlayer;
	}

	private void setEquipes(Equipe[] equipes) {
		this.equipes = equipes;
	}
	
	private Equipe equipeOpposee(Equipe equipe){
		if(getEquipes()[0] == equipe){
			return getEquipes()[1];
		}else{
			return getEquipes()[0];
		}
	}
	
	public boolean addPlayer(Player player){
		if(!this.getListPlayer().contains(player)){
			this.getListPlayer().add(player);
			return true;
		}else{
			return false;
		}
	}
	
	public void lancerJeu(){
		Random rand = new Random();
		int i = (int)(getListPlayer().size()/2);
		for(int j = 0;j<=i;j++){
			Player player = this.getListPlayer().get(rand.nextInt(getListPlayer().size()));
			this.getEquipes()[0].addPlayer(player);
			this.getListPlayer().remove(player);
		}
		this.getEquipes()[1].setListPlayer(this.getListPlayer());
		
		this.demarerRound(getEquipes()[0],getEquipes()[1]);
	}

	private void afficherTitres(Equipe attaquant) {
		Server serv = getMain().getServer();
		for(Player p:attaquant.getListPlayer()){
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Vous êtes attaquant !\",\"color\":\"red\",\"bold\":\"true\"}]}");
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Ramenez le drapeau énemis dans la base\",\"color\":\"green\",\"bold\":\"true\"}]}");
		}
		for(Player p:equipeOpposee(attaquant).getListPlayer()){
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Vous êtes défenseur !\",\"color\":\"red\",\"bold\":\"true\"}]}");
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Protégez le drapeau\",\"color\":\"green\",\"bold\":\"true\"}]}");
		}
		
	}

	private Main getMain() {
		return main;
	}

	private void setMain(Main main) {
		this.main = main;
	}
	
	public void demarerRound(Equipe attaquant, Equipe defenseur){
		attaquant.setAttaquant(true);
		defenseur.setAttaquant(false);
		this.afficherTitres(attaquant);
	}
}
