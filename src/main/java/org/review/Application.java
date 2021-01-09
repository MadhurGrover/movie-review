package org.review;

import org.review.movie.Genre;
import org.review.movie.Movie;
import org.review.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains all the required service methods.
 */
public class Application {

    Set<Movie> movies = new HashSet<Movie>();
    Set<User> users = new HashSet<User>();

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Service to add reviews.
     *
     * @param user
     * @param movie
     * @param rating Throws exception if same user review movie twice
     *               or
     *               movie is not yet released
     */
    public void addReview(User user, Movie movie, Integer rating) {
        if (movie.getReview().containsKey(user))
            throw new UnsupportedOperationException("Same user can't review movie twice");
        if (movie.getReleaseYear() > 2020)
            throw new UnsupportedOperationException("Movie yet to release");
        user.incNumOfReviews();
        movie.addReview(user, rating);
    }


    /**
     * list top movie based on rating in the given release year
     *
     * @param releaseYear
     * @param criticsPreferred: if critic ratings to be used or not
     * @return
     */
    public Map.Entry<Movie, Integer> listTopMovie(Integer releaseYear, boolean criticsPreferred) {

        Set<Movie> subSet = movies.stream().filter(movie -> (movie.getReleaseYear().equals(releaseYear))).collect(Collectors.toSet());
        return getTopMovie(criticsPreferred, subSet);

    }

    private Map.Entry<Movie, Integer> getTopMovie(boolean criticsPreferred, Set<Movie> subSet) {
        Map<Movie, Integer> movieReview = new HashMap<>();

        for (Movie movie : subSet) {
            if (criticsPreferred) {
                for (User user : movie.getCriticReview().keySet()) {
                    movieReview.computeIfPresent(movie, (key, val) ->
                            val + movie.getCriticReview().get(user)
                    );
                    movieReview.putIfAbsent(movie, movie.getCriticReview().get(user));
                }
            } else {
                for (User user : movie.getReview().keySet()) {
                    movieReview.computeIfPresent(movie, (key, val) ->
                            val + movie.getReview().get(user));
                    movieReview.putIfAbsent(movie, movie.getReview().get(user));

                }
            }
        }
        return movieReview.entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }

    /**
     * list top movie based on rating in the given genre
     *
     * @param genre
     * @param criticsPreferred: if critic ratings to be used or not
     * @return
     */
    public Map.Entry<Movie, Integer> listTopMovie(Genre genre, boolean criticsPreferred) {
        Set<Movie> subSet = movies.stream().filter(movie -> (movie.getGenres().contains(genre))).collect(Collectors.toSet());

        return getTopMovie(criticsPreferred, subSet);

    }


    public Movie getMovie(String movie) {
        return movies.stream().filter(mo -> mo.getName().equals(movie)).findFirst().get();
    }

    public User getUser(String user) {
        return users.stream().filter(us -> us.getUserName().equals(user)).findFirst().get();
    }


    /**
     * Give average reviews in an year
     *
     * @param releaseYear
     * @return
     */

    public long getAverageReview(int releaseYear) {

        Set<Movie> subSet = movies.stream().filter(movie -> (movie.getReleaseYear().equals(releaseYear))).collect(Collectors.toSet());

        List<Integer> reviewList = new ArrayList<>();

        subSet.stream().map(movie -> {
            reviewList.addAll(movie.getReview().values());
            reviewList.addAll(movie.getCriticReview().values());
            return reviewList;
        });

        return reviewList.stream()
                .reduce(0, (a, b) -> a + b) / reviewList.stream().count();

    }
}
