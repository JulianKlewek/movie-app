package pl.klewek.filmapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.klewek.filmapp.model.Movie;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie,Long> {

    Movie getMovieById(Long id);

    Movie getMovieByName(String name);

    @Query(value = "select m.name as MovieName, count(um.user_id) as UserCount\n" +
            "from film_app.user_movie um, film_app.movie m\n" +
            "where um.movie_id = m.id\n" +
            "group by m.name", nativeQuery = true)
    List<MovieDto> getFavouritesMoviesCount();

    @Query(value = "select u.username as Username, count(um.movie_id) as MovieCount\n" +
            "from film_app.user u, film_app.user_movie um\n" +
            "where u.id = um.user_id\n" +
            "group by u.id",nativeQuery = true)
    List<MovieDto> getFavouritesMoviesCountForUsers();
}
