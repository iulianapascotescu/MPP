import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserCredentials} from "./model";

@Injectable()
export class AppService {

  constructor(private httpClient: HttpClient, private router: Router) {
    this.loggedIn = false;
    this.admin=false;
  }
  loggedIn:boolean;
  admin:boolean;

  login(username, password): void{
    localStorage.setItem("username", "");
    this.httpClient.post<UserCredentials>("http://localhost:8080/api/login", {username: username,password: password})
      .subscribe(user => {
        if(user.username!="")
      {localStorage.setItem("username", user.username);
       localStorage.setItem("password", user.password);
       localStorage.setItem("role", user.role);
       if(user.role=="ADMIN")
         this.admin=true;
       this.loggedIn= true;
      }});
  }

  logout(): void{
    localStorage.setItem("username", "");
    localStorage.setItem("role", "");
    localStorage.clear();
    this.admin=false;
    this.loggedIn=false;
  }

  checkCredentials(): boolean{
    return false;
  }

}
