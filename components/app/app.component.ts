import { Component } from '@angular/core';
import {AppService} from "./shared/service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MovieProject';
  public isLoggedIn = false;

  constructor(public service: AppService, private router: Router) { }

  ngOnInit() {
    if(localStorage.getItem("username")!=null && localStorage.getItem("username")!="") {
      this.service.loggedIn = true;
      if (localStorage.getItem("role") == "ADMIN")
        this.service.admin = true;
    }
  }

  login(username: string, password: string) {
    this.service.login(username, password);
  }

  logout() {
    this.service.logout();
  }
}
