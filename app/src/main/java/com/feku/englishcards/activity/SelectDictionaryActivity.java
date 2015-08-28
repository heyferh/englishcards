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
//        Bundle bundle = new Bundle();
//        bundle.putLong("DICTIONARY_ID", dictNames.indexOf(parent.getItemAtPosition(position)));
//        bundle.putSerializable("CARD_TYPE", CardFragment.CardType.REGULAR);

        startActivity(new Intent(getApplicationContext(), RegularCardActivity.class));
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
        drawer = drawer
                .withSelectedItem(0);
        drawer.build().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView dictList = (ListView) findViewById(R.id.dictList);
        dictNames = new ArrayList<>();
        for (Dictionary dictionary : dictionaryDao.loadAll()) {
            dictNames.add(dictionary.getTitle());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dictNames);
        dictList.setAdapter(adapter);
        dictList.setOnItemClickListener(this);
        getSupportActionBar().setTitle("Choose topic");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}