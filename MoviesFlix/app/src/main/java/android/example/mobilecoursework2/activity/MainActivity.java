package android.example.mobilecoursework2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.mobilecoursework2.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button registerMovieButton;
    private Button displayMoviesButton;
    private Button favouriteMoviesButton;
    private Button editMoviesButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerMovieButton = findViewById(R.id.register_movie_button);
        displayMoviesButton = findViewById(R.id.displayMoviesButton);
        favouriteMoviesButton = findViewById(R.id.favourites_button);
        editMoviesButton = findViewById(R.id.edit_activity);
        searchButton = findViewById(R.id.search_activity);
    }

    // Method to start relevant activity.
    public void launchActivity(View view) {
        if (view.getId() == registerMovieButton.getId()) {
            Intent intent = new Intent(this, SaveMovieActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == displayMoviesButton.getId()) {
            Intent intent = new Intent(this, ViewMoviesActivity.class);
            intent.putExtra("activity", "display");
            startActivity(intent);
        }
        else if (view.getId() == favouriteMoviesButton.getId()) {
            Intent intent = new Intent(this, ViewMoviesActivity.class);
            intent.putExtra("activity", "favourite");
            startActivity(intent);
        }
        else if (view.getId() == searchButton.getId()) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == editMoviesButton.getId()) {
            Intent intent = new Intent(this, ViewMoviesActivity.class);
            intent.putExtra("activity", "edit");
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, ViewMoviesActivity.class);
            intent.putExtra("activity", "rating");
            startActivity(intent);
        }
    }
}