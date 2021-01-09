import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.review.Application;
import org.review.movie.Genre;
import org.review.movie.Movie;
import org.review.user.User;

import java.util.Arrays;
import java.util.Map;


public class ApplicationTest {

    Application app;

    @BeforeEach
    /**
     * Onboarding
     */
    void setup() {

        app = new Application();
        app.addMovie(new Movie().withName("Don").withReleaseDate(2006).withGenres(Arrays.asList(Genre.Action, Genre.Comedy)));
        app.addMovie(new Movie().withName("Tiger").withReleaseDate(2008).withGenres(Arrays.asList(Genre.Drama)));
        app.addMovie(new Movie().withName("Padmavat").withReleaseDate(2006).withGenres(Arrays.asList(Genre.Comedy)));
        app.addMovie(new Movie().withName("LunchBox").withReleaseDate(2021).withGenres(Arrays.asList(Genre.Drama)));
        app.addMovie(new Movie().withName("Guru").withReleaseDate(2006).withGenres(Arrays.asList(Genre.Drama)));
        app.addMovie(new Movie().withName("Metro").withReleaseDate(2006).withGenres(Arrays.asList(Genre.Romance)));

        app.addUser(new User().withUserName("SRK"));
        app.addUser(new User().withUserName("Salman"));
        app.addUser(new User().withUserName("Deepika"));

        app.addReview(app.getUser("SRK"), app.getMovie("Don"), 2);
        app.addReview(app.getUser("SRK"), app.getMovie("Padmavat"), 8);
        app.addReview(app.getUser("Salman"), app.getMovie("Don"), 5);
        app.addReview(app.getUser("Deepika"), app.getMovie("Guru"), 6);
        app.addReview(app.getUser("Deepika"), app.getMovie("Don"), 9);
    }


    @Test
    /**
     * Test Exception
     */
    void testSameUserMultipleReview() {
        UnsupportedOperationException ex = Assertions.assertThrows(UnsupportedOperationException.class, () -> app.addReview(app.getUser("SRK"), app.getMovie("Don"), 10));
        Assertions.assertEquals("Same user can't review movie twice",ex.getMessage());

    }

    @Test
    /**
     * Test Exception
     */
    void testReviewMovieYetToBeReleased() {
        UnsupportedOperationException ex = Assertions.assertThrows(UnsupportedOperationException.class, () -> app.addReview(app.getUser("Deepika"), app.getMovie("LunchBox"), 10));
        Assertions.assertEquals("Movie yet to release",ex.getMessage());
    }

    @Test
    /**
     * Test user role elevation
     */
    void testViewerElevated() {
        Assertions.assertEquals("Viewer", app.getUser("SRK").getRole().getName());
        app.addReview(app.getUser("SRK"), app.getMovie("Tiger"), 5);
        Assertions.assertEquals("Critic", app.getUser("SRK").getRole().getName());
    }

    @Test
    /**
     * Top rated  movie in a release year.
     */
    void listTopMovie() {
        app.addReview(app.getUser("SRK"), app.getMovie("Tiger"), 5);

        Map.Entry<Movie, Integer> movie = app.listTopMovie(2006, false);
        Assertions.assertEquals(movie.getKey().getName(), "Don");
        Assertions.assertEquals(movie.getValue(), 16);
    }

    @Test
    /**
     * Top Critic preferred movie in a release year.
     */
    void listTopMovieCriticsPreferred() {
        app.addReview(app.getUser("SRK"), app.getMovie("Tiger"), 5);
        app.addReview(app.getUser("SRK"), app.getMovie("Metro"), 7);

        Map.Entry<Movie, Integer> movie = app.listTopMovie(2006, true);
        Assertions.assertEquals(movie.getKey().getName(), "Metro");
        Assertions.assertEquals(movie.getValue(), 14);
    }

    @Test
    /**
     * Top movie based on Genre
     */
    void listTopMovieBasedOnGenre() {
        app.addReview(app.getUser("SRK"), app.getMovie("Tiger"), 5);
        app.addReview(app.getUser("SRK"), app.getMovie("Metro"), 7);

        Map.Entry<Movie, Integer> movie = app.listTopMovie(Genre.Drama, false);
        Assertions.assertEquals(movie.getKey().getName(), "Guru");
        Assertions.assertEquals(movie.getValue(), 6);
    }

    /**little doubtful about this scenario
     *
     * */
    public void getAverageReview() {

        app.addReview(app.getUser("SRK"), app.getMovie("Tiger"), 5);
        app.addReview(app.getUser("SRK"), app.getMovie("Metro"), 7);
        long avg = app.getAverageReview(2006);
        Assertions.assertEquals(avg, 7.33);

    }
}
