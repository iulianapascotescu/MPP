package domain;

import javax.persistence.Entity;

@Entity
public class Rent extends BaseEntity<Integer> {
    private int movieId;
    private int clientId;

    public Rent(int movieId, int clientId) {
        this.movieId = movieId;
        this.clientId = clientId;
    }
    public Rent(int id, int movieId, int clientId) {
        this.movieId = movieId;
        this.clientId = clientId;
        this.setId(id);
    }

    public Rent() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + this.getId() +
                ", movieId=" + movieId +
                ", clientId=" + clientId +
                '}';
    }
}
