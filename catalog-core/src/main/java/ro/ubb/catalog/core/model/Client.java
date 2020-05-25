package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraphs({
        @NamedEntityGraph(name = "clientWithRents",
                attributeNodes = @NamedAttributeNode(value = "rent")),

        @NamedEntityGraph(name = "clientWithRentsAndMovies",
                attributeNodes = @NamedAttributeNode(value = "rent",
                        subgraph = "rentWithMovies"),
                subgraphs = @NamedSubgraph(name = "rentWithMovies",
                        attributeNodes = @NamedAttributeNode(value =
                                "movie")))
})

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Client extends BaseEntity<Integer> {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Min(10)
    @Max(100)
    private int age;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client")
    @JsonManagedReference
    private List<Rent> rents = new ArrayList<>();
}
