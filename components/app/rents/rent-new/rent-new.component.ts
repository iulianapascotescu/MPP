import { Component, OnInit } from '@angular/core';
import {RentService} from "../shared/rent.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-rent-new',
  templateUrl: './rent-new.component.html',
  styleUrls: ['./rent-new.component.css']
})
export class RentNewComponent implements OnInit {

  constructor(private rentService: RentService, private location: Location) { }

  ngOnInit(): void {
  }

  saveRent(movieTitle, clientName){
    console.log("saving rent", {movieTitle, clientName});
    this.rentService.saveRent({movieTitle: movieTitle, clientName: clientName}).subscribe(_ => this.location.back());
  }

  back(){
    this.location.back();
  }

}
