package com.feku.englishcards.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feku.englishcards.App;
import com.feku.englishcards.R;
import com.feku.englishcards.dao.DictionaryDao;
import com.feku.englishcards.entity.Dictionary;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feku on 8/26/2015.
 */
public class SelectDictionaryActivity extends ActivityWithDrawer implements AdapterView.OnItemClickListener {
    DictionaryDao dictionaryDao = App.getDictionaryDao();
    ArrayAdapter<String> adapter;
    List<String> dictNames;

    @Override
    protected int getLayout() {
        return R.layout.dictionaries_layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent cardIntent = new Intent(getApplicationContext(), RegularCardActivity.class);
        cardIntent.putExtra("DICTIONARY_ID", dictNames.indexOf(parent.getItemAtPosition(position)));
        startActivity(cardIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initOnCreate() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer = drawer
                .withActionBarDrawerToggle(true)
                .withSelectedItem(0);
        Drawer.Result result = drawer.build();
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        ListView dictList = (ListView) findViewById(R.id.dictList);
        dictNames = new ArrayList<>();
        for (Dictionary dictionary : dictionaryDao.loadAll()) {
            dictNames.add(dictionary.getTitle());
        }
        adapter = new ArrayAdapter<>(this,
                R.layout.simple_list_item_1, dictNames);
        dictList.setAdapter(adapter);
        dictList.setOnItemClickListener(this);
        getSupportActionBar().setTitle("Choose topic");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}