/*
package com.example.spotparking.Adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.example.spotparking.PlaceApi;

import java.util.ArrayList;

public class PlacesAutoSuggestAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> results;
    private int resource;

    private Context context;
    private PlaceApi placeApi = new PlaceApi();

    public PlacesAutoSuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    public int getCount() {
        return results.size();
    }

    public String getItem(int pos) {
        return results.get(pos);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence != null) {
                    results = placeApi.AutoComplete(charSequence.toString());
                    Log.d("Results", "performFiltering: " + results);
                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
*/
