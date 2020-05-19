import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-rents',
  templateUrl: './rents.component.html',
  styleUrls: ['./rents.component.css']
})
export class RentsComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewRent() {
    console.log("add new rent btn clicked ");
    this.router.navigate(["rent/new"]);
  }

}
