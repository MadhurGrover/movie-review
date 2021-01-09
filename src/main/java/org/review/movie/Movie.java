package org.review.movie;

import org.review.role.Critic;
import org.review.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Entity holding movie details
 */
public class Movie {

    protected String name;
    protected Integer releaseYear;
    protected List<Genre> genres;

    /**
     * holds viewer ratings
     */
    protected Map<User, Integer> review = new HashMap<>();
    /**
     * holds critics rating.
     */
    protected Map<User, Integer> criticReview = new HashMap<>();


    public Map<User, Integer> getCriticReview() {
        return criticReview;
    }

    public Movie withCriticReview(Map<User, Integer> criticReview) {
        this.criticReview = criticReview;
        return this;
    }


    public String getName() {
        return name;
    }

    public Movie withName(String name) {
        this.name = name;
        return this;
    }

    public Map<User, Integer> getReview() {
        return review;
    }

    public Movie withReview(Map<User, Integer> review) {
        this.review = review;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Movie withReleaseDate(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Movie withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /**
     * Maintain review for the movie, based on the role of the user.
     * @param user
     * @param rating
     */
    public void addReview(User user, Integer rating) {
        if (user.getRole() instanceof Critic) {
            this.getCriticReview().putIfAbsent(user, rating * user.getRole().getWeightage());
        } else {
            this.getReview().putIfAbsent(user, rating * user.getRole().getWeightage());
        }
    }
}
