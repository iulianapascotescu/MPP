package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRents",
                attributeNodes = @NamedAttributeNode(value = "rents", subgraph = "rentWithClient"),
                subgraphs = @NamedSubgraph(name = "rentWithClient", attributeNodes = @NamedAttributeNode(value="client")))
})
@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Movie extends BaseEntity<Integer> {

    @NotEmpty(message = "Please provide a title")
    private String title;

    @Size(min=2, max=50, message = "genre size should be between 2 and 50")
    private String genre;

    @Min(1800)
    @Max(2020)
    private int year;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Rent.class, mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Rent> rents;
}
