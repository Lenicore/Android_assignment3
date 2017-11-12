package ca.bcit.ass3.assignment3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvFragment extends Fragment {
    private int eventID;
    private DbHelper helper;
    private ArrayList<ItemsDetails> itemList;
    private ItemsAdapter adapter;

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }


    public EvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        View container = getView();
        helper = new DbHelper(this.getActivity());

    //    itemList = helper.getItems(eventId);

        itemList.clear();
        helper.getAllItems(itemList, eventID);
        adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ev, container, false);

        helper = new DbHelper(this.getActivity());
        itemList = new ArrayList<>();
        helper.getAllItems(itemList, eventID);
        ListView lv = (ListView) view.findViewById(R.id.itemList);



        adapter = new ItemsAdapter(getActivity(), itemList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                intent.putExtra("detailID", itemList.get(i).getItem_ID());
                startActivity(intent);
            }
        });

        // add Button listener
        Button btnEventAdd = (Button)view.findViewById(R.id.addItem);
        btnEventAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });

        // delete Button listener
        Button btnEventDel = (Button)view.findViewById(R.id.deleteEvent);
        btnEventDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.deleteEvent(helper.getWritableDatabase(), eventID);
                getActivity().finish();
            }
        });


        // edit Button listener
        Button btnEventEdit = (Button)view.findViewById(R.id.editEvent);
        btnEventEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditEventActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });

        return view;
    }

}
