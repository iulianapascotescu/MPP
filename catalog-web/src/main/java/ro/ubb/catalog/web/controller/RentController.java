package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.service.RentServiceInterface;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.converter.RentConverter;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.RentDto;
import ro.ubb.catalog.web.dto.RentsDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RentController {
    public static final Logger log = LoggerFactory.getLogger(RentController.class);

    @Autowired
    private RentServiceInterface rentService;

    @Autowired
    private RentConverter rentConverter;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/rents", method = RequestMethod.GET)
    public List<RentDto> getRents(){
        log.trace("RentsDto getRents - method entered");
        List<RentDto> rentsDto = new ArrayList<>(rentConverter
                .convertModelsToDtos(rentService.getAllRents()));
        log.trace("RentsDto getRents - method finished");
        return rentsDto;
    }

    @RequestMapping(value = "/rents", method = RequestMethod.POST)
    public RentDto saveRent(@RequestBody RentDto rentDto) {
        log.trace("RentsDto saveRent: rentDto={} - method entered", rentDto);
        RentDto rentDtoSaved = rentConverter.convertModelToDto(rentService.saveRent(
                rentConverter.convertDtoToModel(rentDto)));
        log.trace("RentsDto saveRent - method finished");
        return rentDtoSaved;
    }

    @RequestMapping(value = "/rents/mostRented", method = RequestMethod.GET)
    public String findMostRentedMovie() {
        log.trace("String findMostRentedMovie - method entered");
        String result = rentService.findMostRentedMovie();
        log.trace("String findMostRentedMovie - method finished");
        return result;
    }

    @RequestMapping(value = "/rents/filter", method = RequestMethod.GET)
    public ArrayList<String> filterMoviesIfRented(){
        log.trace("HashSet<String> filterMoviesIfRented - method entered");
        ArrayList<String> result = new ArrayList<>(rentService.filterMoviesIfRented());
        log.trace("HashSet<String> filterMoviesIfRented - method finished");
        return result;
    }
}
