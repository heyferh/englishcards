package com.feku.englishcards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.feku.englishcards.R;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by feku on 8/26/2015.
 */
public abstract class ActivityWithDrawer extends AppCompatActivity {
    protected Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = new Drawer()
                .withSelectedItem(2)
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header_layout)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_browse).withIcon(FontAwesome.Icon.faw_archive),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_view_starred).withIcon(FontAwesome.Icon.faw_star),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_leitner_system).withIcon(FontAwesome.Icon.faw_line_chart),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.statistics).withIcon(FontAwesome.Icon.faw_cog))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i) {
                            case 1:
                                startActivity(new Intent(getApplicationContext(), SelectDictionaryActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getApplicationContext(), FavouriteCardActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(getApplicationContext(), LeitnerModeActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                                break;
                        }
                    }
                });

        initOnCreate();
    }

    protected void initOnCreate() {
        drawer = drawer.withSelectedItem(-1);
        drawer.build();
    }

    protected abstract int getLayout();

}
