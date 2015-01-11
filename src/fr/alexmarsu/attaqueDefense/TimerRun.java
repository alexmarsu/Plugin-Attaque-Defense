package fr.alexmarsu.attaqueDefense;

import org.bukkit.entity.Player;

public class TimerRun implements Runnable{
	private int temps;
	private Jeu jeu;
	
	public TimerRun(int temps,Jeu jeu) {
		this.setTemps(temps);
		this.setJeu(jeu);
	}
	
	@Override
	public void run() {
		jeu.setTempsScoreBoard(temps);
		for(Player p:jeu.getListPlayer()){
			jeu.afficherScoreBoard(p);
		}
		setTemps(getTemps()-1);
		if(getTemps() == -1){
			jeu.finTimer();
		}
	}

	private int getTemps() {
		return temps;
	}

	private void setTemps(int temps) {
		this.temps = temps;
	}

	private Jeu getJeu() {
		return jeu;
	}

	private void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
