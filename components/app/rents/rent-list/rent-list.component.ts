import {Component, OnInit} from '@angular/core';
import {Client} from "../../clients/shared/client.model";
import {Rent} from "../shared/rent.model";
import {ClientService} from "../../clients/shared/client.service";
import {Router} from "@angular/router";
import {RentService} from "../shared/rent.service";

@Component({
  moduleId: module.id,
  selector: 'app-rent-list',
  templateUrl: './rent-list.component.html',
  styleUrls: ['./rent-list.component.css']
})
export class RentListComponent implements OnInit {
  errorMessage: string;
  rents: Array<Rent>;

  constructor(private rentService: RentService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getRents();
  }

  getRents() {
    this.rentService.getRents()
      .subscribe(
        rents => this.rents = rents,
        error => this.errorMessage = <any>error
      );
  }

}
