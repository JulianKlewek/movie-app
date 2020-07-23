package pl.klewek.filmapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.model.UserMovie;
import pl.klewek.filmapp.repository.UserMovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMovieService {

    protected UserMovieRepository userMovieRepository;
    protected MovieService movieService;

    @Autowired
    public UserMovieService(UserMovieRepository userMovieRepository, MovieService movieService) {
        this.userMovieRepository = userMovieRepository;
        this.movieService = movieService;
    }

    public List<Movie> getFavouriteMovieList(User user){
        List<Movie> movieList = new ArrayList<>();
        List<UserMovie> userMovieList = userMovieRepository.findAllByUser(user);

        for(UserMovie userMovie : userMovieList){
            Movie movie = userMovie.getMovie();
            movieList.add(movie);
        }
        return movieList;
    }

    public void addUserMovie(User user, Movie movie){
        UserMovie userMovie = new UserMovie(user,movie);

        if(getFavouriteMovieList(user).contains(movie)){
            System.out.println("Podany film jest juz dodany do ulubionych");
        }else{
            userMovieRepository.save(userMovie);
        }
    }

    public void deleteUserMovie(User user, Movie movie){
        UserMovie userMovie = userMovieRepository.findByUserAndMovie(user,movie);
        userMovieRepository.delete(userMovie);
    }
}
