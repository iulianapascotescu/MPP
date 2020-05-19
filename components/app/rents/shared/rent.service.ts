import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {NewRent, Rent} from "./rent.model";

@Injectable()
export class RentService {
  private rentsURL = 'http://localhost:8080/api/rents';

  constructor(private httpClient: HttpClient) {
  }

  getRents(): Observable<Rent[]> {
    return this.httpClient.get<Array<Rent>>(this.rentsURL);
  }

  saveRent(rent: NewRent): Observable<Rent> {
    console.log("saveRent");
    return this.httpClient.post<Rent>(this.rentsURL, rent);
  }
}
