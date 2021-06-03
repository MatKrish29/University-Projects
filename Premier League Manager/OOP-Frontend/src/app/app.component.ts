import { Component, OnInit } from '@angular/core';
import { Club } from './club';
import { ClubService } from './club.service';
import { Match } from './match';
import { MatchService } from './match.service';
import { RandomMatch } from './random-match';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  clubs: Club[];
  matches: Match[];
  randomMatches: RandomMatch[];

  constructor(private clubService: ClubService, private matchService: MatchService) { }

  //Getting data and populating the table on initialization
  ngOnInit(): void {
    (<HTMLElement> document.getElementById("clubContainer")).style.display = "block";
    (<HTMLElement> document.getElementById("matchContainer")).style.display = "none";
    (<HTMLElement> document.getElementById("clubStats")).style.display = "none";
    (<HTMLElement> document.getElementById("pointsTable")).style.backgroundColor = "blue";
    (<HTMLElement> document.getElementById("displayMatches")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("stats")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("head")).style.backgroundColor = "darkblue";
    this.clubService.getClubs().subscribe((data: Club[]) => {
      console.log(data);
      this.clubs = data;
    });
  }

  //Displaying Sorted data according to scored goals
  sortGoals(): void {
    this.clubService.getScoredGoalsSorted().subscribe((data: Club[]) => {
      console.log(data);
      this.clubs = data;
    })
  }

  //Displaying Sorted data according to wins
  sortWins(): void {
    this.clubService.getWinsSorted().subscribe((data: Club[]) => {
      console.log(data);
      this.clubs = data;
    })
  }

  //Display generated random match
  displayRandomMatches(): void {
    (<HTMLElement> document.getElementById("pointsTable")).style.backgroundColor = "blue";
    (<HTMLElement> document.getElementById("stats")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("displayMatches")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("head")).style.backgroundColor = "darkblue";
    this.matchService.getRandomMatches().subscribe((data: RandomMatch[]) => {
      console.log(data);
      this.randomMatches = data;
      this.ngOnInit();
      if (this.randomMatches.length == 0) {
        alert("No More Matches Can Be Played Between These Available Clubs!");
      }
    })
  }
  
  //Display played matches
  displayMatches(): void {
    (<HTMLElement> document.getElementById("matchContainer")).style.display = "block";
    (<HTMLElement> document.getElementById("clubContainer")).style.display = "none";
    (<HTMLElement> document.getElementById("clubStats")).style.display = "none";
    (<HTMLElement> document.getElementById("pointsTable")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("stats")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("displayMatches")).style.backgroundColor = "blue";
    (<HTMLElement> document.getElementById("head")).style.backgroundColor = "darkblue";
    this.matchService.getMatches().subscribe((data: Match[]) => {
      console.log(data);
      this.matches = data;
    })
  }
 
  //Search matches according to date and club name
  searchMatches(): void {
    var dateInput = (<HTMLInputElement> document.getElementById("input")).value;
    var clubInput = (<HTMLInputElement> document.getElementById("input")).value;
    var date = new Date(dateInput);
    var matchDate;
    var count1 = 0;
    var count2 = 0;
    for (let match of this.matches) {
      matchDate = new Date(match.date);
      if (date.toUTCString() == matchDate.toUTCString()) {
        count1 += 1;
        if (count1 == 1) {
          this.matches = [];
        }
        this.matches.push(match);
      }
      if (clubInput.toUpperCase() == match.club1 || clubInput.toUpperCase() == match.club2) {
        count2 += 1;
        if (count2 == 1) {
          this.matches = [];
        }
        this.matches.push(match);
      }
    }
  }

  //Display club stats
  displayClubStats(): void {
    (<HTMLElement> document.getElementById("matchContainer")).style.display = "none";
    (<HTMLElement> document.getElementById("clubContainer")).style.display = "none";
    (<HTMLElement> document.getElementById("clubStats")).style.display = "block";
    (<HTMLElement> document.getElementById("pointsTable")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("stats")).style.backgroundColor = "blue";
    (<HTMLElement> document.getElementById("displayMatches")).style.backgroundColor = "darkblue";
    (<HTMLElement> document.getElementById("head")).style.backgroundColor = "blue";
  }
}
