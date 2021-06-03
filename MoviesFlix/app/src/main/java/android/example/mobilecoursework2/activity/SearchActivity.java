package android.example.mobilecoursework2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.database.MovieEventsData;
import android.example.mobilecoursework2.movie.Movie;
import android.example.mobilecoursework2.search.SearchResult;
import android.example.mobilecoursework2.search.SearchResultAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.example.mobilecoursework2.database.MovieConstants.TABLE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_ACTORS;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_DIRECTOR;


public class SearchActivity extends AppCompatActivity {

    private final String[] COLUMNS = {MOVIE_NAME, MOVIE_ACTORS, MOVIE_DIRECTOR};
    private final Map<String, SearchResult> resultMap = new HashMap<>();
    private final List<String> actorsAndDirectorsList = new ArrayList<>();
    private final List<String> directorsList = new ArrayList<>();
    private final List<String> actorsList = new ArrayList<>();
    private final List<String> onlyActorsList = new ArrayList<>();
    private final List<String> onlyDirectorsList = new ArrayList<>();
    private final List<String> titlesList = new ArrayList<>();
    private final List<Movie> moviesList = new ArrayList<>();
    private final String[] spinnerList = {"   Titles", "   Actors", "   Directors", "   Actors & Directors"};
    private String searchQuery;
    private MovieEventsData eventsData;

    private ListView searchListView;
    private EditText searchEditText;
    private Spinner filterSpinner;
    private ImageButton filterButton;
    private ArrayAdapter resultsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        eventsData = new MovieEventsData(this);
        searchListView = findViewById(R.id.search_listView);
        searchEditText = findViewById(R.id.search_editText);
        filterSpinner = findViewById(R.id.filter_spinner);
        filterButton = findViewById(R.id.filter_button);
        ImageView activityIcon = findViewById(R.id.activity_imageView);
        activityIcon.setImageResource(R.drawable.search);
        filterSpinner.setVisibility(View.INVISIBLE);
        filterButton.setVisibility(View.INVISIBLE);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner, spinnerList);
        filterSpinner.setAdapter(arrayAdapter);

        // On focus change make the filter invisible.
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterSpinner.setVisibility(View.INVISIBLE);
                    filterButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    // Method to execute when clicking search button.
    public void searchData(View view) {
        resultMap.clear();
        searchQuery = searchEditText.getText().toString();
        // Message for empty query.
        if (searchQuery.equals("")) {
            showAlertDialog("Please type something!");
        }
        else {
            // Background thread to fetch data since fetching large amount of data in the main thread might freeze the UI.
            Thread searchThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SQLiteDatabase db = eventsData.getReadableDatabase();
                    Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null,
                            null, null, null);

                    while (cursor.moveToNext()) {
                        String title = cursor.getString(0);
                        String actors = cursor.getString(1);
                        String[] actorsArray = cursor.getString(1).split(", ");
                        String director = cursor.getString(2);
                        titlesList.add(title); // Adding the movie names.
                        directorsList.add(director); // Adding the movie directors.
                        actorsList.addAll(Arrays.asList(actorsArray)); // Adding the movie actors.
                        Movie movie = new Movie(title, director, actors);
                        moviesList.add(movie);
                    }
                    cursor.close();

                    // Separate actors and directors, ie. actors who can direct can be in both actors and directors list.
                    // Storing the people who can do both in a separate list called actorsAndDirectorsList.
                    for (String actor : actorsList) {
                        for (String director : directorsList) {
                            if (director.equals(actor)) {
                                actorsAndDirectorsList.add(actor);
                            } else {
                                onlyDirectorsList.add(director);
                                onlyActorsList.add(actor);
                            }
                        }
                    }

                    // Searching the results for the search query in the prepared movies, actors, directors and actorsAndDirectors lists.
                    searchInList(titlesList, searchQuery, "Movie", resultMap, true);
                    searchInList(onlyDirectorsList, searchQuery, "Director", resultMap, true);
                    searchInList(onlyActorsList, searchQuery, "Actor", resultMap, true);
                    searchInList(actorsAndDirectorsList, searchQuery, "Actor & Director", resultMap, true);

                    // Updating the UI in the main thread.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<SearchResult> searchResultList = new ArrayList<>(resultMap.values());
                            Collections.sort(searchResultList);
                            resultsArrayAdapter = new SearchResultAdapter(SearchActivity.this,
                                    R.layout.list_view, searchResultList);
                            searchListView.setAdapter(resultsArrayAdapter);
                            // Message if no results for the search query.
                            if (searchResultList.isEmpty()) {
                                showAlertDialog("No matches found!");
                            }
                            // Filter option will be available once results are shown.
                            else {
                                filterSpinner.setVisibility(View.VISIBLE);
                                filterButton.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
            searchThread.start();
        }
    }

    // Method to execute when clicking the filter button.
    public void filterData(View view) {
        searchEditText.setFocusable(false);
        searchEditText.setFocusableInTouchMode(true);
        Map<String, SearchResult> resultMap = new HashMap<>();
        String filterQuery = filterSpinner.getSelectedItem().toString();
        // Filtering each of the categories.
        switch (filterQuery) {
            case "   Titles": {
                filterInList(titlesList, searchQuery, "Title", resultMap);
                break;
            }
            case "   Actors": {
                filterInList(onlyActorsList, searchQuery, "Actor", resultMap);
                break;
            }
            case "   Directors": {
                filterInList(onlyDirectorsList, searchQuery, "Director", resultMap);
                break;
            }
            default: {
                filterInList(actorsAndDirectorsList, searchQuery, "Actor & Director", resultMap);
                break;
            }
        }
        // Message if no results for the filter query.
        if (resultMap.isEmpty()) {
            showAlertDialog("No matches available!");
        }
    }

    // Method to search data.
    // Map is used to remove duplicate items.
    private void searchInList(List<String> toSearchList, String searchQuery, String resultType, Map<String, SearchResult> resultMap, boolean isSearch) {
        // For filter.
        if (!isSearch) {
            for (String stringValue : toSearchList) {
                if (stringValue.toLowerCase().contains(searchQuery.toLowerCase())) {
                    SearchResult searchResult = new SearchResult(stringValue, resultType);
                    resultMap.put(searchResult.getResultTitle(), searchResult);
                }
            }
        }
        // For search.
        else {
            for (Movie movie : moviesList) {
                if (movie.getTitle().toLowerCase().contains(searchQuery.toLowerCase())
                        || movie.getDirector().toLowerCase().contains(searchQuery.toLowerCase())
                        || movie.getActors().toLowerCase().contains(searchQuery.toLowerCase())) {
                    SearchResult searchResult = new SearchResult(movie.getTitle(), "Movie");
                    resultMap.put(searchResult.getResultTitle(), searchResult);
                }
            }
        }
    }

    // Method to filter data using searched query.
    // Map is used to remove duplicate items.
    private void filterInList(List<String> toFilterList, String searchQuery, String resultType, Map<String, SearchResult> resultMap) {
        searchInList(toFilterList, searchQuery, resultType, resultMap, false);
        List<SearchResult> searchResultList = new ArrayList<>(resultMap.values());
        Collections.sort(searchResultList);
        resultsArrayAdapter = new SearchResultAdapter(SearchActivity.this, R.layout.list_view, searchResultList);
        searchListView.setAdapter(resultsArrayAdapter);
    }

    // Method to show dialog box.
    private void showAlertDialog(String title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alertDialog.create();
        alertDialog.show();
    }
}