package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.web.dto.RentDto;


@Component
public class RentConverter extends BaseConverter<Rent, RentDto> {
    public RentConverter() {
    }

    @Override
    public Rent convertDtoToModel(RentDto dto) {
        Rent rent = Rent.builder()
                .movie(dto.getMovie())
                .client(dto.getClient())
                .build();
        rent.setId(dto.getId());
        return rent;
    }

    @Override
    public RentDto convertModelToDto(Rent rent) {
        RentDto dto = RentDto.builder()
                .movie(rent.getMovie())
                .client(rent.getClient())
                .build();
        dto.setId(rent.getId());
        return dto;
    }
}
