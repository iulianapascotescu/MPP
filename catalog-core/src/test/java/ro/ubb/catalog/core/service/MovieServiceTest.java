package ro.ubb.catalog.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class MovieServiceTest {

    @Autowired
    private MovieServiceInterface movieService;

    @Test
    public void findAll() throws Exception {
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be one movie", 2, movies.size());
    }

    @Test
    public void getMovieByTitle() {
        Movie m = movieService.getMovieByTitle("title1");
        assertNotNull(m);
        assertEquals("title1", m.getTitle());
    }


    @Test
    public void updateMovie() {
        Movie c = new Movie("title2", "genre3", 2000, new ArrayList<>());
        c.setId(2);
        movieService.updateMovie(c);
        Movie new_movie = movieService.getMovieByTitle("title2");
        assertNotNull(new_movie);
        assertEquals(c.getYear(), new_movie.getYear());
        assertEquals(c.getGenre(), new_movie.getGenre());
        assertEquals(c.getTitle(), new_movie.getTitle());

        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be four students", 2, movies.size());
    }

    @Test
    public void deleteMovie() {
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be 2 movies", 2, movies.size());

        movieService.deleteMovie(1);

        List<Movie> movies1 = movieService.getAllMovies();
        assertEquals("there should be 1 movie", 1, movies1.size());
    }
}
