import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Championnat } from '../beans/championnat';
import { Division } from '../beans/division';
import { Poule } from '../beans/poule';

@Injectable({
  providedIn: 'root'
})
export class ChampionnatService {

  private championnatUrl = 'https://convocation-tennis.appspot.com/championnat';

  constructor(private http: HttpClient) { }

  rechercherChampionnats(niveau: string, annee: string, sexe: string, categorie: string, departement: string): Observable<Championnat[]> {
    var queryParams: string = "?niveau=" + niveau + "&annee=" + annee + "&sexe=" + sexe + "&categorie=" + categorie + "&departement=" + departement;
    //var championnats: Championnat[] = [{ id: 1, nom: "Champ1" }, { id: 2, nom: "Champ2" }];
    return this.http.get<Championnat[]>(this.championnatUrl + queryParams);
  }

  rechercherDivisions(championnatId: string): Observable<Division[]> {
    return this.http.get<Division[]>(this.championnatUrl + "/" + championnatId);
  }

  consulterPoules(championnatId: string, divisionId: string): Observable<Poule[]> {
    return this.http.get<Poule[]>(this.championnatUrl + "/" + championnatId + "/division/" + divisionId);
  }
}
