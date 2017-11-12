package ca.bcit.ass3.assignment3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dan on 2017-11-03.
 */

public class ItemsAdapter extends ArrayAdapter<ItemsDetails> {
    Context _context;
    public ItemsAdapter(Context context, ArrayList<ItemsDetails> events) {
        super(context, 0, events);
        _context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        ItemsDetails item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView itemUnit = (TextView) convertView.findViewById(R.id.itemUnit);
        TextView itemQuantity = (TextView) convertView.findViewById(R.id.itemQuantity);

        // Populate the data into the template view using the data object
        itemName.setText(item.get_name());
        itemUnit.setText(item.get_unit());
        itemQuantity.setText(Integer.toString(item.getQuantity()));

        // Return the completed view to render on screen
        return convertView;
    }
}
