import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Match } from './match';
import { RandomMatch } from './random-match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  matches: Match[];
  constructor(private http: HttpClient) { }

  // Getting data of played matches
  getMatches(): Observable<Match[]> {
    return this.http.get<Match[]>("http://localhost:8080/api/matches");
  }

  //Getting data for random match
  getRandomMatches(): Observable<RandomMatch[]> {
    return this.http.get<RandomMatch[]>("http://localhost:8080/api/random/match");
  }
}
