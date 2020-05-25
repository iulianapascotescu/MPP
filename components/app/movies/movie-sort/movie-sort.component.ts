import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {MovieService} from "../shared/movie.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-movie-sort',
  templateUrl: './movie-sort.component.html',
  styleUrls: ['./movie-sort.component.css']
})
export class MovieSortComponent implements OnInit {
  errorMessage: string;
  movies: Array<Movie>;

  constructor(private movieService: MovieService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.sortMovies();
  }

  sortMovies() {
    console.log("movie-sort.component.ts");
    this.movieService.sortMovies()
      .subscribe(
        movies => this.movies = movies.sort((a, b) => a.title.localeCompare(b.title)),
        error => this.errorMessage = <any>error
      );
    console.log("{}", this.movies);
  }

  next() {
    console.log("movie-sort.component - next()");
    this.movieService.nextSort()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
    console.log("movie-sort.component - next(), {}", this.movies);
  }

  prev() {
    console.log("movie-sort.component - prev()");
    this.movieService.prevSort()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
    console.log("movie-sort.component - prev(), {}", this.movies);
  }

}
