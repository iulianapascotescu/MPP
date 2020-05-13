import { Component, OnInit } from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-new.component.html',
  styleUrls: ['./movie-new.component.css']
})
export class MovieNewComponent implements OnInit {

  constructor(private movieService: MovieService,
              private location: Location) { }

  ngOnInit(): void {
  }

  saveMovie(title: string, genre: string, year: string) {
    console.log("saving movie", title, genre, year);
    this.movieService.saveMovie({ id: 0, title, genre, year: +year}).subscribe(_ => this.location.back());
  }

}
