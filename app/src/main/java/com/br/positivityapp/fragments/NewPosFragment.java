package com.br.positivityapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.positivityapp.R;
import com.br.positivityapp.dao.EventDiarySetDAO;
import com.br.positivityapp.models.EventDiarySet;
import com.br.positivityapp.positivityapp.MainActivity;
import com.br.positivityapp.utils.OneDayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPosFragment extends Fragment {

    private MaterialCalendarView calendarView;
    public MainActivity.SectionsPagerAdapter.CalendarPageListener listener;

    public NewPosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_pos, container, false);
        calendarView = (MaterialCalendarView) root.findViewById(R.id.calendarView2);
        ((MainActivity) getActivity()).setActionBarTitle("Positivity App");

        ArrayList<EventDiarySet> allPos = new EventDiarySetDAO(getActivity()).getAllEventDiarySet();

        for (EventDiarySet daySet : allPos) {
            int color;

            switch (daySet.getEventsDiary().size()) {
                case 1 :
                    color = Color.rgb(255, 0 , 255);
                    break;
                case 2 :
                    color = Color.GREEN;
                    break;
                case 3 :
                    color = Color.rgb(255, 200, 0);
                    break;
                case 4 :
                    color = Color.rgb(255, 200, 0);
                    break;
                default:
                    color = Color.YELLOW;
            }
            OneDayDecorator dec = new OneDayDecorator(daySet.getYear(),daySet.getMonth(),daySet.getDay(), color);
            calendarView.addDecorator(dec);
        }

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                listener.onSwitchToNextFirstFragment(date);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).tabLayout.setVisibility(View.VISIBLE);
    }
}
