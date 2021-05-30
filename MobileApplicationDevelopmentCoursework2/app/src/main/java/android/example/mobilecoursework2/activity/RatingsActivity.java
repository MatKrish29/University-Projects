package android.example.mobilecoursework2.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.asynctask.FetchApiData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RatingsActivity extends AppCompatActivity {

    public static String MOVIE_TITLE;
    public static List<JSONObject> dataList = new ArrayList<>();

    private ImageView imageView;
    private ImageButton imageButton;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        Intent intent = getIntent();
        MOVIE_TITLE = intent.getStringExtra("title");

        listView = findViewById(R.id.ratings_listView);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        imageButton = findViewById(R.id.imageButton);
        ImageView activityIcon = findViewById(R.id.activity_imageView);

        activityIcon.setImageResource(R.drawable.rating);
        imageButton.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        // Fetching the imdb data
        FetchApiData apiData = new FetchApiData(listView, this, progressBar);
        apiData.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                // Background thread to get the imdb image.
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imageLink = null;
                        try {
                            imageLink = dataList.get(position).getString("image");
                            System.out.println(imageLink);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            // Getting the input stream from the string and decoding it to a bitmap.
                            InputStream inputStream = new java.net.URL(imageLink).openStream();
                            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                            // Updating the UI in the main thread.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(imageBitmap);
                                    imageView.setVisibility(View.VISIBLE);
                                    imageButton.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        // On long click show context menu.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Show popup.
                PopupMenu popup = new PopupMenu(RatingsActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
                popup.show();

                // Getting the title and year in the clicked item.
                int visiblePosition = position - listView.getFirstVisiblePosition();
                TextView titleTextView = listView.getChildAt(visiblePosition).findViewById(R.id.title_textView);
                TextView yearTextView = listView.getChildAt(visiblePosition).findViewById(R.id.year_textView);

                String title = titleTextView.getText().toString();
                String year = yearTextView.getText().toString();
                String builtTitle = title.replaceAll("\\s", "-").replace(":", "");
                String builtYear = year.substring(7);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            // Navigate to register movie with details.
                            case R.id.context_register:
                                SharedPreferences sharedPreferences = getSharedPreferences("Register Movie", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("title", title);
                                editor.putString("year", builtYear);
                                editor.putBoolean("isFillingNeeded", true);
                                editor.apply();

                                Intent intent = new Intent(RatingsActivity.this, SaveMovieActivity.class);
                                startActivity(intent);
                                return true;
                            // Navigate to YTS website to download movie.
                            case R.id.context_download:
                                String searchQuery = builtTitle.toLowerCase() + "-" + builtYear;
                                goToDownloadPage(searchQuery);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                return true;
            }
        });
    }

    // Method to close the image
    public void closeImage(View view) {
        imageView.setVisibility(View.INVISIBLE);
        imageButton.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    // Method to create implicit intent for the web page.
    private void goToDownloadPage(String searchQuery) {
        String url = "https://yts.mx/movies/" + searchQuery;
        System.out.println(url);

        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

        if (intent.resolveActivity(getPackageManager()) == null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}