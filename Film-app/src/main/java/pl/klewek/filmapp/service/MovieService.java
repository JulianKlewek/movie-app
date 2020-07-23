package pl.klewek.filmapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.repository.MovieDto;
import pl.klewek.filmapp.repository.MovieRepository;
import pl.klewek.filmapp.repository.UserMovieRepository;

import java.util.List;

@Service
public class MovieService {


    protected MovieRepository movieRepository;
    protected UserMovieRepository userMovieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, UserMovieRepository userMovieRepository){
        this.movieRepository = movieRepository;
        this.userMovieRepository = userMovieRepository;
    }

    public void addMovie(Movie movie){
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieList = movieRepository.findAll();
        return movieList;
    }

    public Movie getMovieById(Long id){
        return movieRepository.getMovieById(id);
    }

    public Movie getMovieByName(String name){
        return  movieRepository.getMovieByName(name);
    }

    @Transactional
    public void deleteMovie(Long id){
        userMovieRepository.deleteAllByMovie(getMovieById(id));
        movieRepository.deleteById(id);
    }

    public List<MovieDto> getFavouritesMoviesCount(){
        return movieRepository.getFavouritesMoviesCount();
    }

    public List<MovieDto> getFavouritesMoviesCountForUsers(){
        return movieRepository.getFavouritesMoviesCountForUsers();
    }

}
