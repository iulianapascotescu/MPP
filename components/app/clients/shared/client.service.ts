import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Client} from "./client.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";


@Injectable()
export class ClientService {
  private clientsURL = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Client[]> {
    return this.httpClient.get<Array<Client>>(this.clientsURL);
  }

  getClient(id: number): Observable<Client> {
    return this.getClients().pipe(map(clients => clients.find(client => client.id === id)));
  }

  update(client): Observable<Client> {
    const url = `${this.clientsURL}/${client.id}`;
    return this.httpClient.put<Client>(url, client);
  }

  saveClient(client: Client): Observable<Client> {
    console.log("saveClient", client);
    return this.httpClient.post<Client>(this.clientsURL, client);
  }

  deleteClient(id: number): Observable<any> {
    const url = `${this.clientsURL}/${id}`;
    return this.httpClient.delete(url);
  }
}
