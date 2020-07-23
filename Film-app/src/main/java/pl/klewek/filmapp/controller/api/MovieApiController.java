package pl.klewek.filmapp.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieApiController {

    private MovieService movieService;

    @Autowired
    public MovieApiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public Iterable<Movie> getAll() {
        return movieService.getAllMovies();
    }

    @GetMapping("/getById/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/getByName/{name}")
    public Movie getMovieByName(@PathVariable String name){
        return movieService.getMovieByName(name);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
