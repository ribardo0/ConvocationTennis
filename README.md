﻿# ConvocationTennis

[![pipeline status](https://gitlab.com/ribardo0/ConvocationTennis/badges/master/pipeline.svg)](https://gitlab.com/ribardo0/ConvocationTennis/commits/master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=fr.ribardiere.tennis%3Aconvocationtennis)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=fr.ribardiere.tennis%3Aconvocationtennis&metric=coverage)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=fr.ribardiere.tennis%3Aconvocationtennis&metric=ncloc)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)

Permet de générer des convocations FFT en 3 minutes au lieu de 30

Il faut se le dire, envoyer des convocations pour des matchs de tennis par équipe est un enfer, et ce n'est pas le site de la FFT qui nous y aide. Il faut ouvrir 8 écrans différents, aggréger le tout dans un PDF infâme et à la fin, recopier et envoyer tout ça dans un mail.
Temps de cerveau utile : 20 secondes.
Temps pour préparer et envoyer une convocation : 30 minutes.

L'objectif de cet utilitaire est le suivant :
- exposer une API qui génère le PDF à partir des infos utilisateurs et des infos collectées sur le site de la FFT (http://www.gs.applipub-fft.fr/), à la cible exposée dans le cloud (Google plutôt qu'AWS car ils permettent d'avoir un minimum de 0 instances sur app engine --> nettement moins cher)
  - Les premières ressources sont accessibles sur https://convocation-tennis.appspot.com/
    - [/championnat?niveau=D&annee=2018&sexe=F&categorie=200&departement=44](https://convocation-tennis.appspot.com/championnat?niveau=D&annee=2018&sexe=F&categorie=200&departement=44) pour rechercher un championnat
	- [/championnat/82133499](https://convocation-tennis.appspot.com/championnat/82133499) (idChampionnat) pour consulter un championnnat et récupérer ses divisions
	- [/championnat/82133499/division/3](https://convocation-tennis.appspot.com/championnat/82133499/division/3) (idChampionnat et idDivision) pour consulter les poules d'une division d'un championnat
	- [/poule/325487](https://convocation-tennis.appspot.com/poule/325487) (idPoule) pour consulter une poule et ses équipes
	- [/poule/325487/equipe/1448919](https://convocation-tennis.appspot.com/poule/325487/equipe/1448919) (idPoule et idEquipe) pour consulter les journées où l'équipe reçoit à domicile
- exposer un site qui utilise l'API et permet simplement de récupérer le PDF (là encore exposé dans le cloud)
  - Le site est pour le moment uniquement disponible sur locahost via ng serve mais il utilise l'API Google
