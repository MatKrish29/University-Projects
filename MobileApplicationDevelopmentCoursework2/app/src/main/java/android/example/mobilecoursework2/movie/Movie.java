package android.example.mobilecoursework2.movie;

// POJO class for movies.
public class Movie implements Comparable<Movie>{
    private String title;
    private double rating;
    private int year;
    private String director;
    private String actors;
    private String review;

    public Movie(String title, double rating, int year) {
        this.title = title;
        this.rating = rating;
        this.year = year;
    }

    public Movie(String title, int year, String director, String actors, String review) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.actors = actors;
        this.review = review;
    }

    public Movie(String title, String director, String actors) {
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getReview() {
        return review;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                '}';
    }

    @Override
    public int compareTo(Movie movie) {
        return this.getTitle().compareTo(movie.getTitle());
    }
}
