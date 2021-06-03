package android.example.mobilecoursework2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.database.MovieEventsData;
import android.example.mobilecoursework2.movie.Movie;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.example.mobilecoursework2.database.MovieConstants.PRIMARY_KEY;
import static android.example.mobilecoursework2.database.MovieConstants.TABLE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_DIRECTOR;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_ACTORS;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_YEAR;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_RATING;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_REVIEW;
import static android.example.mobilecoursework2.database.MovieConstants.FAVOURITE;

public class SaveMovieActivity extends AppCompatActivity {

    private MovieEventsData movieEventsData;
    private String movie;
    private final String[] COLUMNS = {PRIMARY_KEY, MOVIE_DIRECTOR, MOVIE_ACTORS, MOVIE_YEAR, MOVIE_RATING, MOVIE_REVIEW, FAVOURITE, MOVIE_NAME};
    private final List<String> titleList = new ArrayList<>();
    private int primaryKey;
    private boolean isNameInvalid;
    private boolean isYearInvalid;
    private boolean isRatingInvalid;
    private boolean isSaved;
    private boolean isEditActivity;

    private EditText movieNameEditText;
    private EditText movieReleasedYearEditText;
    private EditText movieDirectorEditText;
    private EditText movieActorsEditText;
    private EditText movieRatingEditText;
    private EditText movieReviewEditText;
    private RatingBar ratingBar1;
    private RatingBar ratingBar2;
    private Button actionButton;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
        movieEventsData = new MovieEventsData(this);

        movieNameEditText = findViewById(R.id.movie_title_editText);
        movieReleasedYearEditText = findViewById(R.id.movie_year_editText);
        movieDirectorEditText = findViewById(R.id.movie_director_editText);
        movieActorsEditText = findViewById(R.id.movie_actors_editText);
        movieRatingEditText = findViewById(R.id.movie_rating_editText);
        movieReviewEditText = findViewById(R.id.movie_review_editText);
        spinner = findViewById(R.id.spinner);
        TextView movieFavouriteTextView = findViewById(R.id.movie_favourite_textView);
        ratingBar1 = findViewById(R.id.ratingBar1);
        ratingBar2 = findViewById(R.id.ratingBar2);
        actionButton = findViewById(R.id.save_button);
        ImageView activityIcon = findViewById(R.id.activity_imageView);

        spinner.setVisibility(View.INVISIBLE);
        movieFavouriteTextView.setVisibility(View.INVISIBLE);
        ratingBar1.setVisibility(View.INVISIBLE);
        ratingBar2.setVisibility(View.INVISIBLE);
        activityIcon.setImageResource(R.drawable.register);

        SQLiteDatabase db = movieEventsData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS ,  null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(7);
            titleList.add(title);
        }
        cursor.close();

        Intent intent = getIntent();
        isEditActivity = intent.getBooleanExtra("edit", false);
        movie = intent.getStringExtra("movie");

        if (isEditActivity) {
            activityIcon.setImageResource(R.drawable.edit);
            spinner.setVisibility(View.VISIBLE);
            movieFavouriteTextView.setVisibility(View.VISIBLE);
            ratingBar1.setVisibility(View.VISIBLE);
            ratingBar2.setVisibility(View.VISIBLE);
            ratingBar1.setStepSize(1);
            ratingBar2.setStepSize(1);
            movieRatingEditText.setVisibility(View.INVISIBLE);
            actionButton.setText(R.string.update);

            // Setting spinner.
            List<String> choices = new ArrayList<>();
            choices.add("Favourite");
            choices.add("Not Favourite");
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner, choices);
            adapter.setDropDownViewResource(R.layout.spinner);
            spinner.setAdapter(adapter);

            // Getting the movie details which the user has clicked.
            movieNameEditText.setText(movie);
            db = movieEventsData.getReadableDatabase();
            cursor = db.query(TABLE_NAME, COLUMNS , MOVIE_NAME + "=" + "'" + movie + "'", null,
                    null, null, null);

            while (cursor.moveToNext()) {
                primaryKey = cursor.getInt(0);
                String director = cursor.getString(1);
                String actors = cursor.getString(2);
                int year = cursor.getInt(3);
                float rating = cursor.getFloat(4);
                String review = cursor.getString(5);
                String favourite = cursor.getString(6);

                if (rating > 5) {
                    ratingBar1.setRating(5);
                    ratingBar2.setRating(rating - 5);
                }
                else {
                    ratingBar1.setRating(rating);
                }

                movieReleasedYearEditText.setText(String.valueOf(year));
                movieDirectorEditText.setText(director);
                movieActorsEditText.setText(actors);
                movieReviewEditText.setText(review);
                spinner.setSelection(favourite.equals("YES") ? 0 : 1);
            }
            cursor.close();
        }

        // Clicking top rating bar should make the stars white in bottom rating bar.
        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar2.setRating(0);
                ratingBar.setRating(rating);
            }
        });
        // Clicking bottom rating bar should make the stars yellow in top rating bar.
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar1.setRating(5);
                ratingBar.setRating(rating);
            }
        });

        // On focus change check for title validations.
        movieNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                isNameInvalid = false;
                String movieTitle = movieNameEditText.getText().toString().toLowerCase();
                if (!hasFocus) {
                    for (String title : titleList) {
                        // In update check only for already added movies except the current movie.
                        if (isEditActivity) {
                            if (movieTitle.equals(title.toLowerCase()) && !movieTitle.equals(movie.toLowerCase())) {
                                movieNameEditText.setTextColor(Color.RED);
                                actionButton.setEnabled(false);
                                showAlertDialog("Invalid Movie Name!", title + " is already added.");
                                isNameInvalid = true;
                            }
                        }
                        //In register check for all the movies added.
                        else {
                            if (movieTitle.equals(title.toLowerCase())) {
                                movieNameEditText.setTextColor(Color.RED);
                                actionButton.setEnabled(false);
                                showAlertDialog("Invalid Movie Name!", title + " is already added.");
                                isNameInvalid = true;
                            }
                        }
                    }
                }
                if (!isNameInvalid) {
                    movieNameEditText.setTextColor(Color.BLACK);
                }
                if (!isNameInvalid && !isYearInvalid && !isRatingInvalid) {
                    actionButton.setEnabled(true);
                }
            }
        });
        // On focus change check for year validations.
        movieReleasedYearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isYearInvalid = false;
                String year = movieReleasedYearEditText.getText().toString();
                if (!hasFocus && !year.equals("")) {
                    // Year greater than 1895.
                    if (Integer.parseInt(year) < 1896) {
                        showAlertDialog("Invalid Year!", "Movie released year should be greater than 1895.");
                        movieReleasedYearEditText.setTextColor(Color.RED);
                        actionButton.setEnabled(false);
                        isYearInvalid = true;
                    }
                }
                if (!isYearInvalid) {
                    movieReleasedYearEditText.setTextColor(Color.BLACK);
                }
                if (!isNameInvalid && !isYearInvalid && !isRatingInvalid) {
                    actionButton.setEnabled(true);
                }
            }
        });
        // On focus change check for rating validations.
        movieRatingEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isRatingInvalid = false;
                String rating = movieRatingEditText.getText().toString();
                if (!hasFocus && !rating.equals("")) {
                    // Rating more than 0.
                    if (Integer.parseInt(rating) < 1) {
                        showAlertDialog("Invalid Rating!", "Movie rating shouldn't be less than 1.");
                        movieRatingEditText.setTextColor(Color.RED);
                        actionButton.setEnabled(false);
                        isRatingInvalid = true;
                    }
                    // Rating not more than 10.
                    else if (Integer.parseInt(rating) > 10) {
                        showAlertDialog("Invalid Rating!", "Movie rating shouldn't be greater than 10.");
                        movieRatingEditText.setTextColor(Color.RED);
                        actionButton.setEnabled(false);
                        isRatingInvalid = true;
                    }
                }
                if (!isRatingInvalid) {
                    movieRatingEditText.setTextColor(Color.BLACK);
                }
                if (!isNameInvalid && !isYearInvalid && !isRatingInvalid) {
                    actionButton.setEnabled(true);
                }
            }
        });
    }

    // Method to execute when clicking the
    public void saveData(View view) {
        // To validate inputs.
        movieReleasedYearEditText.setFocusable(false);
        movieRatingEditText.setFocusable(false);
        movieNameEditText.setFocusable(false);
        movieReleasedYearEditText.setFocusableInTouchMode(true);
        movieRatingEditText.setFocusableInTouchMode(true);
        movieNameEditText.setFocusableInTouchMode(true);
        actionButton.setEnabled(false);

        // Execute only if no errors.
        if (!isNameInvalid && !isYearInvalid && !isRatingInvalid) {
            String movieName = movieNameEditText.getText().toString();
            String releasedYear = movieReleasedYearEditText.getText().toString();
            String movieDirector = movieDirectorEditText.getText().toString();
            String movieActors = movieActorsEditText.getText().toString();
            String movieReview = movieReviewEditText.getText().toString();

            boolean isAllNotFilled = movieName.equals("") || releasedYear.equals("") || movieDirector.equals("")
                    || movieActors.equals("") || movieReview.equals("");

            if (isAllNotFilled) {
                showAlertDialog("Data Missing!", "Please fill all the fields.");
            }
            else {
                Movie movie = new Movie(movieName, Integer.parseInt(releasedYear), movieDirector, movieActors, movieReview);
                if (actionButton.getText().equals("SAVE")) {
                    saveNewData(movie);
                } else {
                    updateData(movie);
                }
            }
        }
    }

    // Method to clear all the fields.
    public void clearData(View view) {
        movieNameEditText.setText("");
        movieReleasedYearEditText.setText("");
        movieActorsEditText.setText("");
        movieDirectorEditText.setText("");
        movieRatingEditText.setText("");
        movieReviewEditText.setText("");
    }

    // Method to add new data to database.
    private void saveNewData(Movie movie) {
        isSaved = true;
        SQLiteDatabase db = movieEventsData.getWritableDatabase();
        db.insertOrThrow(TABLE_NAME, null, putValues(movie, false));
        finish();
    }

    // Method to update database.
    private void updateData(Movie movie) {
        SQLiteDatabase db = movieEventsData.getWritableDatabase();
        db.update(TABLE_NAME, putValues(movie, true), PRIMARY_KEY + "=" + primaryKey, null);
        finish();
    }

    // Method to put values into the database.
    private ContentValues putValues(Movie movie, boolean isUpdate) {
        int position = spinner.getSelectedItemPosition();
        ContentValues values = new ContentValues();

        values.put(MOVIE_NAME, movie.getTitle());
        values.put(MOVIE_YEAR, movie.getYear());
        values.put(MOVIE_DIRECTOR, movie.getDirector());
        values.put(MOVIE_ACTORS, movie.getActors());
        values.put(MOVIE_REVIEW, movie.getReview());
        if (isUpdate) {
            double movieRating = (ratingBar1.getRating() + ratingBar2.getRating());
            values.put(MOVIE_RATING, movieRating);
            values.put(FAVOURITE, position == 0 ? "YES" : "NO");
        }
        else {
            int movieRating = Integer.parseInt(movieRatingEditText.getText().toString());
            values.put(MOVIE_RATING, movieRating);
            values.put(FAVOURITE, "NO");
        }

        return values;
    }

    // Method to show dialog box.
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alertDialog.create();
        alertDialog.show();
    }

    // Save the fields on pause of the activity.
    @Override
    protected void onPause() {
        super.onPause();
        if (!isEditActivity) {
            SharedPreferences sharedPreferences = getSharedPreferences("SharedText", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isSaved) {
                editor.putBoolean("button", true);
            }
            else {
                editor.putBoolean("button", false);
                editor.putString("title", movieNameEditText.getText().toString());
                editor.putString("year", movieReleasedYearEditText.getText().toString());
                editor.putString("actors", movieActorsEditText.getText().toString());
                editor.putString("director", movieDirectorEditText.getText().toString());
                editor.putString("rating", movieRatingEditText.getText().toString());
                editor.putString("review", movieReviewEditText.getText().toString());
            }
            editor.apply();
        }
    }

    // Restore the fields on resume of the activity.
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("SharedText", MODE_PRIVATE);
        boolean isSaved = sharedPreferences.getBoolean("button", false);

        if (!isEditActivity && !isSaved) {
            String title = sharedPreferences.getString("title", "");
            String year = sharedPreferences.getString("year", "");
            String actors = sharedPreferences.getString("actors", "");
            String director = sharedPreferences.getString("director", "");
            String rating = sharedPreferences.getString("rating", "");
            String review = sharedPreferences.getString("review", "");

            movieNameEditText.setText(title);
            movieReleasedYearEditText.setText(year);
            movieActorsEditText.setText(actors);
            movieDirectorEditText.setText(director);
            movieRatingEditText.setText(rating);
            movieReviewEditText.setText(review);

            // To register movies from ratings activity.
            SharedPreferences ratingsSharedPreferences = getSharedPreferences("Register Movie", MODE_PRIVATE);
            boolean isFillingNeeded = ratingsSharedPreferences.getBoolean("isFillingNeeded", false);
            if (isFillingNeeded) {
                String movieTitle = ratingsSharedPreferences.getString("title", "");
                String movieYear = ratingsSharedPreferences.getString("year", "");
                movieNameEditText.setText(movieTitle);
                movieReleasedYearEditText.setText(movieYear);
                SharedPreferences.Editor editor = ratingsSharedPreferences.edit();
                editor.putBoolean("isFillingNeeded", false);
                editor.apply();
            }
        }
    }
}