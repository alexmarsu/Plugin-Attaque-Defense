package fr.alexmarsu.attaqueDefense;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Jeu {
	
	private ArrayList<Player> listPlayer;
	private Equipe[] equipes;
	private Main main;
	private Scoreboard scoreboard;
	private int indexMinutes;
	private int taskID;
	
	public Jeu(Main main){
		this.setMain(main);
		this.setListPlayer(new ArrayList<Player>());
		this.initEquipes();
		this.initScoreBoard();
		this.setIndexMinutes(0);
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
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Vous �tes attaquant !\",\"color\":\"red\",\"bold\":\"true\"}]}");
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Ramenez le drapeau �nemis dans la base\",\"color\":\"green\",\"bold\":\"true\"}]}");
		}
		for(Player p:equipeOpposee(attaquant).getListPlayer()){
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" title {\"text\":\"\",\"extra\":[{\"text\":\"Vous �tes d�fenseur !\",\"color\":\"red\",\"bold\":\"true\"}]}");
			serv.getConsoleSender().sendMessage("/title "+p.getName()+" subtitle {\"text\":\"\",\"extra\":[{\"text\":\"Prot�gez le drapeau\",\"color\":\"green\",\"bold\":\"true\"}]}");
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
	
	public void demarerTimer(int temps){
		this.setTaskID(Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new TimerRun(temps, this),0L, 20L));
	}
	
	public void initScoreBoard(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard sc = manager.getNewScoreboard();
		Objective obj = sc.registerNewObjective(ChatColor.GREEN+"Temps :", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.setScoreboard(sc);
	}
	
	public void afficherScoreBoard(Player p){
		p.setScoreboard(this.getScoreboard());
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public void setTempsScoreBoard(int temps) {
		this.getScoreboard().resetScores(ChatColor.RED+""+getIndexMinutes()+" minutes");
		this.setIndexMinutes((int)Math.floor(temps/60));
		this.getScoreboard().getObjective(ChatColor.GREEN+"Temps :").getScore(ChatColor.RED+""+getIndexMinutes()+" minutes").setScore(temps%60);
	}

	public int getIndexMinutes() {
		return indexMinutes;
	}

	public void setIndexMinutes(int indexMinutes) {
		this.indexMinutes = indexMinutes;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public void finTimer() {
		Bukkit.getScheduler().cancelTask(getTaskID());
		for(Player p:getListPlayer()){
			p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);;
		}
	}
}
