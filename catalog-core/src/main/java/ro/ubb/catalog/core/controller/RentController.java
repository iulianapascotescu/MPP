package ro.ubb.catalog.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.service.ClientServiceInterface;
import ro.ubb.catalog.core.service.MovieServiceInterface;
import ro.ubb.catalog.core.service.RentServiceInterface;
import ro.ubb.catalog.core.converter.ClientConverter;
import ro.ubb.catalog.core.converter.MovieConverter;
import ro.ubb.catalog.core.converter.RentConverter;
import ro.ubb.catalog.core.dto.MovieDto;
import ro.ubb.catalog.core.dto.NewRentDto;
import ro.ubb.catalog.core.dto.RentDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RentController {
    public static final Logger log = LoggerFactory.getLogger(RentController.class);

    @Autowired
    private RentServiceInterface rentService;

    @Autowired
    private MovieServiceInterface movieService;

    @Autowired
    private ClientServiceInterface clientService;

    @Autowired
    private RentConverter rentConverter;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/rents", method = RequestMethod.GET)
    public List<RentDto> getRents(){
        log.trace("RentController getRents - method entered");
        List<Rent> rents = rentService.getAllRents();
        log.trace("RentController line 40");
        List<RentDto> rentsDto = new ArrayList<>();//rentConverter.convertModelsToDtos(this.rentService.getAllRents()));
        for(Rent rent : rents)
            rentsDto.add(rentConverter.convertModelToDto(rent));
        log.trace("RentController getRents - method finished: rents{}", rentsDto);
        return rentsDto;
    }

    @RequestMapping(value = "/rents", method = RequestMethod.POST)
    public RentDto saveRent(@RequestBody @NotNull NewRentDto newRentDto) {
        log.trace("RentsDto saveRent: newRentDto={} - method entered", newRentDto);
        Movie movie = this.movieService.findById(newRentDto.getMovieId());
        Client client = this.clientService.findById(newRentDto.getClientId());
        Rent rent = new Rent(movie, client);
        RentDto rentDtoSaved = rentConverter.convertModelToDto(rentService.saveRent(rent));
        log.trace("RentsDto saveRent - method finished");
        return rentDtoSaved;
    }

    @RequestMapping(value = "/rents/mostRented", method = RequestMethod.GET)
    public String findMostRentedMovie() {
        log.trace("String findMostRentedMovie - method entered");
        //String result = rentService.findMostRentedMovie();
        log.trace("String findMostRentedMovie - method finished");
        return "result";
    }

}
