package fr.ribardiere.tennis.bean;

import java.util.Date;

public class Convocation {
	
	private Date dateEnvoi;
	private boolean envoiDixJoursAvantRencontre;
	private boolean reportUnMoisAvantRencontre;
	private boolean seniorPlus35Dames;
	private boolean seniorPlus35Messieurs;
	private boolean seniorPlus45Messieurs;
	private boolean seniorPlus55Messieurs;
	private boolean seniorPlus65Messieurs;
	private boolean seniorPlus75Messieurs;
	private boolean seniorHiverDames;
	private boolean seniorHiverMessieurs;
	private boolean coupeHiverDames;
	private boolean seniorEteDames;
	private boolean seniorEteMessieurs;
	private boolean seniorEteMixte;
	private boolean seniorPlusEte35Dames;
	private boolean seniorPlusEte35Messieurs;
	private boolean seniorPlusEte45Messieurs;
	private boolean seniorPlusEte55Messieurs;
	private boolean seniorPlusEte65Messieurs;
	private String division;
	private String poule;
	private int journee;
	private Date dateRencontre;
	private Club clubVisite;
	private Club clubVisiteur;
	private Date dateReelle;
	private boolean samedi;
	private boolean dimanche;
	private String heure;
	private int nombreTerrains;
	private String lieu;
	private String motifReport;
	
	public Convocation() {
        this.seniorHiverDames = false;
        this.coupeHiverDames = false;
        this.seniorEteDames = false;
	    this.seniorPlus35Dames = false;
        this.seniorPlusEte35Dames = false;
        this.seniorHiverMessieurs = false;
	    this.seniorPlus35Messieurs = false;
	    this.seniorPlus45Messieurs = false;
	    this.seniorPlus55Messieurs = false;
	    this.seniorPlus65Messieurs = false;
	    this.seniorPlus75Messieurs = false;
	    this.seniorEteMessieurs = false;
	    this.seniorPlusEte35Messieurs = false;
	    this.seniorPlusEte45Messieurs = false;
	    this.seniorPlusEte55Messieurs = false;
	    this.seniorPlusEte65Messieurs = false;
        this.seniorEteMixte = false;
	}
	
	public Date getDateEnvoi() {
		return dateEnvoi;
	}
	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}
	public boolean isEnvoiDixJoursAvantRencontre() {
		return envoiDixJoursAvantRencontre;
	}
	public void setEnvoiDixJoursAvantRencontre(boolean envoiDixJoursAvantRencontre) {
		this.envoiDixJoursAvantRencontre = envoiDixJoursAvantRencontre;
	}
	public boolean isReportUnMoisAvantRencontre() {
		return reportUnMoisAvantRencontre;
	}
	public void setReportUnMoisAvantRencontre(boolean reportUnMoisAvantRencontre) {
		this.reportUnMoisAvantRencontre = reportUnMoisAvantRencontre;
	}
	public boolean isSeniorPlus35Dames() {
		return seniorPlus35Dames;
	}
	public void setSeniorPlus35Dames(boolean seniorPlus35Dames) {
		this.seniorPlus35Dames = seniorPlus35Dames;
	}
	public boolean isSeniorPlus35Messieurs() {
		return seniorPlus35Messieurs;
	}
	public void setSeniorPlus35Messieurs(boolean seniorPlus35Messieurs) {
		this.seniorPlus35Messieurs = seniorPlus35Messieurs;
	}
	public boolean isSeniorPlus45Messieurs() {
		return seniorPlus45Messieurs;
	}
	public void setSeniorPlus45Messieurs(boolean seniorPlus45Messieurs) {
		this.seniorPlus45Messieurs = seniorPlus45Messieurs;
	}
	public boolean isSeniorPlus55Messieurs() {
		return seniorPlus55Messieurs;
	}
	public void setSeniorPlus55Messieurs(boolean seniorPlus55Messieurs) {
		this.seniorPlus55Messieurs = seniorPlus55Messieurs;
	}
	public boolean isSeniorPlus65Messieurs() {
		return seniorPlus65Messieurs;
	}
	public void setSeniorPlus65Messieurs(boolean seniorPlus65Messieurs) {
		this.seniorPlus65Messieurs = seniorPlus65Messieurs;
	}
	public boolean isSeniorPlus75Messieurs() {
		return seniorPlus75Messieurs;
	}
	public void setSeniorPlus75Messieurs(boolean seniorPlus75Messieurs) {
		this.seniorPlus75Messieurs = seniorPlus75Messieurs;
	}
	public boolean isSeniorHiverDames() {
		return seniorHiverDames;
	}
	public void setSeniorHiverDames(boolean seniorHiverDames) {
		this.seniorHiverDames = seniorHiverDames;
	}
	public boolean isSeniorHiverMessieurs() {
		return seniorHiverMessieurs;
	}
	public void setSeniorHiverMessieurs(boolean seniorHiverMessieurs) {
		this.seniorHiverMessieurs = seniorHiverMessieurs;
	}
	public boolean isCoupeHiverDames() {
		return coupeHiverDames;
	}
	public void setCoupeHiverDames(boolean coupeHiverDames) {
		this.coupeHiverDames = coupeHiverDames;
	}
	public boolean isSeniorEteDames() {
		return seniorEteDames;
	}
	public void setSeniorEteDames(boolean seniorEteDames) {
		this.seniorEteDames = seniorEteDames;
	}
	public boolean isSeniorEteMessieurs() {
		return seniorEteMessieurs;
	}
	public void setSeniorEteMessieurs(boolean seniorEteMessieurs) {
		this.seniorEteMessieurs = seniorEteMessieurs;
	}
	public boolean isSeniorEteMixte() {
		return seniorEteMixte;
	}
	public void setSeniorEteMixte(boolean seniorEteMixte) {
		this.seniorEteMixte = seniorEteMixte;
	}
	public boolean isSeniorPlusEte35Dames() {
		return seniorPlusEte35Dames;
	}
	public void setSeniorPlusEte35Dames(boolean seniorPlusEte35Dames) {
		this.seniorPlusEte35Dames = seniorPlusEte35Dames;
	}
	public boolean isSeniorPlusEte35Messieurs() {
		return seniorPlusEte35Messieurs;
	}
	public void setSeniorPlusEte35Messieurs(boolean seniorPlusEte35Messieurs) {
		this.seniorPlusEte35Messieurs = seniorPlusEte35Messieurs;
	}
	public boolean isSeniorPlusEte45Messieurs() {
		return seniorPlusEte45Messieurs;
	}
	public void setSeniorPlusEte45Messieurs(boolean seniorPlusEte45Messieurs) {
		this.seniorPlusEte45Messieurs = seniorPlusEte45Messieurs;
	}
	public boolean isSeniorPlusEte55Messieurs() {
		return seniorPlusEte55Messieurs;
	}
	public void setSeniorPlusEte55Messieurs(boolean seniorPlusEte55Messieurs) {
		this.seniorPlusEte55Messieurs = seniorPlusEte55Messieurs;
	}
	public boolean isSeniorPlusEte65Messieurs() {
		return seniorPlusEte65Messieurs;
	}
	public void setSeniorPlusEte65Messieurs(boolean seniorPlusEte65Messieurs) {
		this.seniorPlusEte65Messieurs = seniorPlusEte65Messieurs;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPoule() {
		return poule;
	}
	public void setPoule(String poule) {
		this.poule = poule;
	}
	public int getJournee() {
		return journee;
	}
	public void setJournee(int journee) {
		this.journee = journee;
	}
	public Date getDateRencontre() {
		return dateRencontre;
	}
	public void setDateRencontre(Date dateRencontre) {
		this.dateRencontre = dateRencontre;
	}
	public Club getClubVisite() {
		return clubVisite;
	}
	public void setClubVisite(Club clubVisite) {
		this.clubVisite = clubVisite;
	}
	public Club getClubVisiteur() {
		return clubVisiteur;
	}
	public void setClubVisiteur(Club clubVisiteur) {
		this.clubVisiteur = clubVisiteur;
	}
	public Date getDateReelle() {
		return dateReelle;
	}
	public void setDateReelle(Date dateReelle) {
		this.dateReelle = dateReelle;
	}
	public boolean isSamedi() {
		return samedi;
	}
	public void setSamedi(boolean samedi) {
		this.samedi = samedi;
	}
	public boolean isDimanche() {
		return dimanche;
	}
	public void setDimanche(boolean dimanche) {
		this.dimanche = dimanche;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}
	public int getNombreTerrains() {
		return nombreTerrains;
	}
	public void setNombreTerrains(int nombreTerrains) {
		this.nombreTerrains = nombreTerrains;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getMotifReport() {
		return motifReport;
	}
	public void setMotifReport(String motifReport) {
		this.motifReport = motifReport;
	}
}
