package ro.ubb.catalog.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Rent extends BaseEntity<Integer> {
    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Movie movie;

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Client client;
}
