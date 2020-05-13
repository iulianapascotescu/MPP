import {Component} from "@angular/core";
import {MovieService} from "./shared/movie.service";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    selector: 'ubb-movies',
    templateUrl: './movies.component.html',
    styleUrls: ['./movies.component.css'],
})
export class MoviesComponent {
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewMovie() {
    console.log("add new movie btn clicked ");
    this.router.navigate(["movie/new"]);
  }
}
