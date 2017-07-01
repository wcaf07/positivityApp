package com.br.positivityapp.utils;

import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private int color;

    public OneDayDecorator(int y, int m, int d, int c) {
        color = c;
        date = CalendarDay.from(y,m,d);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(color));
    }

}