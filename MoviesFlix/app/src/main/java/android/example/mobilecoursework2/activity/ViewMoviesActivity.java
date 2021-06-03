package android.example.mobilecoursework2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.asynctask.FetchDatabaseData;
import android.example.mobilecoursework2.database.MovieEventsData;
import android.example.mobilecoursework2.movie.Movie;
import android.example.mobilecoursework2.movie.MovieAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.example.mobilecoursework2.database.MovieConstants.FAVOURITE;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.TABLE_NAME;

public class ViewMoviesActivity extends AppCompatActivity {

    public static List<Movie> moviesList = new ArrayList<>();
    public static List<Integer> favouriteMoviesIndex = new ArrayList<>();
    public static List<Integer> notFavouriteMovies = new ArrayList<>();
    public static int checkedMoviePosition = -1;
    private int moviePosition;

    private Intent intent;
    private MovieEventsData eventsData;
    private ListView listView;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        eventsData = new MovieEventsData(this);
        listView = findViewById(R.id.listView);
        actionButton = findViewById(R.id.edit_button);
        ImageView activityIcon = findViewById(R.id.activity_imageView);

        intent = getIntent();
        String activity = intent.getStringExtra("activity");

        moviesList.clear();
        favouriteMoviesIndex.clear();
        notFavouriteMovies.clear();
        checkedMoviePosition = -1;

        switch (activity) {
            // Display Movies Activity
            case "display": {
                // Fetch the saved data from database.
                FetchDatabaseData databaseData = new FetchDatabaseData(listView, this, activity);
                databaseData.execute();
                // Checkbox shouldn't be ticked.
                MovieAdapter.allChecked = false;
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Make checkbox ticked on click.
                        CheckBox checkBox = view.findViewById(R.id.checkBox);
                        checkBox.setChecked(!checkBox.isChecked());
                        // Add selected movies to a list.
                        if (checkBox.isChecked()) {
                            favouriteMoviesIndex.add(position);
                        } else {
                            favouriteMoviesIndex.remove((Integer) position);
                        }
                    }
                });
                actionButton.setText(R.string.add_to_favourites);
                break;
            }
            // Favourites Activity
            case "favourite": {
                activityIcon.setImageResource(R.drawable.favourite);
                // Fetch the favourite movies from database.
                FetchDatabaseData databaseData = new FetchDatabaseData(listView, this, activity);
                databaseData.execute();
                // Checkbox should be ticked.
                MovieAdapter.allChecked = true;
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Make checkbox ticked on click.
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                        checkBox.setChecked(!checkBox.isChecked());
                        // Add unselected movies to a list.
                        if (!checkBox.isChecked()) {
                            notFavouriteMovies.add(position);
                        }
                        else {
                            int index = notFavouriteMovies.indexOf(position);
                            notFavouriteMovies.remove(index);
                        }
                    }
                });
                actionButton.setText(R.string.save);
                break;
            }
            // Edit Movies Activity
            case "edit": {
                activityIcon.setImageResource(R.drawable.edit);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Start saveMovieActivity on click of an item.
                        Intent intent = new Intent(ViewMoviesActivity.this, SaveMovieActivity.class);
                        intent.putExtra("movie", moviesList.get(position).getTitle());
                        intent.putExtra("edit", true);
                        startActivity(intent);
                    }
                });
                actionButton.setVisibility(View.INVISIBLE);
                break;
            }
            // Ratings Activity
            case "rating": {
                activityIcon.setImageResource(R.drawable.rating);
                // Fetch the saved data from database.
                FetchDatabaseData databaseData = new FetchDatabaseData(listView, this, activity);
                databaseData.execute();
                // Checkbox shouldn't be ticked.
                MovieAdapter.allChecked = false;
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int previouslyClickedPosition;
                        // Current visible item clicked position in the list view.
                        int clickedVisiblePosition = position - listView.getFirstVisiblePosition();
                        // Total visible positions in the list view.
                        int totalVisiblePositions = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
                        // Ticking the clicked checkbox.
                        CheckBox checkBox = listView.getChildAt(clickedVisiblePosition).findViewById(R.id.checkBox);
                        checkBox.setChecked(!checkBox.isChecked());
                        // Changing checkbox when clicking already selected item.
                        previouslyClickedPosition = checkedMoviePosition;
                        checkedMoviePosition = position;
                        if (checkedMoviePosition == previouslyClickedPosition) {
                            checkedMoviePosition = -1;
                        }
                        // Single choice mode - Making other checkboxes not ticked when ticking one.
                        for (int i = 0; i <= totalVisiblePositions; i++) {
                            if (i != clickedVisiblePosition) {
                                checkBox = listView.getChildAt(i).findViewById(R.id.checkBox);
                                checkBox.setChecked(false);
                            }
                        }
                        moviePosition = position;
                    }
                });
                actionButton.setText(R.string.imdb);
                break;
            }
        }
    }

    // Method to execute when clicking the action button.
    public void performAction(View view) {
        if (actionButton.getText().equals(getString(R.string.add_to_favourites))) {
            addToFavourites();
        }
        else if (actionButton.getText().equals(getString(R.string.save))) {
            updateFavourites();
        }
        else {
            getRating();
        }
    }

    // Method to add favourite movies.
    private void addToFavourites() {
        SQLiteDatabase db = eventsData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVOURITE, "YES");
        for (int position : favouriteMoviesIndex) {
            db.update(TABLE_NAME, values, MOVIE_NAME + "=" + "'"
                    + moviesList.get(position).getTitle() + "'", null);
        }
        finish();
    }

    // Method to remove favourite movies.
    private void updateFavourites() {
        SQLiteDatabase db = eventsData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVOURITE, "NO");
        for (int position : notFavouriteMovies) {
            db.update(TABLE_NAME, values, MOVIE_NAME + "=" + "'"
                    + moviesList.get(position).getTitle() + "'", null);
        }
        finish();
    }

    // Method to start RatingsActivity for a selected movie.
    private void getRating() {
        if (checkedMoviePosition == -1) {
            showAlertDialog("Please select a movie!");
        }
        else if (!isConnectedToNetwork()) {
            showAlertDialog("Please connect to a network to continue!");
        }
        else {
            Intent intent = new Intent(ViewMoviesActivity.this, RatingsActivity.class);
            intent.putExtra("title", moviesList.get(moviePosition).getTitle());
            startActivity(intent);
        }
    }

    // Fetch data on resume of the activity.
    @Override
    protected void onResume() {
        super.onResume();
        if (intent.getStringExtra("activity").equals("edit")) {
            moviesList.clear();
            FetchDatabaseData databaseData = new FetchDatabaseData(listView, this, "edit");
            databaseData.execute();
        }
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

    // Method to check network connection.
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // Wifi connection.
        NetworkInfo mobileDataConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // Mobile data connection.
        return (wifiConnection != null && wifiConnection.isConnected())
                || (mobileDataConnection != null && mobileDataConnection.isConnected());
    }
}