package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectDictionaryFragment extends Fragment implements AdapterView.OnItemClickListener {
    DictionaryDao dictionaryDao = App.getDictionaryDao();

    public SelectDictionaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_dictionary_layout, container, false);
        ListView dictList = (ListView) view.findViewById(R.id.dictList);
        List<String> dictNames = new ArrayList<>();
        for (Dictionary dictionary : dictionaryDao.loadAll()) {
            dictNames.add(dictionary.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, dictNames);
        dictList.setAdapter(adapter);
        dictList.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardFlipFragment fragment = new CardFlipFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("DICTIONARY_ID", position);
        bundle.putSerializable("CARD_TYPE", CardFragment.CardType.REGULAR);
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
