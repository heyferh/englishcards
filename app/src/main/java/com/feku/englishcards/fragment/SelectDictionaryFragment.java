package com.feku.englishcards.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ArrayAdapter<String> adapter;
    List<String> dictNames;

    public SelectDictionaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_dictionary_layout, container, false);
        ListView dictList = (ListView) view.findViewById(R.id.dictList);
        dictNames = new ArrayList<>();
        for (Dictionary dictionary : dictionaryDao.loadAll()) {
            dictNames.add(dictionary.getTitle());
        }
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, dictNames);
        dictList.setAdapter(adapter);
        dictList.setOnItemClickListener(this);
        setHasOptionsMenu(true);
        view.findViewById(R.id.action_search);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CardFlipFragment fragment = new CardFlipFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("DICTIONARY_ID", dictNames.indexOf(parent.getItemAtPosition(position)));
        bundle.putSerializable("CARD_TYPE", CardFragment.CardType.REGULAR);
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
