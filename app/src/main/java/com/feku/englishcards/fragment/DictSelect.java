package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feku.englishcards.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictSelect extends Fragment implements AdapterView.OnItemClickListener {


    public DictSelect() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictselect, container, false);
        ListView lvMain = (ListView) view.findViewById(R.id.dictList);
        String[] dictNames = getResources().getStringArray(R.array.dictionaries);
        formatDictNames(dictNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, dictNames);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardFlipFragment fragment = new CardFlipFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", position);
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activityContainer, fragment)
                .commit();
    }

    private static void formatDictNames(String[] dictNames) {
        for (int i = 0; i < dictNames.length; i++) {
            dictNames[i] = dictNames[i].substring(0, dictNames[i].indexOf("|"));
        }
    }
}
