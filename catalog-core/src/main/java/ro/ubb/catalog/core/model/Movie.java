package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRents",
                attributeNodes = @NamedAttributeNode(value = "rent")),

        @NamedEntityGraph(name = "movieWithRentsAndClients",
                attributeNodes = @NamedAttributeNode(value = "rent",
                        subgraph = "rentWithClients"),
                subgraphs = @NamedSubgraph(name = "rentWithClients",
                        attributeNodes = @NamedAttributeNode(value =
                                "client")))
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "movie")
    @JsonManagedReference
    private List<Rent> rents = new ArrayList<>();
}
