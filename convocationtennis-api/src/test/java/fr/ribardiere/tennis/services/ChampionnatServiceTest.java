package fr.ribardiere.tennis.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fr.ribardiere.tennis.bean.Championnat;
import fr.ribardiere.tennis.bean.Division;
import fr.ribardiere.tennis.bean.Poule;

public class ChampionnatServiceTest {

	public ChampionnatService championnatService = new ChampionnatService();
	
	@Test
	public void consulterChampionnatTest() {
		List<Championnat> championnats = championnatService.rechercherChampionnat("D", "2018", "F", "200", "44");
		assertTrue(championnats.size()> 0);
	}
	
	@Test
	public void rechercherDivisionsTest() {
		List<Division> divisions = championnatService.rechercherDivisions(82133499);
		assertTrue(divisions.size()> 0);
	}
	
	@Test
	public void consulterPoulesTest() {
		List<Poule> poules = championnatService.consulterPoules(82133499, 0);
		assertTrue(poules.size()> 0);
	}

	
}
