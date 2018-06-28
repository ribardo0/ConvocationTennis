import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Poule } from '../beans/poule';

@Injectable({
  providedIn: 'root'
})
export class PouleService {

  private pouleUrl = 'https://convocation-tennis.appspot.com/poule';

  constructor(private http: HttpClient) { }

  consulterPoule(pouleId: string): Observable<Poule> {
    return this.http.get<Poule>(this.pouleUrl + "/" + pouleId);
  }
}
