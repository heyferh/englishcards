package com.feku.englishcards.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by feku on 9/3/2015.
 */
public class WeeklyProgressChart extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.weekly_progress, container, false);
        ValueLineChart mCubicValueLineChart = (ValueLineChart) rootView.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        LocalDate now = LocalDate.now();
        for (LocalDate date = now.minusDays(7); date.isBefore(now.plusDays(1)); date = date.plusDays(1)) {
            series.addPoint(createValueLinePoint(date));
        }
        mCubicValueLineChart.addSeries(series);
//        mCubicValueLineChart.startAnimation();
        return rootView;
    }

    private ValueLinePoint createValueLinePoint(LocalDate date) {
        SharedPreferences prefs = getActivity().getSharedPreferences("english_cards", MODE_PRIVATE);
        long viewedCards = prefs.getLong(date.toString(), 0);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM, d");
        return new ValueLinePoint(formatter.print(date), viewedCards);
    }
}