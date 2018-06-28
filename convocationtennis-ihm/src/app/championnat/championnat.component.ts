import { Component, OnInit } from '@angular/core';

import { Championnat } from '../beans/championnat';
import { Division } from '../beans/division';
import { Poule } from '../beans/poule';
import { Equipe } from '../beans/equipe';

import { ChampionnatService } from '../services/championnat.service';
import { PouleService } from '../services/poule.service';

@Component({
  selector: 'app-championnat',
  templateUrl: './championnat.component.html',
  styleUrls: ['./championnat.component.css']
})
export class ChampionnatComponent implements OnInit {

  niveau: string;
  annee: string;
  sexe: string;
  categorie: string;
  departement: string;
  championnatId: string;
  divisionId: string;
  pouleId: string;
  equipeId: string;

  championnats: Championnat[];
  divisions: Division[];
  poules: Poule[];
  equipes: Equipe[];

  constructor(private championnatService: ChampionnatService, private pouleService: PouleService) { }

  ngOnInit() {
  }

  rechercherChampionnats() {
    if ((this.niveau != null) && (this.annee != null) && (this.sexe != null) && (this.categorie != null) && (this.departement != null)) {
      this.divisions = null; // Dans le cas où on change le moindre paramètre
      this.divisionId = null; // Idem
      this.poules = null; // Idem
      this.pouleId = null; // Idem
      this.equipes = null;
      this.championnatService.rechercherChampionnats(this.niveau, this.annee, this.sexe, this.categorie, this.departement).subscribe(championnats => this.championnats = championnats);
    }
  }

  rechercherDivisions() {
    this.poules = null; // Dans le cas où on change de championnat
    this.pouleId = null; // Idem
    this.equipes = null; // Idem
    this.championnatService.rechercherDivisions(this.championnatId).subscribe(divisions => this.divisions = divisions);;
  }

  consulterPoules() {
    this.equipes = null; // Dans le cas où on change de division
    this.championnatService.consulterPoules(this.championnatId, this.divisionId).subscribe(poules => this.poules = poules);
  }

  consulterPoule() {
    this.pouleService.consulterPoule(this.pouleId).subscribe(poule => this.equipes = poule.equipes);
  }
}
