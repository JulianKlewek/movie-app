package pl.klewek.filmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.service.MovieService;
import pl.klewek.filmapp.service.UserMovieService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user/movies")
public class UserMovieController {

    private MovieService movieService;
    private UserMovieService userMovieService;

    @Autowired
    public UserMovieController(MovieService movieService, UserMovieService userMovieService) {
        this.movieService = movieService;
        this.userMovieService = userMovieService;
    }

    @GetMapping("/all")
    public String getAllMovies(Model model) {
        List<Movie> movieList = movieService.getAllMovies();
        model.addAttribute("allMovies",movieList);
        return "userMoviePage";
    }

    @RequestMapping(value = "/add/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addToFavourites(@PathVariable Long id, @ModelAttribute Movie movie) throws IOException {
        movie = movieService.getMovieById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMovieService.addUserMovie(user,movie);
        return "redirect:/user/movies/all";
    }

    @GetMapping("/favourites")
    public String getFavouritesMovies(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Movie> favouritesMoviesList = userMovieService.getFavouriteMovieList(user);
        model.addAttribute("favouritesMovies",favouritesMoviesList);
        return "favouritesMoviesPage";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE,RequestMethod.GET})
    public String deleteMovie(@PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movie movie = movieService.getMovieById(id);
        userMovieService.deleteUserMovie(user, movie);
        return "redirect:/user/movies/all";
    }
}
