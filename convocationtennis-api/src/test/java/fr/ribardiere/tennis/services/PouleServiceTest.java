package fr.ribardiere.tennis.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fr.ribardiere.tennis.bean.Equipe;

public class PouleServiceTest {

	private PouleService pouleService = new PouleService();
	
	@Test
	public void testConsulterEquipe() {
		List<Equipe> equipes = pouleService.consulterEquipe(325487);
		assertTrue(equipes.size() > 0);
	}
	
	@Test
	public void testConsulterCalendrier() {
		List<Integer> journees = pouleService.consulterCalendrier(325487, 1448919);
		System.out.println(journees);
		assertTrue(journees.size() > 0);
	}

}
