package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.RentDto;

@Component
public class RentConverter extends BaseConverter<Rent, RentDto> {
    public RentConverter() {
    }

    @Override
    public Rent convertDtoToModel(RentDto dto) {
        Rent rent = Rent.builder()
                .movieId(dto.getMovieId())
                .clientId(dto.getClientId())
                .build();
        rent.setId(dto.getId());
        return rent;
    }

    @Override
    public RentDto convertModelToDto(Rent rent) {
        RentDto dto = RentDto.builder()
                .movieId(rent.getMovieId())
                .clientId(rent.getClientId())
                .build();
        dto.setId(rent.getId());
        return dto;
    }
}
