package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewSuggestionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SuggestionPOJO suggestionPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_suggestion);
        ButterKnife.bind(this);

        suggestionPOJO= (SuggestionPOJO) getIntent().getSerializableExtra("suggestion");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
