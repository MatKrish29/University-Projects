package android.example.mobilecoursework2.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.activity.RatingsActivity;
import android.example.mobilecoursework2.movie.Movie;
import android.example.mobilecoursework2.movie.MovieAdapter;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.example.mobilecoursework2.activity.RatingsActivity.MOVIE_TITLE;
import static android.example.mobilecoursework2.activity.RatingsActivity.dataList;

public class FetchApiData extends android.os.AsyncTask<Void, Void, List<Movie>> {

    private final WeakReference<ListView> listView;
    private final WeakReference<ProgressBar> progressBar;
    private final Context context;

    public FetchApiData(ListView listView, Context context, ProgressBar progressBar) {
        this.listView = new WeakReference<>(listView);
        this.context = context;
        this.progressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        RatingsActivity.dataList.clear();
        List<Movie> movieList = new ArrayList<>();
        JSONObject data = getJsonObject("SearchTitle", MOVIE_TITLE);
        JSONArray dataArray;
        try {
            dataArray = data.getJSONArray("results");
            for (int i = 0; i < dataArray.length(); i++) {
                data = dataArray.getJSONObject(i);
                String title = data.getString("title");
                String id = data.getString("id");
                // Getting rating using id.
                JSONObject ratingData = getJsonObject("Ratings", id);
                String errorMessage = ratingData.getString("errorMessage");
                System.out.println(ratingData);
                // Checking for error.
                if (errorMessage.equals("")) {
                    String rating = ratingData.getString("imDb");
                    String year = ratingData.getString("year");
                    System.out.println(year);
                    // Checking whether all info are present.
                    if (!title.isEmpty() && !year.isEmpty() && !rating.isEmpty()) {
                        Movie movie = new Movie(title, Double.parseDouble(rating), Integer.parseInt(year));
                        movieList.add(movie);
                        dataList.add(data);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(movieList.toString());
        return movieList;
    }

    // Method to fetch api data.
    private JSONObject getJsonObject(String searchParameter, String query) {
        String BASE_URL = "https://imdb-api.com/en/API/" + searchParameter + "/k_qoncyrc8/" + query;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().build();
        System.out.println(builtUri);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setReadTimeout(30000);
        connection.setConnectTimeout(30000);
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoInput(true);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contentAsString = "";
        InputStreamReader inputStreamReader;
        InputStream inputStream = null;
        int streamData;
        try {
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            streamData = inputStreamReader.read();
            while (streamData != -1) {
                char content = (char) streamData;
                streamData = inputStreamReader.read();
                contentAsString += content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONObject data = null;
        try {
            if (inputStream != null) {
                data = new JSONObject(contentAsString);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        // Setting up adapter.
        ArrayAdapter arrayAdapter = new MovieAdapter((Activity) context, R.layout.list_view, movies, false, false);
        listView.get().setAdapter(arrayAdapter);
        progressBar.get().setVisibility(View.INVISIBLE);
        if (movies.isEmpty()) {
            showAlertDialog();
        }
    }

    // Method to show dialog box.
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("No data found!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alertDialog.create();
        alertDialog.show();
    }
}
