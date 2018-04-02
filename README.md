# ConvocationTennis

[![pipeline status](https://gitlab.com/ribardo0/ConvocationTennis/badges/master/pipeline.svg)](https://gitlab.com/ribardo0/ConvocationTennis/commits/master)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=fr.ribardiere.tennis%3Aconvocationtennis)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=fr.ribardiere.tennis%3Aconvocationtennis&metric=coverage)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=fr.ribardiere.tennis%3Aconvocationtennis&metric=nloc)](https://sonarcloud.io/dashboard/index/fr.ribardiere.tennis%3Aconvocationtennis)

Permet de générer des convocations FFT en 3 minutes au lieu de 30

Il faut se le dire, envoyer des convocations pour des matchs de tennis par équipe est un enfer, et ce n'est pas le site de la FFT qui nous y aide. Il faut ouvrir 8 écrans différents, aggréger le tout dans un PDF infâme et à la fin, recopier et envoyer tout ça dans un mail.
Temps de cerveau utile : 20 secondes.
Temps pour préparer et envoyer une convocation : 30 minutes.

L'objectif de cet utilitaire est le suivant :
- exposer une API qui génère le PDF à partir des infos utilisateurs et des infos collectées sur le site de la FFT (http://www.gs.applipub-fft.fr/), à la cible exposée dans le cloud (AWS ou Google, à décider)
- exposer un site qui utilise l'API et permet simplement de récupérer le PDF (là encore exposé dans le cloud).
