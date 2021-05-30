package android.example.mobilecoursework2.search;

import android.app.Activity;
import android.example.mobilecoursework2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// Custom adapter for search results list view
public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    private Activity context;
    private List<SearchResult> resultList;

    public SearchResultAdapter(@NonNull Activity context, int resource, @NonNull List<SearchResult> resultList) {
        super(context, resource, resultList);
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    // To get row views in list view
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewRow = layoutInflater.inflate(R.layout.list_view, null, true);

        TextView titleTextView = (TextView) listViewRow.findViewById(R.id.title_textView);
        TextView ratingTextView = (TextView) listViewRow.findViewById(R.id.rating_textView);
        TextView typeTextView = (TextView) listViewRow.findViewById(R.id.year_textView);
        CheckBox checkBox = (CheckBox) listViewRow.findViewById(R.id.checkBox);

        checkBox.setVisibility(View.INVISIBLE);
        ratingTextView.setVisibility(View.INVISIBLE);

        titleTextView.setText(resultList.get(position).getResultTitle());
        typeTextView.setText(resultList.get(position).getResultType());

        return listViewRow;
    }
}
