import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MoviesComponent} from "./movies/movies.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {ClientsComponent} from "./clients/clients.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {MovieNewComponent} from "./movies/movie-new/movie-new.component";
import {ClientNewComponent} from "./clients/client-new/client-new.component";
import {MovieDeleteComponent} from "./movies/movie-delete/movie-delete.component";
import {ClientDeleteComponent} from "./clients/client-delete/client-delete.component";
import {RentsComponent} from "./rents/rents.component";
import {RentNewComponent} from "./rents/rent-new/rent-new.component";
import {MovieSortComponent} from "./movies/movie-sort/movie-sort.component";
import {MovieFilterComponent} from "./movies/movie-filter/movie-filter.component";


const routes: Routes = [
  // { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'movies', component: MoviesComponent},
  {path: 'movie/detail/:id', component: MovieDetailComponent},
  {path: 'movie/new', component: MovieNewComponent},
  {path: 'clients', component: ClientsComponent},
  {path: 'client/detail/:id', component: ClientDetailComponent},
  {path: 'client/new', component: ClientNewComponent},
  {path: 'movie/delete/:id', component: MovieDeleteComponent},
  {path: 'client/delete/:id', component: ClientDeleteComponent},
  {path: 'rents', component: RentsComponent},
  {path: 'rent/new', component: RentNewComponent},
  {path: 'movie/sort', component: MovieSortComponent},
  {path: 'movie/filter', component: MovieFilterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
