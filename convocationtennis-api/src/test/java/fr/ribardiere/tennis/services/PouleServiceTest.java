package fr.ribardiere.tennis.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import fr.ribardiere.tennis.bean.Poule;

public class PouleServiceTest {

	private PouleService pouleService = new PouleService();
	
	@Test
	public void testConsulterPoule() {
		Poule poule = pouleService.consulterPoule(325487);
		assertTrue(poule.getEquipes().size() > 0);
	}
	
	@Test
	public void testConsulterCalendrier() {
		List<Integer> journees = pouleService.consulterCalendrier(325487, 1448919);
		System.out.println(journees);
		assertTrue(journees.size() > 0);
	}

}
