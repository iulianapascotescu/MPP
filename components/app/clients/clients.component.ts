import {Component, OnInit} from '@angular/core';
import {ClientService} from "./shared/client.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  addNewClient() {
    console.log("add new client btn clicked ");
    this.router.navigate(["client/new"]);
  }

}
