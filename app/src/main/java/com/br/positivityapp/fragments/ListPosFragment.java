package com.br.positivityapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.positivityapp.R;
import com.br.positivityapp.dao.EventDiarySetDAO;
import com.br.positivityapp.models.EventDiary;
import com.br.positivityapp.positivityapp.MainActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPosFragment extends Fragment {

    public ListView listView;
    private CalendarDay day;
    public MainActivity.SectionsPagerAdapter.CalendarPageListener listener;
    public String[] fullDescriptions;

    public ListPosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_pos, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Rever Eventos");

        final String[] descriptions = loadDescriptions();
        listView = (ListView)root.findViewById(R.id.list_pos);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,descriptions);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Description");
                alert.setMessage(fullDescriptions[position]);
                alert.setNeutralButton("Ok", null);
                alert.show();
            }
        });

        return root;
    }

    public String[] loadDescriptions() {
        ArrayList<EventDiary> eventsDiary = new EventDiarySetDAO(getActivity()).getEventDiaryByDate(day);
        String[] descriptions = new String[eventsDiary.size()];
        fullDescriptions = new String[eventsDiary.size()];

        for (int i = 0; i < eventsDiary.size(); i++) {
            String desc = eventsDiary.get(i).getDescription();
            fullDescriptions[i] = desc;

            if (desc.length() > 25) {
                desc = desc.substring(0,25).concat("...");
            }
            descriptions[i] = desc;
        }

        return descriptions;
    }

    public CalendarDay getDay() {
        return day;
    }

    public void setDay(CalendarDay day) {
        this.day = day;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).tabLayout.setVisibility(View.GONE);
    }
}
