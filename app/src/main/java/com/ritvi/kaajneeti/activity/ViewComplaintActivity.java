package com.ritvi.kaajneeti.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewComplaintActivity extends AppCompatActivity {

    String complaint_name = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ComplaintPOJO complaintPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        complaintPOJO= (ComplaintPOJO) getIntent().getSerializableExtra("complaint");
        if(complaintPOJO!=null){
            getSupportActionBar().setTitle(complaintPOJO.getComplaintSubject());
        }

// Create Timeline rows List
        ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

// Add the new row to the list
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());
        timelineRowsList.add(getTimeLineRow());

// Create the Timeline Adapter
        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                false);

// Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) findViewById(R.id.timeline_listView);
        myListView.setAdapter(myAdapter);


    }

    public TimelineRow getTimeLineRow(){

// Create new timeline row (Row Id)
        TimelineRow myRow = new TimelineRow(0);

// To set the row Date (optional)
        myRow.setDate(new Date());
// To set the row Title (optional)
        myRow.setTitle("Title");
// To set the row Description (optional)
        myRow.setDescription("Description");
// To set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
// To set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
// To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
// To set row Image Size in dp (optional)
        myRow.setImageSize(40);
// To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
// To set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(60);
// To set row Date text color (optional)
        myRow.setDateColor(Color.argb(255, 0, 0, 0));
// To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 0, 0, 0));
// To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

        return myRow;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
