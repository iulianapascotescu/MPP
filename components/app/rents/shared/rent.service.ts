import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from "@angular/common/http";

import {Observable} from "rxjs";
import {NewRent, Rent} from "./rent.model";

@Injectable()
export class RentService {
  private rentsURL = 'http://localhost:8080/api/rents';

  constructor(private httpClient: HttpClient) {
  }

  getRents(): Observable<Rent[]> {
    let headers = new HttpHeaders({
      Authorization: "Basic " +
        btoa(localStorage.getItem("username") + ":" +
          localStorage.getItem("password")) });
    headers.append("Content-Type", "application/json");
    headers.append("Cache-Control", "no-cache");
    return this.httpClient.get<Array<Rent>>(this.rentsURL,{headers});
  }

  saveRent(rent: NewRent): Observable<Rent> {
    console.log("saveRent");
    let headers = new HttpHeaders({
      Authorization: "Basic " +
        btoa(localStorage.getItem("username") + ":" +
          localStorage.getItem("password")) });
    headers.append("Content-Type", "application/json");
    headers.append("Cache-Control", "no-cache");
    return this.httpClient.post<Rent>(this.rentsURL, rent,{headers});
  }
}
