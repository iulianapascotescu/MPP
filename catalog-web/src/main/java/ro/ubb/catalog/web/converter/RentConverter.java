package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.web.dto.RentDto;


@Component
public class RentConverter extends BaseConverter<Rent, RentDto> {
    public RentConverter() {
    }

    @Override
    public Rent convertDtoToModel(RentDto dto) {
        Movie movie = new Movie(dto.getMovie().getTitle(), dto.getMovie().getGenre(), dto.getMovie().getYear(), null);
        movie.setId(dto.getMovie().getId());
        Client client = new Client(dto.getClient().getName(), dto.getClient().getAge(), null);
        client.setId(dto.getClient().getId());
        Rent rent = Rent.builder()
                .movie(movie)
                .client(client)
                .build();
        rent.setId(dto.getId());
        return rent;
    }

    @Override
    public RentDto convertModelToDto(Rent rent) {
        Movie movie = new Movie(rent.getMovie().getTitle(), rent.getMovie().getGenre(), rent.getMovie().getYear(), null);
        movie.setId(rent.getMovie().getId());
        Client client = new Client(rent.getClient().getName(), rent.getClient().getAge(), null);
        client.setId(rent.getClient().getId());

        RentDto dto = RentDto.builder()
                .movie(movie)
                .client(client)
                .build();
        dto.setId(rent.getId());
        return dto;
    }
}
