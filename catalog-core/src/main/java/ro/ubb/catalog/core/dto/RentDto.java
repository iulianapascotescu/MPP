package ro.ubb.catalog.core.dto;

import lombok.*;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class RentDto extends BaseDto{
    @NotNull
    @Valid
    private Movie movie;

    @Valid
    @NotNull
    private Client client;
}
