import {Component, Input, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import {Movie} from "../shared/movie.model";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-movie-delete',
  templateUrl: './movie-delete.component.html',
  styleUrls: ['./movie-delete.component.css']
})
export class MovieDeleteComponent implements OnInit {

  @Input() movie: Movie;

  constructor(private movieService: MovieService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.movieService.getMovie(+params['id'])))
      .subscribe(movie => this.movie = movie);
  }

  no(): void {
    this.location.back();
  }

  yes(): void {
    this.movieService.deleteMovie(this.movie.id).subscribe(_ => this.location.back());
  }
}
