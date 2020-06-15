package ro.ubb.catalog.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/repoMovie.xml")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void findByName(){
        Movie movie = this.movieRepository.findByTitle("title1");
        assertNotNull(movie);
        assertEquals(movie.getTitle(),"title1");
    }

    @Test
    public void findAllSimple(){
        List<Movie> movies  = this.movieRepository.findAll();
        assertEquals("there should be 2 movies", 2, movies.size());
    }

    @Test
    public void findAllWithRentsAndMovies(){
        List<Movie> movies  = this.movieRepository.findAllWithRentsAndClients();
        assertEquals("there should be 2 movies", 2, movies.size());
    }

    @Test
    public void updateClient(){
        Movie m = this.movieRepository.findByTitle("title1");
        m.setGenre("new-genre1");
        this.movieRepository.updateMovie(m);
        Movie new_movie = this.movieRepository.findByTitle("title1");
        assertNotNull(new_movie);
        assertEquals(new_movie.getTitle(),m.getTitle());
        assertEquals(new_movie.getGenre(),"new-genre1");
        assertEquals(new_movie.getYear(),m.getYear());
        assertEquals(new_movie.getId(),m.getId());
    }

    @Test
    public void deleteClient(){
        List<Movie> old_movies  = this.movieRepository.findAll();
        assertEquals("there should be 2 movies", 2, old_movies.size());
        Movie m = movieRepository.findByTitle("title1");
        assertNotNull(m);
        movieRepository.delete(m);
        List<Movie> movies  = this.movieRepository.findAll();
        assertEquals("there should be 1 movie", 1, movies.size());
    }

}