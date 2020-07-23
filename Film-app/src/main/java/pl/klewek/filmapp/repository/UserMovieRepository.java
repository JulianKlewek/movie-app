package pl.klewek.filmapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.model.UserMovie;

import java.util.List;

public interface UserMovieRepository extends JpaRepository<UserMovie,Long> {
    List<UserMovie> findAllByUser(User user);

    UserMovie findByUserAndMovie(User user, Movie movie);

    void deleteAllByMovie(Movie movie);
}
