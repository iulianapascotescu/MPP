import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {MoviesComponent} from "./movies/movies.component";
import {MovieListComponent} from "./movies/movie-list/movie-list.component";
import {MovieService} from "./movies/shared/movie.service";
import { ClientsComponent } from './clients/clients.component';
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import {MovieNewComponent} from "./movies/movie-new/movie-new.component";
import { ClientNewComponent } from './clients/client-new/client-new.component';
import { MovieDeleteComponent } from './movies/movie-delete/movie-delete.component';
import { ClientDeleteComponent } from './clients/client-delete/client-delete.component';


@NgModule({
  declarations: [
    AppComponent,
    MovieDetailComponent,
    MoviesComponent,
    MovieListComponent,
    MovieNewComponent,
    ClientsComponent,
    ClientDetailComponent,
    ClientListComponent,
    ClientNewComponent,
    MovieDeleteComponent,
    ClientDeleteComponent,
  ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [MovieService, ClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
