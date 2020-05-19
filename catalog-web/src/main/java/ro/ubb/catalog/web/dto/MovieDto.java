package ro.ubb.catalog.web.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieDto extends BaseDto {

    @NotEmpty
    private String title;

    @Size(min=2, max=50)
    private String genre;

    @Min(1800)
    @Max(2020)
    private int year;
}
