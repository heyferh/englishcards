package com.feku.englishcards.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feku.englishcards.R;

/**
 * Created by feku on 8/17/2015.
 */
public class DictChoiceActivity extends Activity implements AdapterView.OnItemClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionaries_layout);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        String[] dictNames = getResources().getStringArray(R.array.dictionaries);
        formatDictNames(dictNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dictNames);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CardFlipActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", position);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private static void formatDictNames(String[] dictNames) {
        for (int i = 0; i < dictNames.length; i++) {
            dictNames[i] = dictNames[i].substring(0, dictNames[i].indexOf("|"));
        }
    }
}
