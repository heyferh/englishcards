package com.feku.englishcards.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.feku.englishcards.R;
import com.feku.englishcards.dao.util.DataBaseLoader;
import com.feku.englishcards.fragment.SelectDictionaryFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getSharedPreferences("english_cards", MODE_PRIVATE).getBoolean("DB_EXIST", false)) {
            DataBaseLoader.loadDictionaries();
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header_layout)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_browse).withIcon(FontAwesome.Icon.faw_archive).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_view_starred).withIcon(FontAwesome.Icon.faw_star).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_leitner_mode).withIcon(FontAwesome.Icon.faw_line_chart).withIdentifier(3),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_secondary).withIcon(FontAwesome.Icon.faw_cog))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new SelectDictionaryFragment())
                                .commit();
                    }
                })
                .build();
    }
}
