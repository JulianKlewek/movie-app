package pl.klewek.filmapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.klewek.filmapp.model.Movie;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.model.UserMovie;
import pl.klewek.filmapp.repository.MovieRepository;
import pl.klewek.filmapp.repository.UserMovieRepository;
import pl.klewek.filmapp.repository.UserRepository;
import pl.klewek.filmapp.service.MyUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserDetailsService myUserDetailsService;
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private UserMovieRepository userMovieRepository;

    @Autowired
    public WebSecurityConfig(MyUserDetailsService myUserDetailsService, UserRepository userRepository, MovieRepository movieRepository, UserMovieRepository userMovieRepository) {
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.userMovieRepository = userMovieRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").permitAll();
        http.authorizeRequests()
                .antMatchers("/user/movies/*").hasAnyRole("USER","ADMIN")
                .antMatchers("/movies/**").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDateBase(){
        User user1 = new User("JanUser",passwordEncoder().encode("JanUser"), "ROLE_USER");
        User user2 = new User("AdamAdmin",passwordEncoder().encode("AdamAdmin"), "ROLE_ADMIN");
        Movie movie1 = new Movie("Movie1","First movie", "http://res.cloudinary.com/digu0e7da/image/upload/v1595332606/ndi1f5hjlou6rxqlge0t.png");
        Movie movie2 = new Movie("Movie2","Second movie", "http://res.cloudinary.com/digu0e7da/image/upload/v1595332640/omlfat6nbnngo0tiotks.png");
        UserMovie userMovie = new UserMovie(user1,movie2);
        userRepository.save(user1);
        userRepository.save(user2);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        userMovieRepository.save(userMovie);

    }
}
