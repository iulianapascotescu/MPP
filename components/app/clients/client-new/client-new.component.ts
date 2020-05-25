import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-new.component.html',
  styleUrls: ['./client-new.component.css']
})
export class ClientNewComponent implements OnInit {

  constructor(private clientService: ClientService, private location: Location) {
  }

  ngOnInit(): void {
  }

  saveClient(name: string, age: string) {
    console.log("saving client", name, age);
    this.clientService.saveClient({id: 0, name, age: +age}).subscribe(_ => this.location.back());
  }
}
