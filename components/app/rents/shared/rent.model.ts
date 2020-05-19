import {Movie} from "../../movies/shared/movie.model";
import {Client} from "../../clients/shared/client.model";

export class Rent {
  id: number;
  movie: Movie;
  client: Client;
}

export class NewRent {
  movieId: number;
  clientId: number;
}
