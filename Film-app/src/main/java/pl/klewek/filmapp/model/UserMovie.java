package pl.klewek.filmapp.model;

import javax.persistence.*;

@Entity
public class UserMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private User user;

    public UserMovie() {
    }

    public UserMovie(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

