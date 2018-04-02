package fr.ribardiere.tennis;

import java.util.List;

import org.apache.http.HttpHost;

import fr.ribardiere.tennis.bean.Championnat;
import fr.ribardiere.tennis.bean.Division;
import fr.ribardiere.tennis.bean.Equipe;
import fr.ribardiere.tennis.bean.Poule;
import fr.ribardiere.tennis.services.ChampionnatService;
import fr.ribardiere.tennis.services.PouleService;

public class Main {

	// Les paramètres à renseigner pour soumettre le formulaire
	private static final String niveau = "D";
	private static final String annee = "2018"; // Pour la saison 2017 -2018
	private static final String sexe = "F"; // Parmi H, F ou M
	private static final String categorie = "200"; // 80 pour + de 8ans, 180
													// pour 17/18, 200 pour
													// senior, 350 pour +35 ans,
													// etc...
	private static final String departement = "44";

	private static final int idChampionnat = 82133498;
	
	private static final int idPoule = 325487;

	private static ChampionnatService championnatService;
	private static PouleService pouleService;

	//public static final HttpHost proxy = new HttpHost("127.0.0.1", 3128, "http");

	public static void main(String[] args) {

		PouleService pouleService = new PouleService();
		List<Equipe> equipes = pouleService.consulterEquipe(idPoule);
		for (Equipe equipe: equipes) {
			System.out.println(equipe.getNom() + equipe.getId());
		}
		// System.out.println(divisions);

	}

}
