package com.feku.englishcards.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.CardDao;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

/**
 * Created by feku on 9/2/2015.
 */
public class LevelsPieChart extends Fragment {
    CardDao cardDao = App.getCardDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.levels_pie_chart_layout, container, false);
        PieChart mPieChart = (PieChart) rootView.findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("Level 2", getCardsByLevel(2), Color.parseColor("#3F51B5")));
        mPieChart.addPieSlice(new PieModel("Level 3", getCardsByLevel(3), Color.parseColor("#FFC107")));
        mPieChart.addPieSlice(new PieModel("Learned", getCardsByLevel(4), Color.parseColor("#009688")));
        mPieChart.startAnimation();
        return rootView;
    }

    private float getCardsByLevel(int i) {
        return cardDao.queryBuilder()
                .where(CardDao.Properties.CardLevel.eq(i))
                .count();
    }
}