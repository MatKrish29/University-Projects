package android.example.mobilecoursework2.asynctask;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.activity.ViewMoviesActivity;
import android.example.mobilecoursework2.database.MovieEventsData;
import android.example.mobilecoursework2.movie.Movie;
import android.example.mobilecoursework2.movie.MovieAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.example.mobilecoursework2.database.MovieConstants.FAVOURITE;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_RATING;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_YEAR;
import static android.example.mobilecoursework2.database.MovieConstants.TABLE_NAME;

public class FetchDatabaseData extends android.os.AsyncTask<Void, Void, List<Movie>>{

    private final List<Movie> moviesList = new ArrayList<>();
    public static List<Movie> favouritesToAddImage = new ArrayList<>();
    private final String activity;
    private Context context;
    private final String[] COLUMNS = {MOVIE_NAME, FAVOURITE, MOVIE_RATING, MOVIE_YEAR};
    private WeakReference<ListView> listView;

    public FetchDatabaseData(ListView listView, Context context, String activity) {
        this.listView = new WeakReference<>(listView);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        if (activity.equals("favourite")) {
            return getMovies(true);
        }
        else {
            return getMovies(false);
        }
    }

    private List<Movie> getMovies(boolean favourites) {
        favouritesToAddImage.clear();
        MovieEventsData eventsData = new MovieEventsData(context);
        // Fetching data.
        SQLiteDatabase db = eventsData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS , null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(0);
            String favourite = cursor.getString(1);
            double rating = cursor.getDouble(2);
            int year = cursor.getInt(3);
            if (favourites) {
                if (favourite.equals("YES")) {
                    Movie movie = new Movie(title, rating, year);
                    moviesList.add(movie);
                }
            }
            else {
                Movie movie = new Movie(title, rating, year);
                moviesList.add(movie);
            }
            if (favourite.equals("YES")) {
                Movie movie = new Movie(title, rating, year);
                favouritesToAddImage.add(movie);
            }
        }
        Collections.sort(moviesList);
        return moviesList;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        ArrayAdapter arrayAdapter;
        // Setting up array adapter.
        if (activity.equals("display") || activity.equals("favourite") || activity.equals("rating")) {
            arrayAdapter = new MovieAdapter((Activity) context, R.layout.list_view,
                    moviesList, favouritesToAddImage, true, true);
        }
        else {
            arrayAdapter = new MovieAdapter((Activity) context, R.layout.list_view,
                    moviesList, favouritesToAddImage, false, true);
        }
        listView.get().setAdapter(arrayAdapter);
        ViewMoviesActivity.moviesList = moviesList;
    }
}
