package fr.ribardiere.tennis.bean;

public class Equipe {

	private int id;
	private String nom;
	
	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public int getId() {
		return id;
	}


	public Equipe(int id) {
		this.id = id;
	}
	
}
