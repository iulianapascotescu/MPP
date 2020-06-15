package ro.ubb.catalog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.service.MovieServiceInterface;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.MovieDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class MovieControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieServiceInterface movieService;

    @Mock
    private MovieConverter movieConverter;

    private Movie movie1;
    private Movie movie2;
    private MovieDto movieDto1;
    private MovieDto movieDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(movieController)
                .build();
        initData();
    }

    private void initData() {
        movie1 = Movie.builder().title("title1").genre("genre").year(1999).build();
        movie1.setId(1);
        movie2 = Movie.builder().title("title2").genre("genre").year(1998).build();
        movie2.setId(2);

        movieDto1 = createMovieDto(movie1);
        movieDto2 = createMovieDto(movie2);

    }

    private MovieDto createMovieDto(Movie movie) {
        MovieDto movieDto = MovieDto.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .year(movie.getYear())
                .build();
        movieDto.setId(movie.getId());
        return movieDto;
    }


    @Test
    public void getMovies() throws Exception {
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(movieDto1);
        movieDtos.add(movieDto2);

        when(movieService.getAllMovies()).thenReturn(movies);
        when(movieConverter.convertModelsToDtos(movies)).thenReturn(movieDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", anyOf(is("title1"), is("title2"))))
                .andExpect(jsonPath("$[1].genre", anyOf(is("genre"))));
                //.andExpect(jsonPath("$[2].year", anyOf(is(1999), is(1998))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(movieService, times(1)).getAllMovies();
        verify(movieConverter, times(1)).convertModelsToDtos(movies);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    private String toJsonString(MovieDto movieDto) {
        try {
            return new ObjectMapper().writeValueAsString(movieDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createMovie() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/movies", movieDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(movieDto1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMovie() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/movies/{movie_title}", movie1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(movie1.getTitle()))
                .andExpect(status().isOk());
    }

}
