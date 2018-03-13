package fr.ribardiere.tennis.bean;

public class Division {
	
	private int id;
	
	private String nom;
	
	public Division(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

}
