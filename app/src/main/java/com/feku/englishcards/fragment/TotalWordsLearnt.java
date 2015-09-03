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

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by feku on 9/3/2015.
 */
public class TotalWordsLearnt extends Fragment {
    CardDao cardDao = App.getCardDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.total_words_learns, container, false);
        BarChart mBarChart = (BarChart) rootView.findViewById(R.id.barchart);

        LocalDate now = LocalDate.now();

        for (LocalDate date = now.minusDays(5); date.isBefore(now); date = date.plusDays(1)) {
            mBarChart.addBar(createBar(date));
        }
        mBarChart.startAnimation();
        return rootView;
    }

    private BarModel createBar(LocalDate date) {
        long count = cardDao.queryBuilder()
                .where(CardDao.Properties.Updated.eq(date.toDate()))
                .where(CardDao.Properties.CardLevel.eq(4))
                .count();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM, d");
        return new BarModel(formatter.print(date), count, Color.RED);
    }

}
