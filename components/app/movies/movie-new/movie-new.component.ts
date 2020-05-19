import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-new.component.html',
  styleUrls: ['./movie-new.component.css']
})
export class MovieNewComponent implements OnInit {

  errorGroup: FormGroup;

  constructor(private movieService: MovieService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.errorGroup = new FormGroup({
      titleError: new FormControl('', [Validators.required]),
      genreError: new FormControl('', [Validators.required, Validators.minLength(2)]),
      yearError: new FormControl('', [Validators.min(1800), Validators.max(2020)])
    });
  }

  saveMovie(title, genre, year, id = 0) {
    console.log("saving movie", title, genre, year);
    year = +year;
    this.movieService.saveMovie({id, title, genre, year}).subscribe(_ => this.location.back());
    /*
    this.movieService.saveMovie(new FormGroup
    ({
      'id': new FormControl(id), 'title': new FormControl(title,
        [Validators.required, Validators.minLength(2)]),
      'genre': new FormControl(genre, [Validators.minLength(4)]),
      'year': new FormControl(year, [Validators.min(1800), Validators.max(2020)])
    }))
      .subscribe(_ => this.location.back());
     */
  }

}
