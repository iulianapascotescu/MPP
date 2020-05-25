package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class NewRentDto extends BaseDto{
    private String movieTitle;
    private String clientName;
}
