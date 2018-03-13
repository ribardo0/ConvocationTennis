package fr.ribardiere.tennis.bean;

import java.util.Date;

public class Rencontre {
	
	private Equipe equipeDomicile;
	private Equipe equipeExterieur;
	private Date date;
	private int journee;
	public Equipe getEquipeDomicile() {
		return equipeDomicile;
	}
	public void setEquipeDomicile(Equipe equipeDomicile) {
		this.equipeDomicile = equipeDomicile;
	}
	public Equipe getEquipeExterieur() {
		return equipeExterieur;
	}
	public void setEquipeExterieur(Equipe equipeExterieur) {
		this.equipeExterieur = equipeExterieur;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getJournee() {
		return journee;
	}
	public void setJournee(int journee) {
		this.journee = journee;
	}

}
