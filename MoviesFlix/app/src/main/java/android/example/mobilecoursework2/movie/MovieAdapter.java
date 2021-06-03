package android.example.mobilecoursework2.movie;

import android.app.Activity;
import android.example.mobilecoursework2.R;
import android.example.mobilecoursework2.activity.ViewMoviesActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// Custom adapter for search results list view
public class MovieAdapter extends ArrayAdapter<Movie> {
    private final List<Movie> movieList;
    private List<Movie> favouritesToAddImage;
    private final Activity context;
    private final boolean checkboxNeeded;
    private boolean isDisplay;
    public static boolean allChecked;

    public MovieAdapter(@NonNull Activity context, int resourceLayout, @NonNull List<Movie> movieList, boolean checkboxNeeded,
                        boolean isDisplay) {
        super(context, resourceLayout, movieList);
        this.movieList = movieList;
        this.context = context;
        this.checkboxNeeded = checkboxNeeded;
        this.isDisplay = isDisplay;
    }

    public MovieAdapter(@NonNull Activity context, int resourceLayout, @NonNull List<Movie> movieList,
                        @NonNull List<Movie> favouritesToAddImage, boolean checkboxNeeded, boolean isDisplay) {
        super(context, resourceLayout, movieList);
        this.movieList = movieList;
        this.favouritesToAddImage = favouritesToAddImage;
        this.context = context;
        this.checkboxNeeded = checkboxNeeded;
        this.isDisplay = isDisplay;
    }

    @NonNull
    @Override
    // To get row views in list view
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewRow = layoutInflater.inflate(R.layout.list_view, null, true);

        TextView titleTextView = listViewRow.findViewById(R.id.title_textView);
        TextView ratingTextView = listViewRow.findViewById(R.id.rating_textView);
        TextView yearTextView = listViewRow.findViewById(R.id.year_textView);
        CheckBox checkBox = listViewRow.findViewById(R.id.checkBox);
        ImageView imageView = listViewRow.findViewById(R.id.favourite_imageView);

        checkBox.setVisibility(View.INVISIBLE);
        // To check whether checkbox is needed
        if (checkboxNeeded) {
            checkBox.setVisibility(View.VISIBLE);
            // Initially checkboxes ticked
            if (allChecked) {
                checkBox.setChecked(true);
            }
            // Restoring the state of checkbox
            for (int checkedMoviePosition : ViewMoviesActivity.favouriteMoviesIndex) {
                if (checkedMoviePosition == position) {
                    checkBox.setChecked(true);
                }
            }
            for (int uncheckedMoviePosition : ViewMoviesActivity.notFavouriteMovies) {
                if (uncheckedMoviePosition == position) {
                    checkBox.setChecked(false);
                }
            }
            if (ViewMoviesActivity.checkedMoviePosition == position) {
                checkBox.setChecked(true);
            }
        }

        String title = movieList.get(position).getTitle();
        String rating = "Rating - " + movieList.get(position).getRating();
        String year = "Year - " + movieList.get(position).getYear();

        titleTextView.setText(title);
        ratingTextView.setText(rating);
        yearTextView.setText(year);
        imageView.setImageDrawable(null);

        // To display favourite icon for favourite movies.
        if (isDisplay) {
            for (Movie movie : favouritesToAddImage) {
                if (movie.getTitle().equals(title)) {
                    imageView.setImageResource(R.drawable.favourite);
                }
            }
        }

        return listViewRow;
    }
}
