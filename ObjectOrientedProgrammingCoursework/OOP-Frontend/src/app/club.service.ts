import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Club } from './club';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  clubs: Club[];
  constructor(private http: HttpClient) { }

  //Getting club stats
  getClubs(): Observable<Club[]> {
    return this.http.get<Club[]>("http://localhost:8080/api/clubs")
  }

  //Getting sorted club stats according to scored goals
  getScoredGoalsSorted(): Observable<Club[]> {
    return this.http.get<Club[]>("http://localhost:8080/api/clubs/goals/sorted");
  }

  //Getting sorted club stats according to wins
  getWinsSorted(): Observable<Club[]> {
    return this.http.get<Club[]>("http://localhost:8080/api/clubs/wins/sorted");
  }  
}
