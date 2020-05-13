import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Movie} from "./movie.model";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable()
export class MovieService {
  private moviesURL = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.httpClient.get<Array<Movie>>(this.moviesURL);
  }

  getMovie(id: number): Observable<Movie> {
    return this.getMovies().pipe(map(movies => movies.find(movie => movie.id === id)));
  }

  update(movie): Observable<Movie> {
    const url = `${this.moviesURL}/${movie.id}`;
    return this.httpClient.put<Movie>(url, movie);
  }

  saveMovie(movie: Movie): Observable<Movie> {
    console.log("saveMovie", movie);
    return this.httpClient.post<Movie>(this.moviesURL, movie);
  }

  deleteMovie(id: number): Observable<any> {
    const url = `${this.moviesURL}/${id}`;
    return this.httpClient.delete(url);
  }
}
