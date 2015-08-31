package com.feku.englishcards.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feku.englishcards.R;

public class EmptyCardFragment extends Fragment {
    private static final String ARG_1 = "CURRENT_CARD";
    private String message;

    public static EmptyCardFragment newInstance(String message) {
        EmptyCardFragment fragment = new EmptyCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_1, message);
        fragment.setArguments(args);
        return fragment;
    }

    public EmptyCardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(ARG_1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_cards_layout, container, false);
        ((TextView) view.findViewById(R.id.message)).setText(message);
        return view;
    }


}
