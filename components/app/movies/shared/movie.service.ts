import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Movie} from "./movie.model";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {FormGroup} from "@angular/forms";
import get = Reflect.get;

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

  update(movie: Movie): Observable<Movie> {
    const url = `${this.moviesURL}/${movie.id}`;
    return this.httpClient.put<Movie>(url, movie);
  }

  saveMovie(movie: Movie): Observable<Movie> {
    console.log("saveMovie", movie);
    //let movie = {id: form.get('id').value, title: form.get('title').value,
    //  genre: form.get('genre').value, year: form.get('year').value};
    return this.httpClient.post<Movie>(this.moviesURL, movie);
  }

  deleteMovie(id: number): Observable<any> {
    const url = `${this.moviesURL}/${id}`;
    return this.httpClient.delete(url);
  }

  sortMovies(): Observable<Movie[]> {
    const url = `${this.moviesURL}/sort`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result sortMovies {}", result);
    return result;
  }

  nextSort(): Observable<Movie[]> {
    const url = `${this.moviesURL}/nextSort`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result nextSort {}", result);
    return result;
  }

  prevSort(): Observable<Movie[]> {
    const url = `${this.moviesURL}/prevSort`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result prevSort {}", result);
    return result;
  }

  filterMovies(): Observable<Movie[]> {
    const url = `${this.moviesURL}/filter`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result filter {}", result);
    return result;
  }

  nextFilterMovies(): Observable<Movie[]> {
    const url = `${this.moviesURL}/nextFilter`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result nextFilter {}", result);
    return result;
  }

  prevFilterMovies(): Observable<Movie[]> {
    const url = `${this.moviesURL}/prevFilter`;
    const result = this.httpClient.get<Array<Movie>>(url);
    console.log("movie service server result prevFilter {}", result);
    return result;
  }
}
