package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Rent extends BaseEntity<Integer> {
    @NotNull
    @Valid
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @NotNull
    @Valid
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "client_id")
    private Client client;
}
