package pl.klewek.filmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.repository.MovieDto;
import pl.klewek.filmapp.service.CloudinaryImageUploader;
import pl.klewek.filmapp.service.MovieService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private CloudinaryImageUploader cloudinaryImageUploader;
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService, CloudinaryImageUploader cloudinaryImageUploader) {
        this.movieService = movieService;
        this.cloudinaryImageUploader = cloudinaryImageUploader;
    }

    @GetMapping("/add")
    public String getNewMovie(Model model) {
        model.addAttribute("newMovie",new Movie());
        return "addMoviePage";
    }

    @PostMapping("/add-movie")
    public String addMovie(@ModelAttribute Movie movie) throws IOException {
        cloudinaryImageUploader.saveImageToCloudinary(movie.getImageUrl());
        movie.setImageUrl(cloudinaryImageUploader.getCloudinaryImageUrl());
        movieService.addMovie(movie);
        return "redirect:/movies/all";
    }

    @GetMapping("/all")
    public String getAllMovies(Model model) {
        List<Movie> movieList = movieService.getAllMovies();
        model.addAttribute("allMovies",movieList);
        return "moviesPage";
    }

    @GetMapping("/details/{id}")
    public String getMovieDetail(Model model, @PathVariable Long id) {
        Movie movieToUpdate = movieService.getMovieById(id);
        model.addAttribute("movieToUpdate", movieToUpdate);
        return "movieDetailsPage";
    }

    @RequestMapping(value = "/update/{id}", method = {RequestMethod.GET,RequestMethod.PUT})
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movieToUpdate) throws IOException {
        movieToUpdate.setId(id);
        if(movieToUpdate.getImageUrl().equals(movieService.getMovieById(id).getImageUrl())){
        }else{
            cloudinaryImageUploader.saveImageToCloudinary(movieToUpdate.getImageUrl());
            movieToUpdate.setImageUrl(cloudinaryImageUploader.getCloudinaryImageUrl());
        }
        movieService.addMovie(movieToUpdate);
        return "redirect:/movies/all";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE,RequestMethod.GET})
    public String deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return "redirect:/movies/all";
    }

    @GetMapping("/statistics")
    public String getMoviesStatistics(Model model){
        List<MovieDto> movieStatisticsList;
        movieStatisticsList = movieService.getFavouritesMoviesCount();
        List<MovieDto> userStatisticsList;
        userStatisticsList = movieService.getFavouritesMoviesCountForUsers();
        model.addAttribute("movieStatistics", movieStatisticsList);
        model.addAttribute("userStatistics", userStatisticsList);
        return "statisticsPage";
    }

}
