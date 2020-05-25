import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Router} from "@angular/router";
import {Movie} from "../shared/movie.model";

@Component({
  selector: 'app-movie-filter',
  templateUrl: './movie-filter.component.html',
  styleUrls: ['./movie-filter.component.css']
})
export class MovieFilterComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;

  constructor(private movieService: MovieService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.filter();
  }

  filter() {
    console.log("movie-filter.component.ts");
    this.movieService.filterMovies()
      .subscribe(
        movies => this.movies = movies.filter(movie => movie.year >= 2019).sort((movie1, movie2) => movie1.title.localeCompare(movie2.title)),
        error => this.errorMessage = <any>error
      );
    console.log("{}", this.movies);
  }

  nextFilter() {
    console.log("movie-filter.component.ts");
    this.movieService.nextFilterMovies()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
    console.log("{}", this.movies);
  }

  prevFilter() {
    console.log("movie-filter.component.ts");
    this.movieService.prevFilterMovies()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
    console.log("{}", this.movies);
  }

}
