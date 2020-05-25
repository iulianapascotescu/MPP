package ro.ubb.catalog.core.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ClientDto extends BaseDto{
    @NotEmpty
    private String name;

    @Max(100)
    private int age;
}
