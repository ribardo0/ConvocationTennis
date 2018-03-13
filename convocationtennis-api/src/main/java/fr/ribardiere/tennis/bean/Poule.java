package fr.ribardiere.tennis.bean;

public class Poule {
	
	private int id;
	
	private String nom;
	
	public Poule(int  id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
