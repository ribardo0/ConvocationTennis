package fr.ribardiere.tennis.bean;

import java.util.ArrayList;
import java.util.List;

public class Poule {
	
	private int id;
	private String nom;
	private String categorieChampionnat;
	private String nomChampionnat;
	private String nomDivision;
	private String lettrePoule;
	
	private List<Equipe> equipes;
	
	public Poule(int  id) {
		this.id = id;
		equipes = new ArrayList<>();
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
	
	public void setEquipes(List<Equipe> equipes) {
	    this.equipes = equipes;
	}
	
	public List<Equipe> getEquipes() {
	    return equipes;
	}

    public String getCategorieChampionnat() {
        return categorieChampionnat;
    }

    public void setCategorieChampionnat(String categorieChampionnat) {
        this.categorieChampionnat = categorieChampionnat;
    }

    public String getNomChampionnat() {
        return nomChampionnat;
    }

    public void setNomChampionnat(String nomChampionnat) {
        this.nomChampionnat = nomChampionnat;
    }

    public String getNomDivision() {
        return nomDivision;
    }

    public void setNomDivision(String nomDivision) {
        this.nomDivision = nomDivision;
    }

    public String getLettrePoule() {
        return lettrePoule;
    }

    public void setLettrePoule(String lettrePoule) {
        this.lettrePoule = lettrePoule;
    }

}
